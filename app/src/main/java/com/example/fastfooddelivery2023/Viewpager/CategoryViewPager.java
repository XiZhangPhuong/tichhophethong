package com.example.fastfooddelivery2023.Viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.fastfooddelivery2023.Fragment.Category.CategoryFastFragment;
import com.example.fastfooddelivery2023.Fragment.Category.CategoryHotPotFragment;
import com.example.fastfooddelivery2023.Fragment.Category.CategoryMonNhauFragment;
import com.example.fastfooddelivery2023.Fragment.Category.CategoryNoodleFragment;
import com.example.fastfooddelivery2023.Fragment.Category.CategoryRiceFragment;
import com.example.fastfooddelivery2023.Fragment.Category.CategorySeaFragment;
import com.example.fastfooddelivery2023.Fragment.Category.CategorySnackFragment;
import com.example.fastfooddelivery2023.Fragment.Category.CategoryWalterFragment;

public class CategoryViewPager extends FragmentStateAdapter {
    public CategoryViewPager(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0 : return new CategoryRiceFragment();
            case  1 : return new CategoryNoodleFragment();
            case  2: return new CategoryWalterFragment();
            case  3: return new CategorySeaFragment();
            case  4: return new CategoryFastFragment();
            case  5: return new CategorySnackFragment();
            case  6: return new CategoryHotPotFragment();
            case  7: return new CategoryMonNhauFragment();
            default: return new CategoryRiceFragment();

        }
    }

    @Override
    public int getItemCount() {
        return 8;
    }
}
