package com.example.fastfooddelivery2023.Fragment.Cart;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Activity.InforActivity;
import com.example.fastfooddelivery2023.Activity.VideoActivity;
import com.example.fastfooddelivery2023.Activity.WaitingActivity;
import com.example.fastfooddelivery2023.Adapter_New.RcvOrderAdapter;
import com.example.fastfooddelivery2023.Adapter_New.SearchAdapter;
import com.example.fastfooddelivery2023.Control.TEMPS;
import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.Order_FB;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
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


public class WaitingFragment extends Fragment {
private View mView;
private RelativeLayout view_empty,view_waiting;
private TextView txt_time,txt_name,txt_phone,txt_address,txt_id_order,txt_total_order;
private RecyclerView rcv_order;
private Button btn_dismiss;
private RcvOrderAdapter orderAdapter;
private final DatabaseReference dataOrder = FirebaseDatabase.getInstance().getReference("Order");
private User user = null;
private String time = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_waiting,container,false);
        try {
            time = new SimpleDateFormat("dd-MM-yyyy | hh:mm").format(Calendar.getInstance().getTime());
            user = DataPreferences.getUser(getContext(),"KEY_USER");
            initView();
            checkView();
        }catch (Exception e){
            e.printStackTrace();
        }
        return mView;
    }
    private void initView(){
        view_empty = mView.findViewById(R.id.view_empty);
        view_waiting = mView.findViewById(R.id.view_waiting);
        rcv_order = mView.findViewById(R.id.rcv_order);
        btn_dismiss  = mView.findViewById(R.id.btn_dismiss);
        txt_time=  mView.findViewById(R.id.txt_time);
        txt_name=  mView.findViewById(R.id.txt_name);
        txt_phone=  mView.findViewById(R.id.txt_phone);
        txt_address=  mView.findViewById(R.id.txt_address);
        txt_id_order=  mView.findViewById(R.id.txt_id_order);
        txt_total_order=  mView.findViewById(R.id.txt_total_order);
    }
    private void checkView(){
        List<Order_FB> list= new ArrayList<>();
        dataOrder.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    Order_FB o  = ds.getValue(Order_FB.class);
                    list.add(o);
                }
                if(list.size()==0){
                    view_empty.setVisibility(View.VISIBLE);
                    view_waiting.setVisibility(View.GONE);
                    btn_dismiss.setVisibility(View.GONE);
                    return;
                }
                for(Order_FB or : list){
                    if(or.getUser().getId().equals(user.getId()) && or.getCheck()==1){
                        view_empty.setVisibility(View.GONE);
                        view_waiting.setVisibility(View.VISIBLE);
                        btn_dismiss.setVisibility(View.VISIBLE);
                        txt_time.setText(time);
                        txt_name.setText(or.getUser().getFullName());
                        txt_phone.setText(or.getUser().getPhoneNumber());
                        txt_address.setText(or.getAddress_order());
                        txt_id_order.setText(or.getId_order());
                        txt_total_order.setText(or.getTotal_cart()+"");
                        rcv_order.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                        rcv_order.setHasFixedSize(true);
                            orderAdapter = new RcvOrderAdapter(getContext(), or.getListFood(), new SearchAdapter.ClickSearchFood() {
                                @Override
                                public void Click(Food food) {
                                Intent intent = new Intent(getContext(), InforActivity.class);
                                intent.putExtra("KEY_FOOD",food);
                                startActivity(intent);
                            }
                        });
                        rcv_order.setAdapter(orderAdapter);
                        clickButton(or);

                        Snackbar.make(getActivity().findViewById(android.R.id.content),
                                "Xem review đồ ăn", Snackbar.LENGTH_LONG).setAction("Xem", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(getContext(), VideoActivity.class));
                            }
                        }).show();

                    }else if(or.getUser().getId().equals(user.getId()) && or.getCheck()!=1){
                        view_empty.setVisibility(View.VISIBLE);
                        view_waiting.setVisibility(View.GONE);
                        btn_dismiss.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void clickButton(Order_FB or){
        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Thông báo").setMessage("Xác nhận hủy đơn hàng ? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                 Intent intent = new Intent(getContext(), MainActivity.class);
                                 startActivity(intent);
                                 dataOrder.child(or.getId_order()).removeValue();
                                TEMPS.showNotification(getContext(),or.getId_order(),"Hủy đơn hàng thành công");
                                Toast.makeText(getContext(),"Hủy đơn hàng thành công",Toast.LENGTH_SHORT).show();
                            }
                        }).setNegativeButton("No",null).create().show();
            }
        });
    }
}