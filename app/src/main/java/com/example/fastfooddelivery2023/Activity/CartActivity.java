package com.example.fastfooddelivery2023.Activity;

import static com.example.fastfooddelivery2023.Control.TEMPS.showNotification;
import static com.example.fastfooddelivery2023.Dialog.BottomSheetDialogFragment.listCart;
import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Adapter.Cart_Adapter;
import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.Order;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
private LinearLayout linear_CartEmpty,linear_Cart,linear_waiting;
private ImageView image_back,img_return,img_waiting_driver;
private RecyclerView rcv_cart,rcv_cartFood_waiting;
private Cart_Adapter cartAdapter;
private EditText edt_address;
private TextView txt_city,txt_change_address,txt_items,txt_delete_items,tv_subtotal_cart,tv_delivery_cart,tv_total_cart,txt_total_waiting;
private int sum = 0;
private Button btn_addTOCart;
private User user;
private DatabaseReference dataOrder = FirebaseDatabase.getInstance().getReference("ORDER");
public static List<Order> listOrder = new ArrayList<>();
public static List<Food> listHistoryFood = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();


        user = DataPreferences.getUser(CartActivity.this,KEY_USER);
        if(listCart==null){
            listCart = new ArrayList<>();
        }
        listCart = DataPreferences.getListFood(CartActivity.this,String.valueOf(user.getId()));


        setDataCart();
        checkListCartFood();
        deleteAllItems();
        pay_Order();

        txt_items.setText("Items ( "+listCart.size()+" )");
        for(Food f : listCart){
            sum+=f.getPrice_Food();
        }
        tv_subtotal_cart.setText(String.valueOf(sum));
        tv_total_cart.setText(String.valueOf(sum));




        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, MainActivity.class));
            }
        });





    }
    private void initView(){
        linear_CartEmpty = findViewById(R.id.linear_CartEmpty);
        linear_Cart = findViewById(R.id.linear_Cart);
        image_back = findViewById(R.id.image_back);
        rcv_cart = findViewById(R.id.rcv_cartFood);
        img_return = findViewById(R.id.img_return);
        txt_city = findViewById(R.id.txt_city);
        txt_change_address = findViewById(R.id.txt_change_address);
        txt_items = findViewById(R.id.txt_items);
        txt_delete_items = findViewById(R.id.txt_delete_items);
        tv_subtotal_cart = findViewById(R.id.tv_subtotal_cart);
        tv_delivery_cart = findViewById(R.id.tv_delivery_cart);
        tv_total_cart = findViewById(R.id.tv_total_cart);
        btn_addTOCart = findViewById(R.id.btn_addTOCart);
        edt_address = findViewById(R.id.edt_address);
        txt_total_waiting = findViewById(R.id.txt_total_waiting);
        linear_waiting = findViewById(R.id.linear_waiting_driver);
        rcv_cartFood_waiting = findViewById(R.id.rcv_cartFood_waiting);
        img_waiting_driver = findViewById(R.id.img_waiting_driver);
    }

    private void checkListCartFood(){
        if(listCart.size()!=0){
            linear_CartEmpty.setVisibility(View.GONE);
            linear_waiting.setVisibility(View.GONE);
            linear_Cart.setVisibility(View.VISIBLE);

            return;
        }else{
            linear_CartEmpty.setVisibility(View.VISIBLE);
            linear_Cart.setVisibility(View.GONE);
            linear_waiting.setVisibility(View.GONE);
            image_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(CartActivity.this, MainActivity.class));
                }
            });
        }
    }

    private void deleteAllItems(){
        txt_delete_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listCart.clear();
                cartAdapter.notifyDataSetChanged();
                linear_CartEmpty.setVisibility(View.VISIBLE);
                linear_Cart.setVisibility(View.GONE);
                DataPreferences.setListFood(CartActivity.this,listCart,String.valueOf(user.getId()));
            }
        });
    }

    private void setDataCartWaiting(){
        cartAdapter = new Cart_Adapter(listCart, new Cart_Adapter.IClickCart() {
            @Override
            public void ClickPlusCart(TextView tv_number_cart, TextView tv_total_item, Food food) {

            }

            @Override
            public void ClickMinusCart(TextView tv_number_cart, TextView tv_total_item, Food food) {

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL,false);
        rcv_cartFood_waiting.setLayoutManager(linearLayoutManager);
        rcv_cartFood_waiting.setAdapter(cartAdapter);

    }

    private void setDataCart(){
        cartAdapter = new Cart_Adapter(listCart, new Cart_Adapter.IClickCart() {
            @Override
            public void ClickPlusCart(TextView tv_number_cart, TextView tv_total_item, Food food) {
                int quantity = Integer.parseInt(tv_number_cart.getText().toString());
                if(quantity<=10){
                    quantity = quantity + 1 ;
                    food.setQuantity(quantity);
                    tv_number_cart.setText(String.valueOf(quantity));
                    tv_total_item.setText(""+quantity*food.getPrice_Food());

                    sum+=food.getPrice_Food();
                    tv_subtotal_cart.setText(""+sum);
                    tv_total_cart.setText(""+sum);
                    DataPreferences.setListFood(CartActivity.this,listCart,String.valueOf(user.getId()));
                }

            }

            @Override
            public void ClickMinusCart(TextView tv_number_cart, TextView tv_total_item, Food food) {
                int quantity = Integer.parseInt(tv_number_cart.getText().toString());
                quantity = quantity - 1 ;
                food.setQuantity(quantity);
                tv_number_cart.setText(String.valueOf(quantity));
                tv_total_item.setText(""+quantity*food.getPrice_Food());

                sum-=food.getPrice_Food();
                tv_subtotal_cart.setText(""+sum);
                tv_total_cart.setText(""+sum);
                DataPreferences.setListFood(CartActivity.this,listCart,String.valueOf(user.getId()));

                if(quantity==0){
                   listCart.remove(food);
                  // tv_number_cart.setText(String.valueOf(quantity));
                   txt_items.setText("Items ( "+listCart.size()+" )");
                   cartAdapter.notifyDataSetChanged();
                   DataPreferences.setListFood(CartActivity.this,listCart,String.valueOf(user.getId()));
                   if(listCart.size()==0){
                       linear_CartEmpty.setVisibility(View.VISIBLE);
                       linear_Cart.setVisibility(View.GONE);
                       return;
                   }
                }

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL,false);
        rcv_cart.setLayoutManager(linearLayoutManager);
        rcv_cart.setAdapter(cartAdapter);


    }

    private void pay_Order(){
        btn_addTOCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = dataOrder.push().getKey();
                String inf_user = user.getFullName() + user.getPhoneNumber();
                String inf_driver = "Truong Quyen";
                String inf_food = "";
                String address = edt_address.getText().toString();
                String total = tv_total_cart.getText().toString();
                for(Food food : listCart){
                    inf_food+= "\n"+food.getName_Food() +" : \t" + food.getQuantity();
                }
                Order order = new Order(key,inf_user,inf_driver,inf_food,address,total);
              //  dataOrder.child(key).setValue(order);

                listHistoryFood.addAll(listCart);
             //   listCart.clear();
                cartAdapter.notifyDataSetChanged();
                DataPreferences.setListFood(CartActivity.this,listCart,String.valueOf(user.getId()));
                listOrder.add(order);
                showNotification(CartActivity.this,"FastFoodDelivery","Chờ tài xế xác nhận đơn nhé");

              // interface user waiting driver
                linear_CartEmpty.setVisibility(View.GONE);
                linear_Cart.setVisibility(View.GONE);
                linear_waiting.setVisibility(View.VISIBLE);
                txt_total_waiting.setText(tv_total_cart.getText().toString());
                setDataCartWaiting();
               // animationImage();
            }
        });
    }
    private void animationImage(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                RotateAnimation anim = new RotateAnimation(0f, 350f, 300f, 300f);
                anim.setInterpolator(new LinearInterpolator());
                anim.setRepeatCount(Animation.INFINITE);
                anim.setDuration(10000);
                img_waiting_driver.startAnimation(anim);
            }
        },4000);
    }
}