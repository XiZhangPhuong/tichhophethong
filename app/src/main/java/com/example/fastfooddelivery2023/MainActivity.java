package com.example.fastfooddelivery2023;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Control.TEMPS;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.Order_FB;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.example.fastfooddelivery2023.Viewpager.MainPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    public static ViewPager2 viewPager2;
    private MainPager adapter;
    private User user;
    private Order_FB order_fb;
    private FloatingActionButton floating;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private RelativeLayout view_waiting_driver;
    private TextView txt_waiting_driver;
    private DatabaseReference dataHistory = FirebaseDatabase.getInstance().getReference("History");
    private boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_waiting_driver = findViewById(R.id.txt_waiting_driver);
        view_waiting_driver = findViewById(R.id.view_waiting_driver);
        floating = findViewById(R.id.floating);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        viewPager2 = findViewById(R.id.viewpager);
        adapter = new MainPager(this);
        viewPager2.setAdapter(adapter);
        bottomNavigationView.setBackground(null);
        viewPager2.setUserInputEnabled(false);

        try {
            checkDriver();
        }catch (Exception e){
            e.printStackTrace();
        }

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.menu_home) {
                    viewPager2.setCurrentItem(0);
                } else if (id == R.id.menu_category) {
                    viewPager2.setCurrentItem(1);
                } else if (id == R.id.menu_setting) {
                    viewPager2.setCurrentItem(2);
                } else if (id == R.id.menu_user) {
                    viewPager2.setCurrentItem(3);
                }
                return false;
            }
        });
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_category).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_setting).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menu_user).setChecked(true);
                        break;
                }
            }
        });


    }
    private void checkInternet(){
        if(TEMPS.checkInternet(MainActivity.this)==false){
            viewPager2.setVisibility(View.GONE);
            view_waiting_driver.setVisibility(View.VISIBLE);
            txt_waiting_driver.setText("Không có kết nối Internet");
        }else{
            viewPager2.setVisibility(View.VISIBLE);
            view_waiting_driver.setVisibility(View.GONE);
        }
    }
    private void showDialog(){
        builder = new AlertDialog.Builder(MainActivity.this);
        View view = getLayoutInflater().inflate(R.layout.item_waiting_food,null);
        Button btn_detail = view.findViewById(R.id.btn_detail);
        TextView txt_name_driver = view.findViewById(R.id.txt_name_driver);
        TextView txt_phone_driver = view.findViewById(R.id.txt_phone_driver);
        ImageView image_phone = view.findViewById(R.id.image_phone);
        ImageView image_avatar = view.findViewById(R.id.image_avatar);
        Picasso.get().load("https://chuyenphucvu.vn/uploads/images/nghe-shipper-nhung-rui-ro-khon-luong.jpg").into(image_avatar);
        txt_phone_driver.setText(order_fb.getStaff().getPhoneNumber());
        txt_name_driver.setText(order_fb.getStaff().getFullName_staff());
        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        dialog.getWindow().setLayout(600, 800);
        //call phone
    }
    private void ClickFloating_Button(String txt_phone){
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},100);
        }
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:"+txt_phone));
                startActivity(intent);
            }
        });
    }

    private void checkDriver(){
        view_waiting_driver.setVisibility(View.GONE);
        floating.setVisibility(View.GONE);
        final DatabaseReference dataOrder = FirebaseDatabase.getInstance().getReference("Order");
        user = DataPreferences.getUser(MainActivity.this,"KEY_USER");
        dataOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    order_fb = ds.getValue(Order_FB.class);
                }
                if(order_fb==null){
                    return;
                }
                if(order_fb.getCheck()==1 && order_fb.getUser().getId().equals(user.getId())){
                    view_waiting_driver.setVisibility(View.VISIBLE);
                    return;
                }
                if(order_fb.getCheck()==2 && order_fb.getUser().getId().equals(user.getId()) ){
                    floating.setVisibility(View.VISIBLE);
                    // call user
                    view_waiting_driver.setVisibility(View.VISIBLE);
                    txt_waiting_driver.setText("Tài xế "+order_fb.getStaff().getFullName_staff()+" đang giao");
                    ClickFloating_Button(order_fb.getStaff().getPhoneNumber());
                }else if(order_fb.getCheck()==3 && order_fb.getUser().getId().equals(user.getId())){
                    view_waiting_driver.setVisibility(View.GONE);
                    floating.setVisibility(View.GONE);
                    TEMPS.showNotification(MainActivity.this,order_fb.getId_order(),"Giao hàng thành công");
                    dataOrder.child(order_fb.getId_order()).removeValue();
                    dataHistory.child(String.valueOf(user.getId())).child(order_fb.getId_order()).setValue(order_fb);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        if (flag) {
            super.onBackPressed();
            return;
        }
        flag = true;
        Toast.makeText(MainActivity.this,"Nhấn lần nữa để thoát",Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                flag = false;
            }
        }, 2000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkInternet();
    }
}