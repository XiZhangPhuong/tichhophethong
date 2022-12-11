package com.example.fastfooddelivery2023.Adapter_New;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.fastfooddelivery2023.Adapter.History_Adapter;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.Order_FB;
import com.example.fastfooddelivery2023.R;

import java.util.List;

public class ObjectHistoryAdapter extends RecyclerView.Adapter<ObjectHistoryAdapter.ViewHolder> {
     private Context context;
     private List<Order_FB> listOrder;
     public interface  ClickHistoryFood{
         void Click(Food food);
         void ClickImage(Food food);
     }
     private ClickHistoryFood clickHistoryFood;

    public ObjectHistoryAdapter(Context context, List<Order_FB> listOrder, ClickHistoryFood clickHistoryFood) {
        this.context = context;
        this.listOrder = listOrder;
        this.clickHistoryFood = clickHistoryFood;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ObjectHistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_food,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ObjectHistoryAdapter.ViewHolder holder, int position) {
        Order_FB order = this.listOrder.get(position);
        if(order==null){
            return;
        }
        holder.txt_id_order.setText("#"+order.getId_order());
        holder.txt_time_order.setText(order.getTime_order());
        holder.rcv_history.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        holder.rcv_history.setHasFixedSize(true);
        History_Adapter history_adapter = new History_Adapter(context, order.getListFood(), new History_Adapter.ClickEvaluateFood() {
            @Override
            public void Click(Food food) {
                clickHistoryFood.Click(food);
            }

            @Override
            public void ClickImage(Food food) {
                clickHistoryFood.ClickImage(food);
            }
        });
        holder.rcv_history.setAdapter(history_adapter);
        history_adapter.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if(listOrder.size()!=0){
            return listOrder.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_id_order,txt_time_order,txt_count_order;
        private RecyclerView rcv_history;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_id_order = itemView.findViewById(R.id.txt_id_order);
            txt_time_order = itemView.findViewById(R.id.txt_time_order);
            rcv_history = itemView.findViewById(R.id.rcv_history);
        }
    }
}
