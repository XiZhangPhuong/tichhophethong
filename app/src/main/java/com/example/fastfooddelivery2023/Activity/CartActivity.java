package com.example.fastfooddelivery2023.Activity;

import static com.example.fastfooddelivery2023.Control.TEMPS.checkOrderStatus;
import static com.example.fastfooddelivery2023.Control.TEMPS.showNotification;
import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
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
import com.example.fastfooddelivery2023.Service.MyService;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CartActivity extends AppCompatActivity {
private LinearLayout linear_CartEmpty,linear_Cart,linear_waiting,linear_received;
private ImageView image_back,img_return,img_waiting_driver;
private RecyclerView rcv_cart,rcv_cartFood_waiting;
private Cart_Adapter cartAdapter;
private EditText edt_address;
private TextView txt_city,txt_change_address,txt_items,txt_delete_items,tv_subtotal_cart,tv_delivery_cart,tv_total_cart,txt_total_waiting;
private TextView txt_items_cart_waiting,txt_id_order,txt_time_waiting,txt_address_waiting;
private Button btn_addTOCart,btn_cancel_Cart;
private User user;
private final DatabaseReference dataOrder = FirebaseDatabase.getInstance().getReference("Order");
private final DatabaseReference dataHistory  = FirebaseDatabase.getInstance().getReference("History");
public static List<Order> listOrder = new ArrayList<>();
public static List<Food> listHistoryFood = new ArrayList<>();
private List<Food> listCartFB = new ArrayList<>();
private Order order;
private Order order_get;
private  DatabaseReference dataCart;
private Food foodFB;
private double sum = 0;
private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        initView();
        linear_CartEmpty.setVisibility(View.GONE);
        linear_waiting.setVisibility(View.GONE);
        linear_Cart.setVisibility(View.GONE);
        // get user login
        user = DataPreferences.getUser(CartActivity.this,KEY_USER);
        dataCart = FirebaseDatabase.getInstance().getReference("Cart").child(user.getId());
        date = new SimpleDateFormat("dd-MM-yyyy  HH:mm").format(Calendar.getInstance().getTime());

        // get data food cart from firebase

        new Thread(new Runnable() {
            @Override
            public void run() {
                listCartFB.clear();
                dataCart.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listCartFB.clear();
                        for(DataSnapshot ds  : snapshot.getChildren()){
                            foodFB = ds.getValue(Food.class);
                            listCartFB.add(foodFB);
                        }
                        cartAdapter.notifyDataSetChanged();
                        // check cart is empty
                        if(listCartFB.size()==0){
                            linear_CartEmpty.setVisibility(View.VISIBLE);
                            linear_waiting.setVisibility(View.GONE);
                            linear_Cart.setVisibility(View.GONE);
                            return;
                        }
                        linear_CartEmpty.setVisibility(View.GONE);
                        linear_waiting.setVisibility(View.GONE);
                        linear_Cart.setVisibility(View.VISIBLE);
                        getDataOrder();
                        txt_items.setText("Items ( "+listCartFB.size()+" )");
                        cartAdapter.notifyDataSetChanged();

                        totalFood();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }).start();



      //  ItemsClickCart();
       // checkListCartFood();
        deleteAllItems();
        back();
        getDataCartFromFireBase();
        pay_Order();










    }
    private void totalFood(){
        for(Food f : listCartFB){
            sum+=f.getPrice_Food();
        }
        tv_subtotal_cart.setText(String.valueOf(sum));
        tv_total_cart.setText(String.valueOf(sum));
    }
    private void back(){
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CartActivity.this, MainActivity.class));
            }
        });
    }
    private void dataCart_Waiting(){
        cartAdapter = new Cart_Adapter(listCartFB, new Cart_Adapter.IClickCart() {
            @Override
            public void ClickPlusCart(TextView tv_number_cart, TextView tv_total_item, Food food) {
            }
            @Override
            public void ClickMinusCart(TextView tv_number_cart, TextView tv_total_item, Food food) {
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this,LinearLayoutManager.VERTICAL,false);
        rcv_cartFood_waiting.setLayoutManager(linearLayoutManager);
        rcv_cartFood_waiting.setAdapter(cartAdapter);
        rcv_cartFood_waiting.setHasFixedSize(true);
        cartAdapter.notifyDataSetChanged();
    }
    private void getDataCartFromFireBase(){
           cartAdapter = new Cart_Adapter(listCartFB, new Cart_Adapter.IClickCart() {
               @Override
               public void ClickPlusCart(TextView tv_number_cart, TextView tv_total_item, Food food) {
                    int quantity = Integer.parseInt(tv_number_cart.getText().toString());
                    quantity = quantity + 1;
                    foodFB.setQuantity(quantity);
                    tv_number_cart.setText(String.valueOf(quantity));
                    tv_total_item.setText(String.valueOf(quantity*foodFB.getPrice_Food()));
                    sum = sum + foodFB.getPrice_Food();
                    tv_total_cart.setText(sum+"");
                    tv_subtotal_cart.setText(sum+"");
               }

               @Override
               public void ClickMinusCart(TextView tv_number_cart, TextView tv_total_item, Food food) {
                   int quantity = Integer.parseInt(tv_number_cart.getText().toString());
                   quantity = quantity - 1;
                   food.setQuantity(quantity);
                   tv_number_cart.setText(String.valueOf(quantity));
                   tv_total_item.setText(String.valueOf(quantity*food.getPrice_Food()));
                   sum = sum - food.getPrice_Food();
                   tv_total_cart.setText(sum+"");
                   tv_subtotal_cart.setText(sum+"");

                   if(quantity==0){
                       dataCart.child(foodFB.getId_Food()).removeValue();
                       listCartFB.remove(foodFB);
                       sum = 0;
                       totalFood();
                       cartAdapter.notifyDataSetChanged();
                       return;
                   }
                   if(listCartFB.size()==0){
                       linear_CartEmpty.setVisibility(View.VISIBLE);
                       linear_waiting.setVisibility(View.GONE);
                       linear_Cart.setVisibility(View.GONE);
                       return;
                   }

               }
           });
           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this,LinearLayoutManager.VERTICAL,false);
           rcv_cart.setLayoutManager(linearLayoutManager);
           rcv_cart.setAdapter(cartAdapter);
           rcv_cart.setHasFixedSize(true);
    }

    private void getDataOrder(){
         dataOrder.child(user.getId()).addValueEventListener(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 listOrder.clear();
                 for(DataSnapshot ds : snapshot.getChildren()){
                     order_get = ds.getValue(Order.class);
                     listOrder.add(order_get);
                 }
                 if(checkOrderStatus(listOrder)==1){
                     linear_CartEmpty.setVisibility(View.GONE);
                     linear_waiting.setVisibility(View.VISIBLE);
                     linear_Cart.setVisibility(View.GONE);
                     dataCart_Waiting();
                     txt_items_cart_waiting.setText("Đơn hàng của bạn ( "+listCartFB.size()+" )");
                     txt_id_order.setText(order_get.getId_Order());
                     txt_total_waiting.setText(tv_total_cart.getText().toString()+" vnd");
                     txt_time_waiting.setText(date);
                     txt_address_waiting.setText("Giao tới "+order_get.getAddress());
                     Cancellation_Order();

                 }else if(checkOrderStatus(listOrder)==2){
                     linear_CartEmpty.setVisibility(View.GONE);
                     linear_waiting.setVisibility(View.GONE);
                     linear_Cart.setVisibility(View.GONE);
                     startService(new Intent(CartActivity.this, MyService.class));
                     showAlerDialogDriver();
                 }else if(checkOrderStatus(listOrder)==3){
                     linear_CartEmpty.setVisibility(View.GONE);
                     linear_waiting.setVisibility(View.GONE);
                     linear_Cart.setVisibility(View.GONE);
                     dataOrder.child(user.getId()).removeValue();
                     dataCart.removeValue();
                     startActivity(new Intent(CartActivity.this,MainActivity.class));
                     startService(new Intent(CartActivity.this, MyService.class));
                     dataHistory.child(user.getId()).child(foodFB.getId_Food()).setValue(foodFB);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {

             }
         });
    }
    private void driver_received(){

    }
    private void deleteAllItems(){
        txt_delete_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listCartFB.clear();
                cartAdapter.notifyDataSetChanged();
                linear_CartEmpty.setVisibility(View.VISIBLE);
                linear_Cart.setVisibility(View.GONE);
                View parentLayout = findViewById(android.R.id.content);
                Snackbar.make(parentLayout, "Giỏ hàng của bạn trống", Snackbar.LENGTH_LONG)
                        .setAction("Hoàn tất", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                        .show();
                dataCart.removeValue();
            }

        });
    }


    private void pay_Order(){
        btn_addTOCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_address.length() ==0){
                    edt_address.setError("Địa chỉ trống");
                    edt_address.requestFocus();
                    return;
                }
                String key = dataOrder.push().getKey();
                String inf_user = user.getFullName() +"@"+ user.getPhoneNumber();
                String inf_driver = "";
                String inf_food = "";
                String address = edt_address.getText().toString();
                String total = tv_total_cart.getText().toString();
                for(Food food : listCartFB){
                    inf_food+= "\n"+food.getName_Food() +" : \t" + food.getQuantity();
                }
                order = new Order(String.valueOf(user.getId()),key,inf_user,inf_driver,inf_food,address,total,1);
                dataOrder.child(user.getId()+"").child(order.getId_Order()).setValue(order);

                cartAdapter.notifyDataSetChanged();
                listOrder.add(order);
                showNotification(CartActivity.this,"FastFoodDelivery","Chờ tài xế xác nhận đơn nhé");

              // interface user waiting driver
                linear_CartEmpty.setVisibility( View.GONE);
                linear_Cart.setVisibility(View.GONE);
                linear_waiting.setVisibility(View.VISIBLE);

                dataCart_Waiting();
                Cancellation_Order();
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

    private void Cancellation_Order(){
        btn_cancel_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference dataOrder = FirebaseDatabase.getInstance().getReference("Order").child(String.valueOf(user.getId()));
                dataOrder.removeValue();
                Toast.makeText(CartActivity.this,"Bạn đã hủy đơn hàng",Toast.LENGTH_LONG).show();
                startActivity(new Intent(CartActivity.this,MainActivity.class));
                linear_CartEmpty.setVisibility(View.VISIBLE);
                linear_Cart.setVisibility(View.GONE);
                linear_waiting.setVisibility(View.GONE);
            }
        });
    }

    private void showAlerDialogDriver(){
        AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
        View view_driver = getLayoutInflater().inflate(R.layout.item_driver,null);
        TextView  txt_driver_received = (TextView)view_driver.findViewById(R.id.txt_driver_received);
        TextView  txt_phone_driver = (TextView)view_driver.findViewById(R.id.txt_phone_driver);
        TextView  txt_car_driver = (TextView)view_driver.findViewById(R.id.txt_car_driver);
        Button btn_contact_driver = (Button) view_driver.findViewById(R.id.btn_contact_driver);


        String arr[] = order_get.getInf_driver().split("@");
        txt_driver_received.setText(arr[0]+" đang giao");
        txt_phone_driver.setText("Sđt : "+arr[1]);
        txt_car_driver.setText("Loại xe : "+arr[2]);

        btn_contact_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(arr[2]));
                startActivity(intent);
            }
        });

        builder.setView(view_driver);
        AlertDialog dialog = builder.create();
        dialog.show();
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
        txt_items_cart_waiting  = findViewById(R.id.txt_items_cart_waiting);
        btn_cancel_Cart = findViewById(R.id.btn_cancel_Cart);
        txt_time_waiting = findViewById(R.id.txt_time_wating);
        txt_id_order = findViewById(R.id.txt_id_order);
        txt_address_waiting = findViewById(R.id.txt_address_waiting);
    }
}
