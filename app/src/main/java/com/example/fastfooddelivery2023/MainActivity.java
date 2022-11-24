package com.example.fastfooddelivery2023;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.fastfooddelivery2023.Activity.CartActivity;
import com.example.fastfooddelivery2023.Model.Food;
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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    public static ViewPager2 viewPager2;
    private MainPager adapter;
    private FloatingActionButton btncart;
    private TextView txt_cart_badge;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_cart_badge = findViewById(R.id.cart_badge);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        viewPager2 = findViewById(R.id.viewpager);
        btncart = findViewById(R.id.btn_cart);
        btncart.setColorFilter(Color.WHITE);
        adapter = new MainPager(this);
        viewPager2.setAdapter(adapter);
        bottomNavigationView.setBackground(null);
        bottomNavigationView.getMenu().getItem(2).setEnabled(false);
        viewPager2.setUserInputEnabled(false);

       try {
           badge_cart();
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
                } else if (id == R.id.menu_user) {
                    viewPager2.setCurrentItem(2);
                } else if (id == R.id.menu_setting) {
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
                        bottomNavigationView.getMenu().findItem(R.id.menu_user).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menu_setting).setChecked(true);
                        break;
                }
            }
        });

        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void badge_cart(){
        final DatabaseReference dataCart = FirebaseDatabase.getInstance().getReference("Cart");
        List<Food> list = new ArrayList<>();
        user = DataPreferences.getUser(MainActivity.this,KEY_USER);
        dataCart.child(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Food food = ds.getValue(Food.class);
                    list.add(food);
                }
                if(list.size()==0){
                    txt_cart_badge.setVisibility(View.GONE);
                    return;
                }
                txt_cart_badge.setVisibility(View.VISIBLE);
                txt_cart_badge.setText(list.size()+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}