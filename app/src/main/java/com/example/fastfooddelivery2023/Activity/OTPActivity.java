package com.example.fastfooddelivery2023.Activity;

import static com.example.fastfooddelivery2023.Control.TEMPS.ranDomCODE;
import static com.example.fastfooddelivery2023.Control.TEMPS.showNotification;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OTPActivity extends AppCompatActivity {
    private TextView txt_phone,txt_cowdow;
    private EditText edt_code_verify;
    private Button bt_veiry;
    private CountDownTimer countDownTimer;
    private String name,phone,pass;
    private DatabaseReference dataUser = FirebaseDatabase.getInstance().getReference("User");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        txt_phone = findViewById(R.id.txt_phone_verify);
        txt_cowdow = findViewById(R.id.txt_timeout_verify);
        edt_code_verify = findViewById(R.id.edt_code_verify);
        bt_veiry = findViewById(R.id.btn_verify1);

        phone = getIntent().getStringExtra("key_phone");
        name = getIntent().getStringExtra("key_name");
        pass = getIntent().getStringExtra("key_pass");
        txt_phone.setText(phone);

        cowDowTimer();
        verify();
    }

    private void cowDowTimer(){
        countDownTimer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long l) {
                txt_cowdow.setText("Hết hạn sau : "+l/1000+"s");
            }

            @Override
            public void onFinish() {
                txt_cowdow.setText("Mã OTP đã hết hạn.Gửi mã khác");
                txt_cowdow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String title = "Xác thực OTP";
                        int OTP = ranDomCODE();
                        String mess  = +OTP+" là mã xác thực của ban";
                        showNotification(OTPActivity.this,title,mess);
                    }
                });
            }
        }.start();
    }

    private void verify(){
        String title = "Xác thực OTP";
        int OTP = ranDomCODE();
        String mess  = OTP+" là mã xác thực của ban";
        showNotification(OTPActivity.this,title,mess);
         bt_veiry.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                  if(edt_code_verify.length()==0){
                      edt_code_verify.setError("Chưa nhập OTP");
                      edt_code_verify.requestFocus();
                      return;
                  }
                  if(Integer.parseInt(edt_code_verify.getText().toString())==OTP){
                      User user = new User("User"+ranDomCODE(),name,phone,pass);
                      dataUser.child(user.getPhoneNumber()).setValue(user);
                      startActivity(new Intent(OTPActivity.this,Login_SignUpActivity.class));
                      finishAffinity();
                      Toast.makeText(OTPActivity.this,"Đăng ký tài khoản thành công",Toast.LENGTH_SHORT).show();
                      showNotification(OTPActivity.this,"FastFoodDelivery","Đăng ký tài khoản thành công");

                  }else{
                      Toast.makeText(OTPActivity.this,"Mã xác thực k đúng",Toast.LENGTH_SHORT).show();
                      edt_code_verify.setText("");
                      edt_code_verify.requestFocus();
                  }
             }
         });
    }
}