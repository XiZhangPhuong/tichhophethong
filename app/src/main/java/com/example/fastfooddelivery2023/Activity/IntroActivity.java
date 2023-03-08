package com.example.fastfooddelivery2023.Activity;

import static com.example.fastfooddelivery2023.Control.TEMPS.stringToBase64;
import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Adapter.FunctionUser_Adapter;
import com.example.fastfooddelivery2023.Fragment.Cart.CartNotEmptyFragment;
import com.example.fastfooddelivery2023.Helper.AppInfo;
import com.example.fastfooddelivery2023.Helper.CreateOrder;
import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.Advertisement;
import com.example.fastfooddelivery2023.Model.Comment;
import com.example.fastfooddelivery2023.Model.Comment_FB;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.FunctionUser;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.Model.VideoShort;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.Service.MyService;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import vn.zalopay.sdk.Environment;
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