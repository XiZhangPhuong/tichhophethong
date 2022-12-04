package com.example.fastfooddelivery2023;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private DatabaseReference dataHistory = FirebaseDatabase.getInstance().getReference("History");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_nav);
        viewPager2 = findViewById(R.id.viewpager);
        adapter = new MainPager(this);
        viewPager2.setAdapter(adapter);
        bottomNavigationView.setBackground(null);
        viewPager2.setUserInputEnabled(false);

        checkDriver();

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

    private void checkDriver(){
        final DatabaseReference dataOrder = FirebaseDatabase.getInstance().getReference("Order");
        user = DataPreferences.getUser(MainActivity.this,"KEY_USER");
        dataOrder.child(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()){
                    order_fb = ds.getValue(Order_FB.class);
                }
                if(order_fb==null){
                    return;
                }
                if(order_fb.getCheck()==2){
                    TEMPS.showNotification(MainActivity.this,"Hoan hô",order_fb.getStaff().getFullName_staff()+" đã nhận đơn");
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
                }else if(order_fb.getCheck()==3){
                    TEMPS.showNotification(MainActivity.this,order_fb.getId_order(),"Giao hàng thành công");
                    dataOrder.child(String.valueOf(user.getId())).child(order_fb.getId_order()).removeValue();
                    dataHistory.child(String.valueOf(user.getId())).child(order_fb.getId_order()).setValue(order_fb);
                    if(dialog.isShowing()==false){
                        return;
                    }
                    dialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}