package com.example.fastfooddelivery2023.Adapter;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfooddelivery2023.Control.TEMPS;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Recommend_Adapter extends RecyclerView.Adapter<Recommend_Adapter.RecommendViewHolder> {
    private List<Food> listFood;
    private Sale_Adapter.ICLickFood icLickFood;
    public interface ICLickFood{
        void Click_Add_Cart(Food food);
        void Click_View_Food(Food food);
    }

    public Recommend_Adapter(List<Food> listFood, Sale_Adapter.ICLickFood icLickFood) {
        this.listFood = listFood;
        this.icLickFood = icLickFood;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Recommend_Adapter.RecommendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecommendViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fastfood,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Recommend_Adapter.RecommendViewHolder holder, int position) {
        Food food = this.listFood.get(position);
        if(food==null){
            return;
        }
        holder.progressBar_Food.setVisibility(View.VISIBLE);
        Picasso.get().load(food.getImage_Food()).into(holder.img_food);
        holder.txt_name_food.setText(food.getName_Food());
        holder.txt_prince_new.setText(food.getPrice_Food()+" vnd");
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icLickFood.Click_View_Food(food);
            }
        });
        holder.img_add_Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                icLickFood.Click_Add_Cart(food);
            }
        });
        holder.progressBar_Food.setVisibility(View.GONE);

    }

    @Override
    public int getItemCount() {
        if(listFood.size()!=0){
            return listFood.size();
        }
        return 0;
    }

    public class RecommendViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView txt_name_food,txt_prince_new,txt_prince_old;
        private ImageView img_add_Cart,img_food;
        private ProgressBar progressBar_Food;
        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_OnClick);
            txt_name_food = itemView.findViewById(R.id.txt_name_food);
            txt_prince_new = itemView.findViewById(R.id.txt_prince_new);
            img_food = itemView.findViewById(R.id.img_items_food);
            img_add_Cart  = itemView.findViewById(R.id.img_add_Cart);
            progressBar_Food = itemView.findViewById(R.id.progressBar_Food);
        }
    }
}
