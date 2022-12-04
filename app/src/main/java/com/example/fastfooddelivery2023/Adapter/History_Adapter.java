package com.example.fastfooddelivery2023.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.Order_FB;
import com.example.fastfooddelivery2023.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class History_Adapter extends RecyclerView.Adapter<History_Adapter.HistoryViewHolder>  {
    private Context context;
    private List<Food> listFood;
    public interface ClickEvaluateFood{
        void Click(Food food);
    }
    private ClickEvaluateFood clickEvaluateFood;

    public History_Adapter(Context context, List<Food> listFood, ClickEvaluateFood clickEvaluateFood) {
        this.context = context;
        this.listFood = listFood;
        this.clickEvaluateFood = clickEvaluateFood;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public History_Adapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_fastfood,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull History_Adapter.HistoryViewHolder holder, int position) {
         Food food = this.listFood.get(position);
         Picasso.get().load(food.getImage_Food()).into(holder.imageView_Food);
         holder.txt_name_food.setText(food.getName_Food());
         holder.txt_category_food.setText(food.getCategory_Food());
         holder.txt_price_food.setText(food.getPrice_Food()+"");
         holder.btn_evaluate.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 clickEvaluateFood.Click(food);
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

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView_Food;
        private TextView txt_name_food,txt_category_food,txt_price_food,txt_evaluate_history_food;
        private Button btn_evaluate;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_Food = itemView.findViewById(R.id.imageView_Food);
            txt_name_food = itemView.findViewById(R.id.txt_name_food);
            txt_category_food = itemView.findViewById(R.id.txt_category_food);
            txt_price_food = itemView.findViewById(R.id.txt_price_food);
            btn_evaluate = itemView.findViewById(R.id.btn_evaluate);
        }
    }
}
