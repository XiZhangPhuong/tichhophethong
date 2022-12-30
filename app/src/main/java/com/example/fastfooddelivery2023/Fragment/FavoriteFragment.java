package com.example.fastfooddelivery2023.Fragment;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Activity.HistoryActivity;
import com.example.fastfooddelivery2023.Activity.InforActivity;
import com.example.fastfooddelivery2023.Activity.IntroActivity;
import com.example.fastfooddelivery2023.Adapter.Favorite_Adapter;
import com.example.fastfooddelivery2023.Adapter_New.FoodAdapter;
import com.example.fastfooddelivery2023.Adapter_New.SearchAdapter;
import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FavoriteFragment extends Fragment {
    private View mView;
    private FloatingActionButton floatTing;
    private ScrollView scrollViewLike;
    private User user;
    private LinearLayout linear_Favorite_Empty,linear_favorite;
    private RecyclerView rcv_favorite,rcv_item;
    private TextView txt_sizeList,txt_delete_items_favorite;
    private FoodAdapter foodAdapter;
    private SearchAdapter searchAdapter;
    private Favorite_Adapter favorite_adapter;
    private final DatabaseReference data = FirebaseDatabase.getInstance().getReference("Favorite");
    private final DatabaseReference dataFood = FirebaseDatabase.getInstance().getReference("Food");
    private Food foodFB = null;
    private String m = "";
    private Thread th_favorite;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_favorite, container, false);


        try {
            user = DataPreferences.getUser(getContext(),"KEY_USER");
            initView(mView);
            ClickFloating();
            loadDataFavorite();
            loadDataProduct();
        }catch (Exception e){
            e.printStackTrace();
        }

        return mView;
    }

    private void loadDataFavorite(){
        List<Food> list = new ArrayList<>();
        data.child(String.valueOf(user.getId())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                linear_Favorite_Empty.setVisibility(View.GONE);
                linear_favorite.setVisibility(View.GONE);
                list.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Food food = ds.getValue(Food.class);
                    list.add(food);
                }
                if(list.size()==0){
                    linear_Favorite_Empty.setVisibility(View.VISIBLE);
                    linear_favorite.setVisibility(View.GONE);
                    return;
                }
                linear_Favorite_Empty.setVisibility(View.GONE);
                linear_favorite.setVisibility(View.VISIBLE);
                txt_sizeList.setText("Items ("+list.size()+")");
                clickDelete();
                rcv_favorite.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                rcv_favorite.setHasFixedSize(true);
                favorite_adapter = new Favorite_Adapter(list, new Favorite_Adapter.IClickFavorite() {
                    @Override
                    public void ClickLike(Food food, ImageView img_favorite) {
                        data.child(String.valueOf(user.getId())).child(food.getId_Food()).removeValue();
                        Toast.makeText(getContext(),"Đã xóa khỏi mục yêu thích",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void ClickView(Food food) {
                        Intent intent = new Intent(getContext(), InforActivity.class);
                        intent.putExtra("KEY_FOOD",food);
                        startActivity(intent);
                    }
                });
                rcv_favorite.setAdapter(favorite_adapter);
                favorite_adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private String listCategory(){
        List<String> list  = new ArrayList<>();
        data.child(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Food f = ds.getValue(Food.class);
                    list.add(f.getCategory_Food());
                }
                for(String str : list){
                    m+=str;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return m;
    }
    private void loadDataProduct(){
        List<Food> list = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                dataFood.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            foodFB = ds.getValue(Food.class);
                            if(foodFB.getCategory_Food().toLowerCase().contains(listCategory().toLowerCase())){
                                list.add(foodFB);
                            }
                        }
                        rcv_item.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
                        rcv_item.setHasFixedSize(true);
                        foodAdapter = new FoodAdapter(getContext(), list, new FoodAdapter.ClickItemFood() {
                            @Override
                            public void Click(Food food) {
                                Intent intent = new Intent(getContext(),InforActivity.class);
                                intent.putExtra("KEY_FOOD",food);
                                startActivity(intent);
                            }
                        });
                        rcv_item.setAdapter(foodAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }).start();
    }
    private void ClickFloating(){
        floatTing.setVisibility(View.GONE);
        scrollViewLike.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(scrollViewLike.getScrollY()>1000){
                    floatTing.setVisibility(View.VISIBLE);
                    floatTing.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            scrollViewLike.scrollTo(0,0);
                        }
                    });
                }else{
                    floatTing.setVisibility(View.GONE);
                }
            }
        });
    }
    private void clickDelete(){
        txt_delete_items_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Thông báo").setMessage("Bạn có muốn xóa ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                        data.child(String.valueOf(user.getId())).removeValue();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();
            }
        });
    }
    private void initView(View view){
        linear_Favorite_Empty = view.findViewById(R.id.linear_Favorite_Empty);
        linear_favorite = view.findViewById(R.id.linear_favorite);
        rcv_favorite = view.findViewById(R.id.rcv_favorite);
        txt_sizeList  =  view.findViewById(R.id.txt_sizeList);
        txt_delete_items_favorite =view.findViewById(R.id.txt_delete_items_favorite);
        rcv_item = view.findViewById(R.id.rcv_item);
        floatTing = view.findViewById(R.id.floating);
        scrollViewLike = view.findViewById(R.id.scrollViewLike);
    }


}