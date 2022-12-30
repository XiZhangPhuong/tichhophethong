package com.example.fastfooddelivery2023.Adapter_New;

import android.content.Context;
import android.text.TextUtils;
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

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private List<Food> listFood;


    public interface ClickSearchFood{
        void Click(Food food);
    }
    private ClickSearchFood clickSearchFood;

    public SearchAdapter(Context context, List<Food> listFood, ClickSearchFood clickSearchFood) {
        this.context = context;
        this.listFood = listFood;
        this.clickSearchFood = clickSearchFood;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_fastfood,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
       Food food = this.listFood.get(position);
       if(food==null){
           return;
       }
        Picasso.get().load(food.getImage_Food()).into(holder.imageView_Food);
        holder.txt_name_food.setText(food.getName_Food());
        holder.txt_category_food.setText(food.getCategory_Food());
        holder.txt_price_food.setText(food.getPrice_Food()+" đ");
        holder.relative_clickFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickSearchFood.Click(food);
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
        private TextView txt_name_food,txt_category_food,txt_price_food;
        private RelativeLayout relative_clickFood;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_Food = itemView.findViewById(R.id.imageView_Food);
            txt_name_food = itemView.findViewById(R.id.txt_name_food);
            txt_category_food = itemView.findViewById(R.id.txt_category_food);
            txt_price_food = itemView.findViewById(R.id.txt_price_food);
            relative_clickFood = itemView.findViewById(R.id.relative_clickFood);
        }
    }

    public void filterList(List<Food> filterList){
        listFood = filterList;
        notifyDataSetChanged();
    }


}
