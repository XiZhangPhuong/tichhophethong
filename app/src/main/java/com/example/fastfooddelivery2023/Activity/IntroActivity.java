package com.example.fastfooddelivery2023.Activity;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Helper.CreateOrder;
import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;

import org.json.JSONObject;

import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class IntroActivity extends AppCompatActivity {
private ImageView imageView;
    Food f = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        imageView = findViewById(R.id.imageView3);
        User user = DataPreferences.getUser(IntroActivity.this,"KEY_USER");


       // zaloPay();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = DataPreferences.getUser(IntroActivity.this,KEY_USER);
                if(user==null){
                    startActivity(new Intent(IntroActivity.this, Login_SignUpActivity.class));
                    finishAffinity();
                }else{
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                    finishAffinity();
                }
            }
        },0);
    }

   private  void zaloPay(){


       CreateOrder orderApi = new CreateOrder();
       try {
           JSONObject data = orderApi.createOrder("150000");
           String code = data.getString("returncode");

           if (code.equals("1")) {



               String token = data.getString("zptranstoken");

               ZaloPaySDK.getInstance().payOrder(IntroActivity.this, token, "demozpdk://app", new PayOrderListener() {
                   @Override
                   public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {

                       System.out.println("Payment Suceess");
                       //  Toast.makeText(getActivity(), "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onPaymentCanceled(String zpTransToken, String appTransID) {
                       Toast.makeText(IntroActivity.this, "Thanh toán bị hủy", Toast.LENGTH_SHORT).show();
                   }

                   @Override
                   public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                       Toast.makeText(IntroActivity.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                   }
               });
           }

       } catch (Exception e) {
           e.printStackTrace();

       }
    }




}