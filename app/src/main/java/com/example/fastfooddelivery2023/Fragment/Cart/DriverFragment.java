package com.example.fastfooddelivery2023.Fragment.Cart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fastfooddelivery2023.Model.Order_FB;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class DriverFragment extends Fragment {
private View mView;
private RelativeLayout view_main,view_empty;
private CardView view_driver;
private TextView txt_time,txt_name,txt_phone;
private ImageView img_driver,img_phone;
private Button btn_call;
private final DatabaseReference dataOrder = FirebaseDatabase.getInstance().getReference("Order");
private User user = null;
private String time = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_driver,container,false);
        try {
            time = new SimpleDateFormat("dd-MM-yyyy | hh:mm").format(Calendar.getInstance().getTime());
            user = DataPreferences.getUser(getContext(),"KEY_USER");
            initView();
            checkView();
        }catch (Exception e){
            e.printStackTrace();
        }
        return mView;
    }
    private void checkView(){
        List<Order_FB> list= new ArrayList<>();
        dataOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Order_FB or = ds.getValue(Order_FB.class);
                    list.add(or);
                }
                if(list.size()==0){
                    view_main.setBackground(null);
                    view_main.setBackgroundColor(Color.parseColor("#f2f2f2"));
                    view_empty.setVisibility(View.VISIBLE);
                    view_driver.setVisibility(View.GONE);
                }
                for(Order_FB or : list){
                    if(or.getUser().getId().equals(user.getId()) && or.getCheck()==2){
                        view_main.setBackgroundDrawable(ContextCompat.getDrawable(getContext(), R.drawable.map));
                        view_empty.setVisibility(View.GONE);
                        view_driver.setVisibility(View.VISIBLE);
                        txt_time.setText(time);
                        txt_name.setText(or.getStaff().getFullName_staff());
                        txt_phone.setText(or.getStaff().getPhoneNumber());
                        Picasso.get().load("https://thumbs.dreamstime.com/b/phone-vector-icon-isolated-illustration-handset-flat-style-eps-file-available-166354863.jpg").into(img_phone);
                        Picasso.get().load("https://img.freepik.com/premium-vector/female-user-profile-avatar-is-woman-character-screen-saver-with-emotions_505620-617.jpg?w=2000").into(img_driver);
                        clickCallPhone(btn_call,img_phone,txt_phone.getText().toString());

                    }else if(or.getUser().getId().equals(user.getId()) && or.getCheck()!=2){
                        view_main.setBackground(null);
                        view_main.setBackgroundColor(Color.parseColor("#f2f2f2"));
                        view_empty.setVisibility(View.VISIBLE);
                        view_driver.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void clickCallPhone(Button button,ImageView imageView,String txt_phone){
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CALL_PHONE},100);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+txt_phone));
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+txt_phone));
                startActivity(intent);
            }
        });
    }
    private void initView(){
        view_main = mView.findViewById(R.id.view_main);
        view_empty = mView.findViewById(R.id.view_empty);
        view_driver = mView.findViewById(R.id.view_driver);
        txt_time = mView.findViewById(R.id.txt_time);
        txt_name = mView.findViewById(R.id.txt_name);
        txt_phone = mView.findViewById(R.id.txt_phone);
        img_driver = mView.findViewById(R.id.img_driver);
        img_phone = mView.findViewById(R.id.img_phone);
        btn_call = mView.findViewById(R.id.btn_call);
    }
}