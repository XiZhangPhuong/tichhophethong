package com.example.fastfooddelivery2023.Service;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fastfooddelivery2023.Control.TEMPS;
import com.example.fastfooddelivery2023.Model.Order;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyService extends Service {
    private User user;
    private DatabaseReference dataOrder;
    private Order order;
    private List<Order> listOrder = new ArrayList<>();
    @Override
    public void onCreate() {
        super.onCreate();
        user = DataPreferences.getUser(MyService.this,KEY_USER);
        dataOrder = FirebaseDatabase.getInstance().getReference("Order");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        CallService();
        return super.onStartCommand(intent, flags, startId);
    }
    private void CallService(){
        dataOrder.child(user.getId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listOrder.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    order = ds.getValue(Order.class);
                    listOrder.add(order);
                }
                if(TEMPS.checkOrderStatus(listOrder)==2){
                    TEMPS.showNotification(MyService.this,"Tài xế đang giao",order.getInf_driver());
                }else if(TEMPS.checkOrderStatus(listOrder)==3){
                    TEMPS.showNotification(MyService.this,"Giao thành công","Mã ĐH : "+order.getId_Order());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
