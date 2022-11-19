package com.example.fastfooddelivery2023.Adapter;

import android.graphics.Paint;
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
import com.squareup.picasso.Picasso;

import java.util.List;

public class Sale_Adapter extends RecyclerView.Adapter<Sale_Adapter.ViewHolder> {
    private List<Food> listFood;
    private ICLickFood icLickFood;
    public interface ICLickFood{
        void Click_Add_Cart(Food food);
        void Click_View_Food(Food food);
    }

    public Sale_Adapter(List<Food> listFood, ICLickFood icLickFood) {
        this.listFood = listFood;
        this.icLickFood = icLickFood;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Sale_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Sale_Adapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fastfood,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Sale_Adapter.ViewHolder holder, int position) {
        Food food = this.listFood.get(position);
        if(food==null){
            return;
        }
        Picasso.get().load(food.getImage_Food()).into(holder.img_food);
        holder.txt_name_food.setText(food.getName_Food());
        holder.txt_prince_new.setText(food.getPrice_Food()+"");
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
    }

    @Override
    public int getItemCount() {
        if(listFood.size()!=0){
            return listFood.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        private TextView txt_name_food,txt_prince_new,txt_prince_old;
        private ImageView img_add_Cart,img_food;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout = itemView.findViewById(R.id.linear_OnClick);
            txt_name_food = itemView.findViewById(R.id.txt_name_food);
            txt_prince_new = itemView.findViewById(R.id.txt_prince_new);
            img_food = itemView.findViewById(R.id.img_items_food);
            img_add_Cart  = itemView.findViewById(R.id.img_add_Cart);
        }
    }
}
