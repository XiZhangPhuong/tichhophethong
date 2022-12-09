package com.example.fastfooddelivery2023.Fragment.LoginSignUp;

import static com.example.fastfooddelivery2023.Control.TEMPS.checkPhoneNumber;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Activity.OTPActivity;
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


public class SignUpFragment extends Fragment {
    private EditText edt_Name,edt_Phone,edt_password,edt_conf;
    private Button btn_Signup;
    private View mView;
    private LinearLayout linear_SignUp;
    private ProgressBar progressBar;
    private DatabaseReference dataUser = FirebaseDatabase.getInstance().getReference("User");
    private List<User> listUser;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_sign_up, container, false);

        edt_Name = mView.findViewById(R.id.edt_fullname);
        edt_Phone = mView.findViewById(R.id.edt_phonenumber);
        edt_password = mView.findViewById(R.id.edt_password);
        edt_conf = mView.findViewById(R.id.edt_confirmpassword);
        btn_Signup = mView.findViewById(R.id.btnsingup);
        progressBar = mView.findViewById(R.id.progressBar);
        linear_SignUp = mView.findViewById(R.id.linear_SignUp);



        progressBar.setVisibility(View.GONE);

        edt_Name.addTextChangedListener(loginTextWatcher);
        edt_Phone.addTextChangedListener(loginTextWatcher);
        edt_password.addTextChangedListener(loginTextWatcher);
        edt_conf.addTextChangedListener(loginTextWatcher);



        btn_Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataUser.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(edt_Phone.getText().toString()).exists()){
                            Toast.makeText(getContext(), "Số điện thoại đã được đăng ký", Toast.LENGTH_SHORT).show();
                            edt_Phone.setText("");
                            edt_Phone.requestFocus();
                            edt_Phone.setError("Vui lòng đổi số điện thoại");
                        }else {
                            Intent intent = new Intent(getContext(), OTPActivity.class);
                            intent.putExtra("key_name",edt_Name.getText().toString());
                            intent.putExtra("key_phone",edt_Phone.getText().toString());
                            intent.putExtra("key_pass",edt_password.getText().toString());
                            startActivity(intent);
                            edt_Name.setText("");
                            edt_Phone.setText("");
                            edt_password.setText("");
                            edt_conf.setText("");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        return mView;
    }

    private TextWatcher loginTextWatcher  = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String name = edt_Name.getText().toString();
            String phone = edt_Phone.getText().toString();
            String pass = edt_password.getText().toString();
            String confirm_pass = edt_conf.getText().toString();
            btn_Signup.setEnabled(false);
            if(edt_Name.length()<3){
                edt_Name.setError("Nhập lại họ tên");
            }else if(phone.length()!=0){
                edt_Phone.setError("Số điện thoại phải 10 số");
            }else if(pass.length()<6){
                edt_password.setError("Mật khẩu phải 6 kí tự trở lên");
            }else  if(!confirm_pass.equals(pass)){
                edt_conf.setError("Mật khẩu không khớp");
            }else {
                edt_Name.setError(null);
                edt_Phone.setError(null);
                edt_password.setError(null);
                edt_conf.setError(null);
                btn_Signup.setEnabled(true);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}