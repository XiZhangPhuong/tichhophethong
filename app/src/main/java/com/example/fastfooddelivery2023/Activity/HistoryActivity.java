package com.example.fastfooddelivery2023.Activity;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Adapter.History_Adapter;
import com.example.fastfooddelivery2023.Adapter_New.ObjectHistoryAdapter;
import com.example.fastfooddelivery2023.Control.TEMPS;
import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.Comment;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.Order_FB;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.security.spec.EllipticCurve;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
private RelativeLayout view_history_empty,view_history;
private ImageView image_quick;
private TextView txt_items_history,txt_delete_history;
private RecyclerView rcv_history_food;
private ObjectHistoryAdapter history_adapter;
private final DatabaseReference dataHistory = FirebaseDatabase.getInstance().getReference("History");
private final DatabaseReference dataFood = FirebaseDatabase.getInstance().getReference("Food");
private User user;
private AlertDialog.Builder builder;
private AlertDialog dialog;
private Order_FB o;
private int star = 0;
private int rating ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        try {
            user = DataPreferences.getUser(HistoryActivity.this,"KEY_USER");
            initView();
            setWindow();
            loadDataHistory();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void loadDataHistory(){
        view_history_empty.setVisibility(View.GONE);
        view_history.setVisibility(View.GONE);
        List<Order_FB> list = new ArrayList<>();
        dataHistory.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Order_FB o   = ds.getValue(Order_FB.class);
                    if(o.getUser().getId().equals(user.getId())){
                        list.add(o);
                    }
                }
                if(list.size()==0){
                    view_history_empty.setVisibility(View.VISIBLE);
                    view_history.setVisibility(View.GONE);
                    return;
                }
                view_history_empty.setVisibility(View.GONE);
                view_history.setVisibility(View.VISIBLE);
                txt_items_history.setText("Items ("+list.size()+")");
                rcv_history_food.setLayoutManager(new LinearLayoutManager(HistoryActivity.this,LinearLayoutManager.VERTICAL,false));
                rcv_history_food.setHasFixedSize(true);
                history_adapter = new ObjectHistoryAdapter(HistoryActivity.this, list, new ObjectHistoryAdapter.ClickHistoryFood() {
                    @Override
                    public void Click(Food food) {
                        Intent intent = new Intent(HistoryActivity.this,EvaluateActivity.class);
                        intent.putExtra("KEY_HISTORY",food);
                        startActivity(intent);
                    }
                    @Override
                    public void ClickImage(Food food){
                        Intent intent = new Intent(HistoryActivity.this,InforActivity.class);
                        intent.putExtra("KEY_FOOD",food);
                        startActivity(intent);
                    }
                });
                rcv_history_food.setAdapter(history_adapter);
                history_adapter.notifyDataSetChanged();
                clickReturn();
                deleteItems();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    private void deleteItems(){
        txt_delete_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                builder.setTitle("Thông báo").setMessage("Bạn có muốn xóa ?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataHistory.child(String.valueOf(user.getId())).removeValue();
                        startActivity(new Intent(HistoryActivity.this, MainActivity.class));
                        finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).create().show();
            }
        });
    }
    private void clickReturn(){
        image_quick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void initView(){
        view_history_empty = findViewById(R.id.view_history_empty);
        view_history = findViewById(R.id.view_history);
        txt_items_history = findViewById(R.id.txt_items_history);
        txt_delete_history = findViewById(R.id.txt_delete_history);
        rcv_history_food = findViewById(R.id.rcv_history_food);
        image_quick = findViewById(R.id.image_quick);
    }
    private void setWindow(){
        if(Build.VERSION.SDK_INT>=21){
            Window window = HistoryActivity.this.getWindow();
            window.setStatusBarColor(HistoryActivity.this.getResources().getColor(android.R.color.holo_blue_dark));
            window.setNavigationBarColor(HistoryActivity.this.getResources().getColor(android.R.color.holo_blue_dark));
        }
    }
}