package com.example.fastfooddelivery2023.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.fastfooddelivery2023.R;

public class FavoriteFragment extends Fragment {
    private View mView;
    private ImageView img_return;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_favorite, container, false);
        initView(mView);
        return mView;
    }
    private void initView(View view){

    }
}