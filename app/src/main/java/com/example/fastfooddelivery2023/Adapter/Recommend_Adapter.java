package com.example.fastfooddelivery2023.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.R;

import java.util.List;

public class Recommend_Adapter extends RecyclerView.Adapter<Recommend_Adapter.RecommendViewHolder> {
    private List<Food> listFood;
    public interface IClickFood{
        void Click(Food food);
    }
    private  IClickFood iClickFood;

    public Recommend_Adapter(List<Food> listFood, IClickFood iClickFood) {
        this.listFood = listFood;
        this.iClickFood = iClickFood;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Recommend_Adapter.RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecommendViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Recommend_Adapter.RecommendViewHolder holder, int position) {
        Food food = listFood.get(position);
        if(food==null){
            return;
        }
        holder.img_red_photo.setImageResource(Integer.parseInt(food.getImage_Food()));
        holder.tv_red_price.setText(food.getName_Food());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickFood.Click(food);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(listFood.size()!=0){
            return listFood.size();
        }
        return 0;
    }

    public class RecommendViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_red_photo;
        private TextView tv_red_price;
        private LinearLayout linearLayout;
        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);
            img_red_photo = itemView.findViewById(R.id.img_red_photo);
            tv_red_price = itemView.findViewById(R.id.tv_name_red_food);
            linearLayout = itemView.findViewById(R.id.linear_Food);
        }
    }
}
