package com.example.fastfooddelivery2023.Viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fastfooddelivery2023.Fragment.Cart.CartNotEmptyFragment;
import com.example.fastfooddelivery2023.Fragment.FavoriteFragment;
import com.example.fastfooddelivery2023.Fragment.HomeFragment;
import com.example.fastfooddelivery2023.Fragment.UserFragment;

public class MainPager extends FragmentStateAdapter {
    public MainPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new FavoriteFragment();
            case 2:
                return new CartNotEmptyFragment();
            case 3:
                return new UserFragment();
            default:
                return new HomeFragment();

        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
