package com.example.fastfooddelivery2023.Fragment;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Adapter.Favorite_Adapter;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FavoriteFragment extends Fragment {
    private View mView;
    private User user;
    private LinearLayout linear_Favorite_Empty,linear_favorite;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_favorite, container, false);


        try {
            linear_Favorite_Empty.setVisibility(View.GONE);
            linear_favorite.setVisibility(View.GONE);
            user = DataPreferences.getUser(getContext(),KEY_USER);
            initView(mView);
            checkView();
        }catch (Exception e){
            e.printStackTrace();
        }

        return mView;
    }
    private void checkView(){
        Toast.makeText(getContext(),getlistFavorite().size()+"",Toast.LENGTH_SHORT).show();
        if(getlistFavorite().size()==0){
            linear_Favorite_Empty.setVisibility(View.VISIBLE);
            linear_favorite.setVisibility(View.GONE);
        }else{
            linear_Favorite_Empty.setVisibility(View.GONE);
            linear_favorite.setVisibility(View.VISIBLE);
        }
    }
    private List<Food> getlistFavorite(){
        List<Food> list = new ArrayList<>();

                final DatabaseReference data = FirebaseDatabase.getInstance().getReference("Favorite");
                data.child(String.valueOf(user.getId())).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            Food f = ds.getValue(Food.class);
                            list.add(f);
                        }
                        Toast.makeText(getContext(),getlistFavorite().size()+"",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        return list;
    }
    private void initView(View view){
        linear_Favorite_Empty = view.findViewById(R.id.linear_Favorite_Empty);
        linear_favorite = view.findViewById(R.id.linear_favorite);
    }


}