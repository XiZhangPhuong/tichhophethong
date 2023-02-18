package com.example.fastfooddelivery2023.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Adapter_New.FoodAdapter;
import com.example.fastfooddelivery2023.Adapter_New.MessAdapter;
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
private RecyclerView rcv_searchFood,rcv_recommend;
private SearchAdapter searchAdapter;
private RelativeLayout view_rcv,view_history_search,view_rc_search;
private EditText edt_search;
private TextView txt_list_search;
private ImageView imageView9;
private final DatabaseReference dataFood = FirebaseDatabase.getInstance().getReference("Food");
private MessAdapter messAdapter;
private List<Food> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        try {
            setWindow();
            initView();
            back();
            loadData();
            my_searchFood();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void loadData(){
        view_rc_search.setVisibility(View.VISIBLE);
        view_rcv.setVisibility(View.GONE);
        view_history_search.setVisibility(View.VISIBLE);
    }
    private void my_searchFood(){
         dataFood.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            Food f = ds.getValue(Food.class);
                            list.add(f);
                           // Collections.shuffle(list);
                        }
                        loadDataRcvFood();
                        searchFood();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }


    private void loadDataRcvFood(){
        rcv_searchFood.setLayoutManager(new LinearLayoutManager(SearchActivity.this,LinearLayoutManager.VERTICAL,false));
        rcv_searchFood.setHasFixedSize(true);
        searchAdapter = new SearchAdapter(SearchActivity.this, list, new SearchAdapter.ClickSearchFood() {
            @Override
            public void Click(Food food) {
                Intent intent = new Intent(SearchActivity.this, InforActivity.class);
                intent.putExtra("KEY_FOOD",food);
                startActivity(intent);
            }
        });
        rcv_searchFood.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
    }

    private void searchFood(){
        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                  if(edt_search.length()==0){
                      loadData();
                      return;
                  }
                  filter(editable.toString());
            }
        });
    }
    private void filter(String text){
       List<Food> listSearch = new ArrayList<>();
       for(Food f : list){
           if(f.getName_Food().toLowerCase().contains(text.toLowerCase())
            || f.getCategory_Food().toLowerCase().contains(text.toLowerCase())){
               listSearch.add(f);
           }
       }
        txt_list_search.setText("Tìm thấy "+listSearch.size()+" sản phẩm");
        searchAdapter.filterList(listSearch);
        view_rc_search.setVisibility(View.GONE);
        view_rcv.setVisibility(View.VISIBLE);
        view_history_search.setVisibility(View.GONE);
    }

    private void back(){
        imageView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initView(){
        rcv_searchFood = findViewById(R.id.rcv_searchFood);
        edt_search = findViewById(R.id.esearch_Viewdt_search);
        view_rcv = findViewById(R.id.view_rcv);
        view_rc_search = findViewById(R.id.view_rc_search);
        view_history_search = findViewById(R.id.view_history_search);
        txt_list_search = findViewById(R.id.txt_list_search);
        imageView9 = findViewById(R.id.imageView9);
        rcv_recommend = findViewById(R.id.rcv_recommend);
    }

    private void setWindow(){
        if(Build.VERSION.SDK_INT>=21){
            Window window = SearchActivity.this.getWindow();
            window.setStatusBarColor(SearchActivity.this.getResources().getColor(android.R.color.holo_blue_dark));
            window.setNavigationBarColor(SearchActivity.this.getResources().getColor(R.color.white));
        }
    }
}