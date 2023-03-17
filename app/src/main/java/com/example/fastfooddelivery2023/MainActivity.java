package com.example.fastfooddelivery2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.BroadcastReceiver.MyInternet;
import com.example.fastfooddelivery2023.Model.Order_FB;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.Service.MyService;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.example.fastfooddelivery2023.Viewpager.MainPager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    public static ViewPager2 viewPager2;
    private MainPager adapter;
    private User user;
    private Order_FB order_fb;
    private AlertDialog.Builder builder;
    private ProgressBar progressBar5;
    private AlertDialog dialog;
    private RelativeLayout view_waiting_driver;
    private TextView txt_waiting_driver;
    private DatabaseReference dataHistory = FirebaseDatabase.getInstance().getReference("History");
    private boolean flag = false;
    public static int check = 0;
    private MyInternet myInternet;
    List<Order_FB>  list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar5 = findViewById(R.id.progressBar5);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        viewPager2 = findViewById(R.id.viewpager);
        adapter = new MainPager(this);
        viewPager2.setAdapter(adapter);
        bottomNavigationView.setBackground(null);
        viewPager2.setUserInputEnabled(false);

        user = DataPreferences.getUser(MainActivity.this,"KEY_USER");




        try {
            startService(new Intent(MainActivity.this,MyService.class));
            checkDriver();
            //checkInternet();
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
        progressBar5.setVisibility(View.GONE);

    }
    private void checkInternet(){
        myInternet = new MyInternet();
        if(myInternet.checkInternet==true){
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Đã kết nối Internet", Snackbar.LENGTH_LONG)
                    .setAction("", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .show();
            return;
        }
        if(myInternet.checkInternet==false){
            View parentLayout = findViewById(android.R.id.content);
            Snackbar.make(parentLayout, "Mất kết nối Internet", Snackbar.LENGTH_INDEFINITE)
                    .setAction("", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .show();
            return;
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            registerReceiver(myInternet,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            return;
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            registerReceiver(myInternet,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            return;
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


    private void checkDriver(){

        progressBar5.setVisibility(View.GONE);
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

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }




}