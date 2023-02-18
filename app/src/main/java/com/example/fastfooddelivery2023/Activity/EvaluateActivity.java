package com.example.fastfooddelivery2023.Activity;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Control.TEMPS;
import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.Comment;
import com.example.fastfooddelivery2023.Model.Comment_FB;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EvaluateActivity extends AppCompatActivity {
private ImageView imageview_back,imageView_Food;
private RatingBar ratingBar;
private TextView txt_name_food,txt_category_food,txt_price_food,txt_count;
private EditText edt_evaluate;
private Button btn_evaluate;
private Food food;
private User user;
private int star = 0;
private int rating;
 private String time;
private final DatabaseReference dataCMT = FirebaseDatabase.getInstance().getReference("Comment");
 private String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        try {
            user = DataPreferences.getUser(EvaluateActivity.this,"KEY_USER");
            time = new SimpleDateFormat("dd-MM-yyyy | hh:mm").format(Calendar.getInstance().getTime());
            setWindow();
            initView();
            clickBack();
            loadDataFood();
            clickRatingBar();
            reviews();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private void initView(){
        imageview_back = findViewById(R.id.imageview_back);
        imageView_Food = findViewById(R.id.imageView_Food);
        ratingBar = findViewById(R.id.ratingBar);
        txt_name_food = findViewById(R.id.txt_name_food);
        txt_category_food = findViewById(R.id.txt_category_food);
        txt_price_food = findViewById(R.id.txt_price_food);
        txt_count = findViewById(R.id.txt_count);
        edt_evaluate = findViewById(R.id.edt_evaluate);
        btn_evaluate = findViewById(R.id.btn_evaluate);
    }
    private void clickRatingBar(){
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rating = (int) v;
                String mess= " ";
                star =  (int)ratingBar.getRating();
                switch (rating){
                    case 1 : mess = "Bạn đã chọn 1 sao";
                        break;
                    case 2 : mess = "Bạn đã chọn 2 sao";
                        break;
                    case 3 : mess = "Bạn đã chọn 3 sao";
                        break;
                    case 4 : mess = "Bạn đã chọn 4 sao";
                        break;
                    case 5 : mess = "Bạn đã chọn 5 sao";
                        break;
                }
                txt_count.setText(mess);
            }
        });
    }

    private void reviews(){
        btn_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(EvaluateActivity.this);
                builder.setTitle("Thông báo").setMessage("Xác nhận đánh giá ? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Comment_FB fb = new Comment_FB(food.getId_Food(),user,edt_evaluate.getText().toString(),time,star);
                                List<Comment_FB> list = new ArrayList<>();
                                list.add(fb);
                                dataCMT.child(food.getId_Food()).child(String.valueOf(user.getId())).setValue(fb);
                                finish();
                                TEMPS.showNotification(EvaluateActivity.this,"FastFoodDelivery","Cảm ơn bạn đã đánh giá");
                                Toast.makeText(EvaluateActivity.this,"Đánh giá đơn hàng thành công",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No",null).create().show();
            }
        });
    }
    private void loadDataFood(){
       food = getIntent().getParcelableExtra("KEY_HISTORY");
       txt_name_food.setText(food.getName_Food());
       txt_category_food.setText(food.getCategory_Food());
       txt_price_food.setText(food.getPrice_Food()+"");
       Picasso.get().load(food.getImage_Food()).into(imageView_Food);
    }
    private void clickBack(){
        imageview_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(EvaluateActivity.this,HistoryActivity.class));
            }
        });
    }
    private void setWindow(){

        if(Build.VERSION.SDK_INT>=21){
            Window window = EvaluateActivity.this.getWindow();
            window.setStatusBarColor(EvaluateActivity.this.getResources().getColor(android.R.color.holo_blue_dark));
            window.setNavigationBarColor(EvaluateActivity.this.getResources().getColor(R.color.white));
        }
    }
}