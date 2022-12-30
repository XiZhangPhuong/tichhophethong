package com.example.fastfooddelivery2023.Control;

import androidx.annotation.NonNull;

import com.example.fastfooddelivery2023.Model.Order_FB;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Control_Order {

    public static List<Order_FB> getListOrder(){
        final DatabaseReference dataOrder = FirebaseDatabase.getInstance().getReference("Order");
        List<Order_FB> listOrder = new ArrayList<>();
        dataOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listOrder.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Order_FB or = ds.getValue(Order_FB.class);
                    listOrder.add(or);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return listOrder;
    }

    public static int checkOrder(Order_FB or1 ,String id_user){
        for( Order_FB or : getListOrder()){
             if(or.getCheck()==1 && or.getUser().getId().equals(id_user)){
                 return 1;
             }else if(or.getCheck()==2 && or.getUser().getId().equals(id_user)){
                 return 2;
             }
        }
        return 0;
    }

}
