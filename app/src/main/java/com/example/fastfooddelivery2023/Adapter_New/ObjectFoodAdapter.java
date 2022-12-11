package com.example.fastfooddelivery2023.Adapter_New;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.ObjectFood;
import com.example.fastfooddelivery2023.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ObjectFoodAdapter extends RecyclerView.Adapter<ObjectFoodAdapter.ViewHolder> {
    private Context context;
    private List<ObjectFood> list_objectFood;
    public  interface ClickObjectFood{
        void Click(Food food);
    }
    private ClickObjectFood clickObjectFood;

    public ObjectFoodAdapter(Context context, List<ObjectFood> list_objectFood, ClickObjectFood clickObjectFood) {
        this.context = context;
        this.list_objectFood = list_objectFood;
        this.clickObjectFood = clickObjectFood;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_object_food_new,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ObjectFood objectFood = this.list_objectFood.get(position);
        if(objectFood==null){
            return;
        }
        holder.txt_category.setText(objectFood.getCategory());
        Picasso.get().load(objectFood.getImage_category()).into(holder.imageView_category);
        holder.rcv_category.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.rcv_category.setHasFixedSize(true);
        List<Food> listFood = new ArrayList<>();
        listFood.addAll(objectFood.getListFood());

        FoodAdapter foodAdapter = new FoodAdapter(context, listFood, new FoodAdapter.ClickItemFood() {
            @Override
            public void Click(Food food) {
                clickObjectFood.Click(food);
            }
        });
        holder.rcv_category.setAdapter(foodAdapter);
    }

    @Override
    public int getItemCount() {
        if(list_objectFood.size()!=0){
            return list_objectFood.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_category;
        private ImageView imageView_category;
        private RecyclerView rcv_category;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_category = itemView.findViewById(R.id.txt_category);
            imageView_category = itemView.findViewById(R.id.imageView_category);
            rcv_category = itemView.findViewById(R.id.rcv_category);
        }
    }
}
