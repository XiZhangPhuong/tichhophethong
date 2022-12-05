package com.example.fastfooddelivery2023.Activity;

import static com.example.fastfooddelivery2023.Fragment.HomeFragment.listFoodNew;
import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Adapter_New.FoodAdapter;
import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class InforActivity extends AppCompatActivity {
private ImageView img_return,img_getImageFood,img_like_Food;
private TextView txt_getNameFood,txt_getCategory_Food,txt_getPriceFood,txt_getInForFood;
private Button btn_addTOCart;
private User user;
private RecyclerView rcv_similar_product;
public static List<Food> listFoodToCart = new ArrayList<>();
private FoodAdapter foodAdapter ;
private Food food;
private boolean flag = true;
private DatabaseReference dataFavorite = FirebaseDatabase.getInstance().getReference("Favorite");
private DatabaseReference data = FirebaseDatabase.getInstance().getReference("Cart");
final DatabaseReference dataFood = FirebaseDatabase.getInstance().getReference("Food");
public static List<Food> listFoodFB = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor);

        user = DataPreferences.getUser(InforActivity.this,KEY_USER);
        try {
            setWindow();
            initView();
            loadDataFood();
            loadDataSimilarFood();
            clickBack();
            clickFavoriteFood();
            click_add_cart();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void loadDataFood(){
        food = (Food) getIntent().getParcelableExtra("KEY_FOOD");
        Picasso.get().load(food.getImage_Food()).into(img_getImageFood);
        txt_getNameFood.setText(food.getName_Food());
        txt_getCategory_Food.setText(food.getCategory_Food());
        txt_getInForFood.setText(food.getInformation_Food());
        txt_getPriceFood.setText(food.getPrice_Food()+" đ");
    }
    private void clickBack(){
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InforActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private void clickFavoriteFood(){
        img_like_Food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 if(flag==true){
                     img_like_Food.setImageResource(R.drawable.ic_like_24);
                     Toast.makeText(InforActivity.this,"Đã thêm vào mục yêu thích",Toast.LENGTH_SHORT).show();
                     dataFavorite.child(String.valueOf(user.getId())).child(food.getId_Food()).setValue(food);
                     flag = false;
                 }else{
                     img_like_Food.setImageResource(R.drawable.ic_favorite_24);
                     dataFavorite.child(String.valueOf(user.getId())).child(food.getId_Food()).removeValue();
                     flag = true;
                 }
            }
        });
    }
    private void click_add_cart(){
        btn_addTOCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(InforActivity.this);
                builder.setTitle("Thêm sản phẩm")
                        .setMessage("")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(InforActivity.this,MainActivity.class));
                                finish();
                                data.child(String.valueOf(user.getId())).child(food.getId_Food()).setValue(food);
                                Toast.makeText(InforActivity.this,"Đã thêm vào giỏ hàng",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("Hủy",null).create().show();
            }
        });
    }
         private void loadDataSimilarFood() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        dataFood.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                listFoodFB.clear();
                                for (DataSnapshot ds : snapshot.getChildren()) {
                                    Food f = ds.getValue(Food.class);
                                        if(f.getCategory_Food().toLowerCase().contains(food.getCategory_Food().toLowerCase())){
                                            listFoodFB.add(f);
                                            Collections.shuffle(listFoodFB);
                                        }
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        rcv_similar_product.setLayoutManager(new LinearLayoutManager(InforActivity.this, LinearLayoutManager.HORIZONTAL, false));
                                        rcv_similar_product.setHasFixedSize(true);
                                        foodAdapter = new FoodAdapter(InforActivity.this, listFoodFB, new FoodAdapter.ClickItemFood() {
                                            @Override
                                            public void Click(Food food) {
                                                Toast.makeText(InforActivity.this,food.getId_Food(),Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        rcv_similar_product.setAdapter(foodAdapter);
                                        foodAdapter.notifyDataSetChanged();
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
            private void initView() {
                img_return = findViewById(R.id.img_return);
                img_getImageFood = findViewById(R.id.img_getImageFood);
                img_like_Food = findViewById(R.id.img_like_Food);
                txt_getNameFood = findViewById(R.id.txt_getNameFood);
                txt_getCategory_Food = findViewById(R.id.txt_getCategory_Food);
                txt_getPriceFood = findViewById(R.id.txt_getPriceFood);
                txt_getInForFood = findViewById(R.id.txt_getInForFood);
                btn_addTOCart = findViewById(R.id.btn_addTOCart);
                rcv_similar_product = findViewById(R.id.rcv_similar_product);
            }

            private void setWindow() {
                if (Build.VERSION.SDK_INT >= 21) {
                    Window window = InforActivity.this.getWindow();
                    window.setStatusBarColor(InforActivity.this.getResources().getColor(android.R.color.holo_blue_dark));
                    window.setNavigationBarColor(InforActivity.this.getResources().getColor(R.color.white));
                }
            }
        }



