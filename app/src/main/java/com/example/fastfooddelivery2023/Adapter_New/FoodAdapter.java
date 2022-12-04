package com.example.fastfooddelivery2023.Adapter_New;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {
    private Context context;
    private List<Food> listFood;
    public interface ClickItemFood{
       void Click(Food food);
    }
    private ClickItemFood clickItemFood;

    public FoodAdapter(Context context, List<Food> listFood, ClickItemFood clickItemFood) {
        this.context = context;
        this.listFood = listFood;
        this.clickItemFood = clickItemFood;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food_new,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = this.listFood.get(position);
        if(food==null){
            return;
        }
        Picasso.get().load(food.getImage_Food()).into(holder.imageView_Food);
        holder.txt_name_food.setText(food.getName_Food());
        holder.txt_price_food.setText(food.getPrice_Food()+" đ");
        holder.relative_clickFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItemFood.Click(food);
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
        private ImageView imageView_Food;
        private TextView  txt_name_food,txt_price_food;
        private RelativeLayout relative_clickFood;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_Food = itemView.findViewById(R.id.imageView_Food);
            txt_name_food = itemView.findViewById(R.id.txt_name_food);
            txt_price_food = itemView.findViewById(R.id.txt_price_food);
            relative_clickFood  = itemView.findViewById(R.id.relative_clickFood);
        }
    }
}
