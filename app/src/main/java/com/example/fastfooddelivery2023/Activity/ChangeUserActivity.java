package com.example.fastfooddelivery2023.Activity;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeUserActivity extends AppCompatActivity {
private ImageView img_back,img_photo_user;
private TextView tv_name_user,tv_phone_user,tv_id_user;
private EditText edt_fullname,edt_phonenumber,edt_password,edt_confirmpassword;
private Button btn_change;
private User user;
private DatabaseReference dataUser = FirebaseDatabase.getInstance().getReference("User");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_user);



        try {
            initView();
            setWindow();
            setUser();
            edittext();
            changeUser();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void initView(){
        img_back = findViewById(R.id.img_back);
        img_photo_user = findViewById(R.id.img_photo_user);
        tv_name_user = findViewById(R.id.tv_name_user);
        tv_phone_user = findViewById(R.id.tv_phone_user);
        tv_id_user = findViewById(R.id.tv_id_user);
        edt_fullname = findViewById(R.id.edt_fullname);
        edt_phonenumber = findViewById(R.id.edt_phonenumber);
        edt_password = findViewById(R.id.edt_password);
        edt_confirmpassword = findViewById(R.id.edt_confirmpassword);
        btn_change = findViewById(R.id.btn_change);
    }
    private void edittext(){
        edt_fullname.addTextChangedListener(textWatcher);
        edt_phonenumber.addTextChangedListener(textWatcher);
        edt_password.addTextChangedListener(textWatcher);
        edt_confirmpassword.addTextChangedListener(textWatcher);
    }
    private void changeUser(){
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ChangeUserActivity.this);
                builder.setTitle("Thông báo").setMessage("Xác nhận thay đổi ? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                User u = new User(user.getId(),edt_fullname.getText().toString(),
                                        edt_phonenumber.getText().toString(),edt_password.getText().toString());
                                DataPreferences.setUser(ChangeUserActivity.this,u,"KEY_USER");
                                dataUser.child(String.valueOf(user.getId())).setValue(u);
                                startActivity(new Intent(ChangeUserActivity.this, MainActivity.class));
                                finish();
                                Toast.makeText(ChangeUserActivity.this,"Cập nhập thông tin thành công",Toast.LENGTH_LONG).show();

                            }
                        }).setNegativeButton("No",null).create().show();
            }
        });
    }
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            btn_change.setBackgroundColor(Color.parseColor("#ff9999"));
            btn_change.setEnabled(false);
            if(edt_fullname.length()<3){
                edt_fullname.setError("Nhập lại họ tên");
            }else if(edt_phonenumber.length()!=10){
                edt_phonenumber.setError("Số điện thoại phải 10 số");
            }else if(edt_password.length()<6){
                edt_password.setError("Mật khẩu phải 6 kí tự trở lên");
            }else  if(!edt_confirmpassword.getText().toString().equals(edt_password.getText().toString())){
                edt_confirmpassword.setError("Mật khẩu không khớp");
            }else {
                edt_fullname.setError(null);
                edt_phonenumber.setError(null);
                edt_password.setError(null);
                edt_confirmpassword.setError(null);
                btn_change.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
    private void setUser(){
        user = DataPreferences.getUser(ChangeUserActivity.this,KEY_USER);
        tv_phone_user.setText(user.getPhoneNumber());
        tv_name_user.setText(user.getFullName());
        tv_id_user.setText(user.getId());

    }
    private void setWindow(){

        if(Build.VERSION.SDK_INT>=21){
            Window window = ChangeUserActivity.this.getWindow();
            window.setStatusBarColor(ChangeUserActivity.this.getResources().getColor(android.R.color.holo_blue_dark));
            window.setNavigationBarColor(ChangeUserActivity.this.getResources().getColor(android.R.color.holo_blue_dark));
        }
    }
}