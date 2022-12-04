package com.example.fastfooddelivery2023.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Window;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Adapter_New.FoodAdapter;
import com.example.fastfooddelivery2023.Adapter_New.SearchAdapter;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
private RecyclerView rcv_searchFood;
private SearchAdapter searchAdapter;
private SearchView search_View;
private EditText edt_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        try {
            setWindow();
            initView();
            loadDataRcvFood();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private List<Food> getListFoodFb(){
        List<Food> list = new ArrayList<>();
        final DatabaseReference dataFood = FirebaseDatabase.getInstance().getReference("Food");
        new Thread(new Runnable() {
            @Override
            public void run() {
                dataFood.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            Food f = ds.getValue(Food.class);
                                list.add(f);
                                Collections.shuffle(list);
                        }
                        searchAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }).start();
        return list;
    }
    private void loadDataRcvFood(){
        rcv_searchFood.setLayoutManager(new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL,false));
        rcv_searchFood.setHasFixedSize(true);
        searchAdapter = new SearchAdapter(SearchActivity.this, getListFoodFb(), new SearchAdapter.ClickSearchFood() {
            @Override
            public void Click(Food food) {

            }
        });
        rcv_searchFood.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
    }
    private void initView(){
        rcv_searchFood = findViewById(R.id.rcv_searchFood);
        search_View = findViewById(R.id.search_View);
        edt_search = findViewById(R.id.edt_search);
    }
    private void setWindow(){
        if(Build.VERSION.SDK_INT>=21){
            Window window = SearchActivity.this.getWindow();
            window.setStatusBarColor(SearchActivity.this.getResources().getColor(android.R.color.holo_blue_dark));
            window.setNavigationBarColor(SearchActivity.this.getResources().getColor(R.color.white));
        }
    }
}