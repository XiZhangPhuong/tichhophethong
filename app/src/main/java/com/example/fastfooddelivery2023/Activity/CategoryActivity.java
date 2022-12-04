package com.example.fastfooddelivery2023.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.widget.TableLayout;

import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.Viewpager.CategoryViewPager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class CategoryActivity extends AppCompatActivity {
private TabLayout tabLayout_category;
public static ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        tabLayout_category = findViewById(R.id.tabLayout_category);
        tabLayout_category.setTabTextColors(Color.parseColor("#000000"),Color.parseColor("#ff4d4d"));
        tabLayout_category.setSelectedTabIndicatorColor(Color.parseColor("#ff4d4d"));

        // tabLayout.setTabTextColors(Color.parseColor("#727272"), Color.parseColor("#ffffff"));
        viewPager2 = findViewById(R.id.viewpager2_category);
        setWindow();
        CategoryViewPager adapterViewPager2 = new CategoryViewPager(this);
        viewPager2.setAdapter(adapterViewPager2);

        new TabLayoutMediator(tabLayout_category, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Cơm");
                        break;
                    case 1:
                        tab.setText("Bún|Mì");
                        break;
                    case 2:
                        tab.setText("Đồ uống");
                        break;
                    case 3:
                        tab.setText("Hải sản");
                        break;
                    case 4:
                        tab.setText("Đồ ăn nhanh");
                        break;
                    case 5:
                        tab.setText("Ăn vặt");
                        break;
                    case 6:
                        tab.setText("Nướng|Lẩu");
                        break;
                    case 7:
                        tab.setText("Món nhậu");
                        break;
                }
            }
        }).attach();
        viewItemCategory();
    }

    private void setWindow(){
        if(Build.VERSION.SDK_INT>=21){
            Window window = CategoryActivity.this.getWindow();
            window.setStatusBarColor(CategoryActivity.this.getResources().getColor(android.R.color.holo_blue_dark));
            window.setNavigationBarColor(CategoryActivity.this.getResources().getColor(android.R.color.holo_blue_dark));
        }
    }
    private void viewItemCategory(){
        String str = getIntent().getStringExtra("KEY_CATEGORY");
        switch (str){
            case "Category111111" : viewPager2.setCurrentItem(0);break;
            case "Category222222" : viewPager2.setCurrentItem(1);break;
            case "Category333333" : viewPager2.setCurrentItem(2);break;
            case "Category444444" : viewPager2.setCurrentItem(3);break;
            case "Category555555" : viewPager2.setCurrentItem(4);break;
            case "Category666666" : viewPager2.setCurrentItem(5);break;
            case "Category777777" : viewPager2.setCurrentItem(6);break;
            case "Category888888" : viewPager2.setCurrentItem(7);break;

        }
    }
}