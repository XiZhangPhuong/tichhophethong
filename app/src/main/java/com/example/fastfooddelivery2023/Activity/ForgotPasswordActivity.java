package com.example.fastfooddelivery2023.Activity;

import static com.example.fastfooddelivery2023.Control.TEMPS.checkUser;
import static com.example.fastfooddelivery2023.Control.TEMPS.ranDomCODE;
import static com.example.fastfooddelivery2023.Control.TEMPS.showNotification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Control.TEMPS;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ForgotPasswordActivity extends AppCompatActivity {
private LinearLayout linear_getOTP,linear_change_password;
private EditText edt_phone_forgot,edt_input_code,edt_password,edt_password_retype;
private TextView txt_timeout_forgot,txt_sent_otp;
private Button btn_getOTP,btn_confirm_OTP,btn_confirm_password;
private List<User> listUser;
private User user;
private int OTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        linear_getOTP = findViewById(R.id.linear_getOTP);
        linear_change_password = findViewById(R.id.linear_forgot_password);
        edt_phone_forgot = findViewById(R.id.editTextPhone);
        edt_input_code = findViewById(R.id.edt_inputCode);
        edt_password = findViewById(R.id.edt_forgot_password);
        edt_password_retype = findViewById(R.id.edt_retype_password);
        btn_getOTP = findViewById(R.id.btn_getOTP);
        btn_confirm_OTP = findViewById(R.id.confirm_getOTP);
        btn_confirm_password = findViewById(R.id.confirm_getPassword);
        txt_timeout_forgot = findViewById(R.id.txt_timeout_forgot);
        txt_sent_otp = findViewById(R.id.txt_sent_OTP);

        linear_getOTP.setVisibility(View.GONE);
        linear_change_password.setVisibility(View.GONE);



        final DatabaseReference dataUser = FirebaseDatabase.getInstance().getReference("User");
        dataUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listUser = new ArrayList<>();
                for(DataSnapshot ds : snapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    listUser.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        edt_phone_forgot.addTextChangedListener(textWatcher);
        edt_password.addTextChangedListener(textWatcher);
        edt_password_retype.addTextChangedListener(textWatcher);
        edt_input_code.addTextChangedListener(textWatcher);

        btn_getOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkUser(edt_phone_forgot.getText().toString(),listUser)==null){
                    edt_phone_forgot.setError("K t??m th???y S??T");
                    edt_phone_forgot.requestFocus();
                    return;
                }
                linear_getOTP.setVisibility(View.VISIBLE);
                linear_change_password.setVisibility(View.GONE);
                btn_getOTP.setVisibility(View.GONE);
                String title = "X??c th???c OTP";
                OTP = ranDomCODE();
                String mess  = OTP+" l?? m?? x??c th???c c???a ban";
                showNotification(ForgotPasswordActivity.this,title,mess);
                edt_input_code.requestFocus();
                cowDowTimer();
            }
        });

          // check input OTP
        btn_confirm_OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_input_code.length()<6){
                    edt_input_code.setError("Ph???i ????ng 6 k?? t???");
                    return;
                }
                if(Integer.parseInt(edt_input_code.getText().toString())==OTP){
                    linear_getOTP.setVisibility(View.GONE);
                    linear_change_password.setVisibility(View.VISIBLE);
                    edt_password.requestFocus();
                }else{
                    Toast.makeText(ForgotPasswordActivity.this,"M?? x??c nh???n kh??ng ch??nh x??c",Toast.LENGTH_SHORT).show();
                    edt_input_code.setError("M?? OTP k ????ng");
                    edt_input_code.setText("");
                    edt_input_code.requestFocus();
                }
            }
        });

        txt_sent_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = "X??c th???c OTP";
                OTP = ranDomCODE();
                String mess  = OTP+" l?? m?? x??c th???c c???a ban";
                showNotification(ForgotPasswordActivity.this,title,mess);
                edt_input_code.requestFocus();
                cowDowTimer();
            }
        });

        btn_confirm_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_password.length()<6){
                    edt_password.setError("M???t kh???u ph???i 6 k?? t??? tr??? l??n");
                    edt_password.requestFocus();
                    return;
                }
                if(!edt_password_retype.getText().toString().equals(edt_password.getText().toString())){
                    edt_password_retype.setError("M???t kh???u kh??ng kh???p");
                    edt_password_retype.requestFocus();
                    return;
                }
                user = TEMPS.checkUser(edt_phone_forgot.getText().toString(),listUser);
                if(user!=null){
                    User u = new User(user.getId(),user.getFullName(),user.getPhoneNumber(),edt_password.getText().toString());
                    dataUser.child(u.getId()).setValue(u);
                    showNotification(ForgotPasswordActivity.this,"FastFoodDelivery","X??c nh???n m???t kh???u th??nh c??ng");
                    startActivity(new Intent(ForgotPasswordActivity.this,Login_SignUpActivity.class));
                    finishAffinity();
                }

            }
        });


    }

    private void cowDowTimer(){
       CountDownTimer countDownTimer = new CountDownTimer(60000,1000) {
            @Override
            public void onTick(long l) {
                txt_timeout_forgot.setText("H???t h???n sau : "+l/1000+"s");
            }

            @Override
            public void onFinish() {
                txt_timeout_forgot.setText("M?? OTP ???? h???t h???n.G???i m?? kh??c");
                startActivity(new Intent(ForgotPasswordActivity.this,Login_SignUpActivity.class));
            }
        }.start();
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String phone = edt_phone_forgot.getText().toString();
            String password = edt_password.getText().toString();
            String re_password = edt_password.getText().toString();
            String input_code = edt_input_code.getText().toString();


            edt_phone_forgot.setError(phone.isEmpty()?"S??T tr???ng" : TEMPS.checkUser(phone,listUser)==null ? "K t??m th???y S??T" : null );
            edt_password.setError(password.length()<6 ? "M???t kh???u ph???i 6 k?? t??? tr??? l??n" : null);
            edt_password_retype.setError(!re_password.equals(password) ? "Ch??a kh???p m???t kh???u" : null);
            edt_input_code.setError(input_code.equals("") ? "Tr???ng":null);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}