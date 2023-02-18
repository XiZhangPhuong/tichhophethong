package com.example.fastfooddelivery2023.Service;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.fastfooddelivery2023.Control.TEMPS;
import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.Order_FB;
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
    private List<Order_FB> list;
    int check = 0;
    @Override
    public void onCreate() {
        super.onCreate();
        user = DataPreferences.getUser(MyService.this,KEY_USER);
        dataOrder = FirebaseDatabase.getInstance().getReference("Order");

        final DatabaseReference dataOrder = FirebaseDatabase.getInstance().getReference("Order");
        list=  new ArrayList<>();
        dataOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Order_FB order_fb = ds.getValue(Order_FB.class);
                    list.add(order_fb);
                }
                if(list.size()==0){
                    return;
                }
                for(Order_FB or : list){
                    if(or.getCheck()==1 && or.getUser().getId().equals(user.getId())){
                      //  TEMPS.showNotification(MyService.this,"Thông báo","Chờ tài xế xác nhận đơn hàng");
                    }else if(or.getCheck()==2 && or.getUser().getId().equals(user.getId())){
                        TEMPS.showNotification(MyService.this,"Thông báo","Tài xế đang giao");
                    }else if(or.getCheck()==3 && or.getUser().getId().equals(user.getId())){
                        TEMPS.showNotification(MyService.this,"Thông báo","Giao hàng thành công");
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public ComponentName startForegroundService(Intent service) {
        return super.startForegroundService(service);
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
