package com.example.fastfooddelivery2023.Fragment;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fastfooddelivery2023.Activity.HistoryActivity;
import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class UserFragment extends Fragment {
    private View mView;
    private TextView txt_name_user,txt_phone_user,txt_id_user;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ImageView img_back;
    private LinearLayout linear_history;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_user, container, false);
        initView(mView);
        setDataUser();


        linear_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HistoryActivity.class));
            }
        });






        return mView;
    }
    private void setDataUser(){
        User user = DataPreferences.getUser(getContext(),KEY_USER);
        txt_phone_user.setText(user.getPhoneNumber());
        txt_name_user.setText(user.getFullName());
        txt_id_user.setText(user.getId());
    }

    private void initView(View view){
        linear_history = view.findViewById(R.id.linear_history_cart);
        txt_name_user = view.findViewById(R.id.tv_name_user);
        txt_id_user = view.findViewById(R.id.tv_id_user);
        txt_phone_user = view.findViewById(R.id.tv_phone_user);
    }
}