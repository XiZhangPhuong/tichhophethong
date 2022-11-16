package com.example.fastfooddelivery2023.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Bundle;

import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.Viewpager.LoginSignUpViewPager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Login_SignUpActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    public static ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);



        tabLayout = findViewById(R.id.tabLayoutLoginSignUp);
        tabLayout.setTabTextColors(Color.parseColor("#000000"),Color.parseColor("#ff4d4d"));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#ff4d4d"));

        // tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
        viewPager2 = findViewById(R.id.viewPagerLoginSignUp);

        LoginSignUpViewPager adapterViewPager2 = new LoginSignUpViewPager(this);
        viewPager2.setAdapter(adapterViewPager2);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("ĐĂNG NHẬP");
                        break;
                    case 1:
                        tab.setText("ĐĂNG KÝ");
                        break;
                }
            }
        }).attach();
    }
}