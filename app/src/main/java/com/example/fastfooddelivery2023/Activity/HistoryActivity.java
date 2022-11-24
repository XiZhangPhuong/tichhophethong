package com.example.fastfooddelivery2023.Activity;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Adapter.History_Adapter;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.spec.EllipticCurve;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
private RecyclerView rcv_history;
private TextView txt_sizeListHistory,txt_delete_items_history;
private LinearLayout  linear_history_food,linear_history_empty;
private RelativeLayout relative_history;
private Food foodFB;
private DatabaseReference dataFood = FirebaseDatabase.getInstance().getReference("History");
private List<Food> listHistoryFB = new ArrayList<>();
private History_Adapter history_adapter;
private User user;
public static String OBJECT_FOOD = "OBJECT_FOOD";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        rcv_history = findViewById(R.id.rcv_history_food);
        txt_sizeListHistory = findViewById(R.id.txt_sizeListHistory);
        txt_delete_items_history = findViewById(R.id.txt_delete_items_history);
        relative_history = findViewById(R.id.relative_history);
        linear_history_food = findViewById(R.id.linear_history_food);
        linear_history_empty = findViewById(R.id.linear_history_empty);

        user = DataPreferences.getUser(HistoryActivity.this,KEY_USER);

        try {
            loadDataHistoryFoodFromFireBase();
            deleteAllItemsHistory();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void loadDataHistoryFoodFromFireBase(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                 dataFood.child(user.getId()).addValueEventListener(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         listHistoryFB.clear();
                         for(DataSnapshot ds  : snapshot.getChildren()){
                             Food food = ds.getValue(Food.class);
                             listHistoryFB.add(food);
                         }
                         history_adapter.notifyDataSetChanged();
                         loadView();
                         txt_sizeListHistory.setText("Items history ("+listHistoryFB.size()+" )");
                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });
            }
        }).start();
        history_adapter = new History_Adapter(listHistoryFB, new History_Adapter.ClickEvaluateFood() {
            @Override
            public void Click(Food food) {
                Intent intent = new Intent(HistoryActivity.this, EvaluateActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(OBJECT_FOOD,food);
//                intent.putExtras(bundle);
               startActivity(intent);
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryActivity.this,LinearLayoutManager.VERTICAL,false);
        rcv_history.setLayoutManager(linearLayoutManager);
        rcv_history.setAdapter(history_adapter);
        history_adapter.notifyDataSetChanged();
        rcv_history.setHasFixedSize(true);
    }

    private void loadView(){
         if(listHistoryFB.isEmpty()){
            linear_history_empty.setVisibility(View.VISIBLE);
            linear_history_food.setVisibility(View.GONE);
            relative_history.setVisibility(View.GONE);
        }else if(listHistoryFB.size()>0){
            linear_history_empty.setVisibility(View.GONE);
            linear_history_food.setVisibility(View.VISIBLE);
            relative_history.setVisibility(View.VISIBLE);
        }
    }

    private void deleteAllItemsHistory(){
        txt_delete_items_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataFood.child(user.getId()).removeValue();
                Toast.makeText(HistoryActivity.this,"Đã xóa tất cả",Toast.LENGTH_SHORT).show();
            }
        });
    }



}