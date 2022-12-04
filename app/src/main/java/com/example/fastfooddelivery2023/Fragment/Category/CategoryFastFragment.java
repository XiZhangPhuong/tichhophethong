package com.example.fastfooddelivery2023.Fragment.Category;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fastfooddelivery2023.Activity.InforActivity;
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

public class CategoryFastFragment extends Fragment {
    private View mView;
    private RecyclerView rcv_rice;
    private SearchAdapter searchAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_category_fast,container,false);
        rcv_rice  = mView.findViewById(R.id.rcv_rice);

        loadData();
        return  mView;
    }
    private void loadData(){
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
                            if(f.getCategory_Food().equals("Đồ ăn nhanh")){
                                list.add(f);
                                Collections.shuffle(list);
                            }
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rcv_rice.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                                rcv_rice.setHasFixedSize(true);
                                searchAdapter = new SearchAdapter(getContext(), list, new SearchAdapter.ClickSearchFood() {
                                    @Override
                                    public void Click(Food food) {
                                        Intent intent = new Intent(getContext(), InforActivity.class);
                                        intent.putExtra("KEY_FOOD",food);
                                        startActivity(intent);
                                    }
                                });
                                rcv_rice.setAdapter(searchAdapter);
                            }
                        });
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }).start();
    }
}