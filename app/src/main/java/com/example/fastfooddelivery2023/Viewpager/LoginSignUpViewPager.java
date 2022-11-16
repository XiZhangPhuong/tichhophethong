package com.example.fastfooddelivery2023.Viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment;
import com.example.fastfooddelivery2023.Fragment.LoginSignUp.SignUpFragment;

public class LoginSignUpViewPager extends FragmentStateAdapter {
    public LoginSignUpViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new LoginFragment();
            case 1:
                return new SignUpFragment();
            default:
                return new LoginFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
