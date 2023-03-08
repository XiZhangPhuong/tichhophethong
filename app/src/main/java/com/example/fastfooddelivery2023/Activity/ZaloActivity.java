package com.example.fastfooddelivery2023.Activity;

import static com.example.fastfooddelivery2023.Fragment.Cart.CartNotEmptyFragment.listFoodCart;
import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Control.TEMPS;
import com.example.fastfooddelivery2023.Helper.AppInfo;
import com.example.fastfooddelivery2023.Helper.CreateOrder;
import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.Order_FB;
import com.example.fastfooddelivery2023.Model.Staff;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonIOException;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ZaloActivity extends AppCompatActivity {
private ProgressBar progressBar6;
private Order_FB order_fb;
private DecimalFormat df = new DecimalFormat("######");
private DatabaseReference dataCart = FirebaseDatabase.getInstance().getReference("Cart");
private DatabaseReference dataOrder = FirebaseDatabase.getInstance().getReference("Order");
private  User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zalo);
        progressBar6 = findViewById(R.id.progressBar6);

         //user = DataPreferences.getUser(ZaloActivity.this,KEY_USER);
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);
        try {

            zaloPay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  void zaloPay() throws Exception {

      //  order_fb = (Order_FB) getIntent().getParcelableExtra("KEY_ZALO");
        String address = getIntent().getStringExtra("ADDRESS");
        double total = getIntent().getDoubleExtra("TOTALCART",0);

        CreateOrder orderApi = new CreateOrder();
            int x = (int) total;
            JSONObject data = orderApi.createOrder(x+"");
            String code = data.getString("returncode");

            if (code.equals("1")) {

                String token = data.getString("zptranstoken");

                ZaloPaySDK.getInstance().payOrder(ZaloActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {

                        String id_order = dataOrder.push().getKey();
                        user = DataPreferences.getUser(ZaloActivity.this,"KEY_USER");
                        Staff staff = new Staff("","","");
                       // String address = edt_address.getText().toString() + " "+txt_city.getText().toString();
                        String time= new SimpleDateFormat("dd/MM/yyyy | hh:mm").format(Calendar.getInstance().getTime());
                      //  Double total = Double.parseDouble(txt_total_cart.getText().toString());
                        order_fb = new Order_FB(id_order,user,staff,address,listFoodCart,time,total,"ZaloPay",1);

                        dataOrder.child(id_order).setValue(order_fb);

                        dataCart.child(String.valueOf(user.getId())).removeValue();
                        MainActivity.viewPager2.setCurrentItem(0);
                        dataOrder.child(order_fb.getId_order()).setValue(order_fb);
                        dataCart.child(String.valueOf(user.getId())).removeValue();

                        TEMPS.showNotification(ZaloActivity.this,"Đặt hàng thành công","Chờ tài xế xác nhận đơn nhé");
                        startActivity(new Intent(ZaloActivity.this,MainActivity.class));
                        //finishAffinity();

                    }

                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransID) {
                        Toast.makeText(ZaloActivity.this, "Thanh toán bị hủy", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                        Toast.makeText(ZaloActivity.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
            }


    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}