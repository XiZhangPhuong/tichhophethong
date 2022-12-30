package com.example.fastfooddelivery2023.Fragment.Cart;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Activity.InforActivity;
import com.example.fastfooddelivery2023.Activity.WaitingActivity;
import com.example.fastfooddelivery2023.Adapter.Cart_Adapter;
import com.example.fastfooddelivery2023.Control.TEMPS;
import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.Order_FB;
import com.example.fastfooddelivery2023.Model.Staff;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CartNotEmptyFragment extends Fragment {
    private View mView;
    private RecyclerView rcv_cart;
    private User user;
    private ProgressBar progressBar3;
    private ImageView image_back;
    private Button btn_addTOCart;
    private FloatingActionButton floating;
    private RelativeLayout view_cart_not_empty,view_cart_empty,view_cart;
    private Cart_Adapter cartAdapter;
    private TextView txt_total_cart,txt_delete_items,txt_items,txt_city,txt_temp_cart,txt_ship_cart;
    private EditText edt_address;
    public static List<Food> listFoodCart ;
    private double sum = 0;
    private DatabaseReference dataCart = FirebaseDatabase.getInstance().getReference("Cart");
    private DatabaseReference dataOrder = FirebaseDatabase.getInstance().getReference("Order");
    private Food f;
    private Order_FB order_fb;
    private List<Order_FB> listOrder = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_cart_not_empty, container, false);

        user = DataPreferences.getUser(getContext(),KEY_USER);
        listFoodCart = new ArrayList<>();




        try {
            initView(mView);
            checkOrder();
            loadDataCart();
            deleteItem();

        }catch (Exception e){
            e.printStackTrace();
        }
        return mView;
    }
    private void total_cart(){
        for(Food food: listFoodCart){
            sum+=food.getQuantity()*food.getPrice_Food();
        }
        txt_temp_cart.setText(sum+" ");
        if(sum<=100000){
            double ship = 15000;
            txt_ship_cart.setText(ship+"");
            sum = sum + (double)15000;
            txt_total_cart.setText(sum+"");
        }else{
            txt_ship_cart.setText("Free");
            txt_total_cart.setText(sum+"");
        }
    }
    private void loadDataCart(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                dataCart.child(String.valueOf(user.getId())).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                         listFoodCart.clear();
                         view_cart.setVisibility(View.GONE);
                         for(DataSnapshot ds : snapshot.getChildren()){
                             f = ds.getValue(Food.class);
                             listFoodCart.add(f);
                         }
                         getActivity().runOnUiThread(new Runnable() {
                             @Override
                             public void run() {
                                 view_cart.setVisibility(View.VISIBLE);
                                 if(listFoodCart.size()==0){
                                     view_cart_empty.setVisibility(View.VISIBLE);
                                     view_cart_not_empty.setVisibility(View.GONE);
                                     btn_addTOCart.setVisibility(View.GONE);
                                     floating.setVisibility(View.GONE);
                                     progressBar3.setVisibility(View.GONE);
                                     return;
                                 }
                                 view_cart_empty.setVisibility(View.GONE);
                                 view_cart_not_empty.setVisibility(View.VISIBLE);
                                 btn_addTOCart.setVisibility(View.VISIBLE);
                                 txt_items.setText("Mặt hàng ("+listFoodCart.size()+" món)");
                                 rcv_cart.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                                 rcv_cart.setHasFixedSize(true);
                                 cartAdapter = new Cart_Adapter(listFoodCart, new Cart_Adapter.IClickCart() {
                                     @Override
                                     public void ClickPlusCart(TextView tv_number_cart, TextView tv_total_item, Food food) {
                                         int i = Integer.parseInt(tv_number_cart.getText().toString());
                                         i++;
                                         food.setQuantity(i);
                                         tv_number_cart.setText(i+"");
                                         tv_total_item.setText(i*food.getPrice_Food()+"");
                                         sum = sum + food.getPrice_Food();
                                         txt_temp_cart.setText(sum + "");

                                         if(sum<=100000){
                                             txt_ship_cart.setText("15000");
                                             sum = sum + (double)15000;
                                             txt_total_cart.setText(sum+"");
                                         }else{
                                             txt_ship_cart.setText("Free");
                                             txt_total_cart.setText(sum+"");
                                         }
                                     }

                                     @Override
                                     public void ClickMinusCart(TextView tv_number_cart, TextView tv_total_item, Food food) {
                                         int i = Integer.parseInt(tv_number_cart.getText().toString());
                                         i--;
                                         tv_number_cart.setText(i+"");
                                         food.setQuantity(i);
                                         tv_total_item.setText(i*food.getPrice_Food()+"");
                                         sum = sum - food.getPrice_Food();
                                         txt_temp_cart.setText(sum+" ");
                                         if(sum<=100000){
                                             double ship = 15000;
                                             txt_ship_cart.setText(ship+"");
                                             sum = sum + (double)15000;
                                             txt_total_cart.setText(sum+"");
                                         }else{
                                             txt_ship_cart.setText("Free");
                                             txt_total_cart.setText(sum+"");
                                         }
                                         if(i==0){
                                            // listFoodCart.remove(food);
                                             sum = 0;
                                             dataCart.child(String.valueOf(user.getId())).child(food.getId_Food()).removeValue();
                                             cartAdapter.notifyDataSetChanged();
                                             txt_total_cart.setText(sum + " ");
                                             tv_number_cart.setText(i+"");
                                             floating.setVisibility(View.GONE);
                                         }else if(listFoodCart.size()==0){
                                             MainActivity.viewPager2.setCurrentItem(0);
                                             dataCart.child(String.valueOf(user.getId())).removeValue();
                                             floating.setVisibility(View.GONE);
                                         }
                                     }
                                     @Override
                                     public void ClickImage(Food food){
                                         Intent intent = new Intent(getContext(), InforActivity.class);
                                         intent.putExtra("KEY_FOOD",food);
                                         startActivity(intent);
                                     }
                                 });
                                 rcv_cart.setAdapter(cartAdapter);
                                 cartAdapter.notifyDataSetChanged();
                                 progressBar3.setVisibility(View.GONE);
                                 total_cart();
                                 confirm_Cart();
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
    private void deleteItem(){
        txt_delete_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Thông báo").setMessage("Xóa tất cả đơn hàng")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                dataCart.child(user.getId()).removeValue();
                                                cartAdapter.notifyDataSetChanged();
                                                Toast.makeText(getContext(),"Xóa thành công",Toast.LENGTH_SHORT).show();
                                                MainActivity.viewPager2.setCurrentItem(0);
                                            }
                                        }).setNegativeButton("Hủy",null)
                                        .create().show();
                            }
                        });
                    }
                }).start();
            }
        });
    }
    private void confirm_Cart(){
        btn_addTOCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setTitle("Thông báo").setMessage("Xác nhận đặt hàng")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                  if(edt_address.length()<=5){
                                                      edt_address.setError("Nhập lại địa chỉ");
                                                      edt_address.setText("");
                                                      edt_address.requestFocus();
                                                      return;
                                                  }
                                                  String id_order = dataOrder.push().getKey();
                                                  user = DataPreferences.getUser(getContext(),"KEY_USER");
                                                  Staff staff = new Staff("","","");
                                                  String address = edt_address.getText().toString() + " "+txt_city.getText().toString();
                                                  String time= new SimpleDateFormat("dd/MM/yyyy | hh:mm").format(Calendar.getInstance().getTime());
                                                  Double total = Double.parseDouble(txt_total_cart.getText().toString());
                                                  order_fb = new Order_FB(id_order,user,staff,address,listFoodCart,time,total,1);
                                                  dataOrder.child(id_order).setValue(order_fb);
                                                  Toast.makeText(getContext(),"Đặt hàng thành công",Toast.LENGTH_SHORT).show();
                                                  dataCart.child(String.valueOf(user.getId())).removeValue();
                                                  MainActivity.viewPager2.setCurrentItem(0);
                                                  TEMPS.showNotification(getContext(),"Đặt hàng thành công","Chờ tài xế xác nhận đơn nhé");

                                            }
                                        }).setNegativeButton("Hủy",null).create().show();
                            }
                        });
                    }
                }).start();
            }
        });
    }
    private void checkOrder(){
        List<Order_FB> list = new ArrayList<>();
        dataOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Order_FB or = ds.getValue(Order_FB.class);
                    list.add(or);
                }
                if(list.size()==0){
                    return;
                }
                for(Order_FB or : list){
                    if(or.getUser().getId().equals(user.getId()) && or.getCheck()==1){
                        btn_addTOCart.setBackgroundColor(Color.parseColor("#ff8080"));
                        btn_addTOCart.setText("Đang trong trạng thái chờ");
                        btn_addTOCart.setEnabled(false);
                        ClickFloating();
                    }else if(or.getUser().getId().equals(user.getId()) && or.getCheck()==2){
                        btn_addTOCart.setBackgroundColor(Color.parseColor("#ff8080"));
                        btn_addTOCart.setText("Đang trong trạng thái chờ");
                        btn_addTOCart.setEnabled(false);
                        ClickFloating();
                    }else if(or.getUser().getId().equals(user.getId()) && or.getCheck()!=1 || or.getCheck()!=2) {
                        btn_addTOCart.setBackgroundColor(Color.parseColor("#ff0000"));
                        btn_addTOCart.setText("Xác nhận đặt hàng");
                        btn_addTOCart.setEnabled(true);
                        floating.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void ClickFloating(){
        floating.setVisibility(View.VISIBLE);
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),WaitingActivity.class);
                startActivity(intent);
            }
        });
    }
    private void initView(View view){
        rcv_cart = view.findViewById(R.id.rcv_cart);
        txt_total_cart = view.findViewById(R.id.txt_total_cart);
        txt_delete_items = view.findViewById(R.id.txt_delete_items);
        txt_items  = view.findViewById(R.id.txt_items);
        view_cart_not_empty = view.findViewById(R.id.view_cart_not_empty);
        view_cart_empty = view.findViewById(R.id.view_cart_empty);
        btn_addTOCart = view.findViewById(R.id.btn_addTOCart);
        view_cart = view.findViewById(R.id.view_cart);
        edt_address = view.findViewById(R.id.edt_address);
        txt_city = view.findViewById(R.id.txt_city);
        txt_temp_cart = view.findViewById(R.id.txt_temp_cart);
        txt_ship_cart = view.findViewById(R.id.txt_ship_cart);
        floating = view.findViewById(R.id.floating);
        progressBar3 = view.findViewById(R.id.progressBar3);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}