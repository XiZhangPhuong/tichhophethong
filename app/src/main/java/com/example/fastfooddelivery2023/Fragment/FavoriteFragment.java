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
import com.example.fastfooddelivery2023.Adapter.Recommend_Adapter;
import com.example.fastfooddelivery2023.Adapter.Sale_Adapter;
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
    private ImageView img_return;
    private TextView txt_sizeList,txt_delete_items_favorite;
    private RecyclerView rcv_favorite;
    private LinearLayout linear_Favorite_Empty,linear_favorite,linear_title_favorite;
    private Favorite_Adapter  favorite_adapter;
    private User user;
    private List<Food> listFavoriteFB = new ArrayList<>();
    private boolean flagLike = true;
    private boolean flag_Cart = true;
    final DatabaseReference dataFavorite = FirebaseDatabase.getInstance().getReference("Favorite");
    final DatabaseReference dataCart = FirebaseDatabase.getInstance().getReference("Cart");
    private Food foodFB;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_favorite, container, false);
        initView(mView);
        getDataFavoriteFromFireBase();


        return mView;
    }


    private void initView(View view){
        rcv_favorite = view.findViewById(R.id.rcv_favorite);
        txt_sizeList = view.findViewById(R.id.txt_sizeList);
        txt_delete_items_favorite = view.findViewById(R.id.txt_delete_items_favorite);
        linear_Favorite_Empty = mView.findViewById(R.id.linear_Favorite_Empty);
        linear_favorite = mView.findViewById(R.id.linear_favorite);
        linear_title_favorite = mView.findViewById(R.id.linear_title_favorite);
    }

    private void getDataFavoriteFromFireBase(){
        user = DataPreferences.getUser(getContext(),KEY_USER);
         final DatabaseReference dataLike = FirebaseDatabase.getInstance().getReference("Favorite");
         new Thread(new Runnable() {
             @Override
             public void run() {
                 dataLike.child(user.getId()).addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         listFavoriteFB.clear();
                         for(DataSnapshot ds : snapshot.getChildren()){
                             foodFB = ds.getValue(Food.class);
                             listFavoriteFB.add(foodFB);
                         }
                         if(listFavoriteFB.size()==0){
                             linear_favorite.setVisibility(View.GONE);
                             linear_Favorite_Empty.setVisibility(View.VISIBLE);
                             linear_title_favorite.setVisibility(View.GONE);
                         }else{
                             linear_Favorite_Empty.setVisibility(View.GONE);
                             linear_favorite.setVisibility(View.VISIBLE);
                             linear_title_favorite.setVisibility(View.VISIBLE);
                             favorite_adapter.notifyDataSetChanged();
                             txt_sizeList.setText("Sản phẩm yêu thích ( "+listFavoriteFB.size()+" )");
                         }
                     }
                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });
             }
         }).start();
        favorite_adapter = new Favorite_Adapter(listFavoriteFB, new Favorite_Adapter.IClickFavorite() {
            @Override
            public void ClickLike(Food food, ImageView img_favorite) {
                img_favorite.setImageResource(R.drawable.ic_favorite_24);
                dataFavorite.child(user.getId()).child(foodFB.getId_Food()).removeValue();
                favorite_adapter.notifyDataSetChanged();
            }

            @Override
            public void ClickAddToCart(Food food, ImageView img_check) {

            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        rcv_favorite.setLayoutManager(gridLayoutManager);
        rcv_favorite.setAdapter(favorite_adapter);
        rcv_favorite.setHasFixedSize(true);


    }
}