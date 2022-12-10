package com.example.fastfooddelivery2023.Viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fastfooddelivery2023.Fragment.Cart.DriverFragment;
import com.example.fastfooddelivery2023.Fragment.Cart.WaitingFragment;

public class WaitingViewPager extends FragmentStateAdapter {

    public WaitingViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0 : return new WaitingFragment();
            case 1 : return new DriverFragment();

            default: return new WaitingFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
