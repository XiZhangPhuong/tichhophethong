package com.example.fastfooddelivery2023.Fragment.LoginSignUp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.fastfooddelivery2023.Activity.ForgotPasswordActivity;
import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class LoginFragment extends Fragment {
    private View mView;
    private EditText edt_username,edt_password;
    private Button btn_login;
    private ProgressBar progressBar;
    private DatabaseReference dataUser = FirebaseDatabase.getInstance().getReference("User");
    public static final String KEY_USER = "KEY_USER";
    private List<User> listUser = new ArrayList<>();
    private TextView txt_forgot_password;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_login, container, false);

        btn_login = mView.findViewById(R.id.btnlogin);
        progressBar = mView.findViewById(R.id.progressBar);
        edt_username = mView.findViewById(R.id.username);
        edt_password = mView.findViewById(R.id.password);
        txt_forgot_password = mView.findViewById(R.id.forgotpassword);
        progressBar.setVisibility(View.GONE);



        txt_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                loGinWithFireBase();
            }
        });
        return mView;
    }


    private void loGinWithFireBase(){
        String phone = edt_username.getText().toString().trim();
        String pass = edt_password.getText().toString();
        dataUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    listUser.add(user);
                }
                User u = checkUser(phone,pass);
                if(u!=null){
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(),"Đăng nhập thành công",Toast.LENGTH_SHORT).show();;
                    DataPreferences.setUser(getContext(),u,KEY_USER);
                    progressBar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(getContext(),"Sai tên đăng nhập hoặc mật khẩu",Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                  Toast.makeText(getContext(),"K kết nối được với Database",Toast.LENGTH_SHORT).show();
            }
        });
    }


    private  User checkUser(String phone,String pass){
        for(User user : listUser){
            if(user.getPhoneNumber().equals(phone) && user.getPassWord().equals(pass)){
                return user;
            }
        }
        return null;
    }
}