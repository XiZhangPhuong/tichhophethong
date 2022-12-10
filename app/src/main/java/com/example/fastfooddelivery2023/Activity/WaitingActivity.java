package com.example.fastfooddelivery2023.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.Viewpager.CategoryViewPager;
import com.example.fastfooddelivery2023.Viewpager.WaitingViewPager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class WaitingActivity extends AppCompatActivity {
    private TabLayout tabLayout_waiting;
    public static ViewPager2 viewPager2;
    private ImageView image_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        tabLayout_waiting = findViewById(R.id.tablayout);
        viewPager2  = findViewById(R.id.viewpager2_waiting);
        image_back = findViewById(R.id.image_back);
        setWindow();
        clickBack();
        tabLayout_waiting.setTabTextColors(Color.parseColor("#000000"),Color.parseColor("#ff4d4d"));
        tabLayout_waiting.setSelectedTabIndicatorColor(Color.parseColor("#ff4d4d"));
        WaitingViewPager waitingViewPager = new WaitingViewPager(this);
        viewPager2.setAdapter(waitingViewPager);


        new TabLayoutMediator(tabLayout_waiting, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0 : tab.setText("Chờ xác nhận");break;
                    case 1 : tab.setText("Đang giao");break;
                }
            }
        }).attach();

    }
    private void clickBack(){
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void setWindow(){
        if(Build.VERSION.SDK_INT>=21){
            Window window = WaitingActivity.this.getWindow();
            window.setStatusBarColor(WaitingActivity.this.getResources().getColor(android.R.color.holo_blue_dark));
            window.setNavigationBarColor(WaitingActivity.this.getResources().getColor(R.color.white));
        }
    }
}