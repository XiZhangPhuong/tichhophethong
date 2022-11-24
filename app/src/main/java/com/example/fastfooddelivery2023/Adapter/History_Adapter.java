package com.example.fastfooddelivery2023.Adapter;

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
import com.example.fastfooddelivery2023.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class History_Adapter extends RecyclerView.Adapter<History_Adapter.HistoryViewHolder>  {
    List<Food> listFood;
    public interface ClickEvaluateFood{
        void Click(Food food);
    }
    private ClickEvaluateFood clickEvaluateFood;

    public History_Adapter(List<Food> listFood, ClickEvaluateFood clickEvaluateFood) {
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
         Picasso.get().load(food.getImage_Food()).into(holder.img_history_food);
         holder.txt_name_history_food.setText(food.getName_Food());
         holder.txt_quantity_history_food.setText("Số lượng : "+food.getQuantity());
         holder.txt_prince_history_food.setText(food.getPrice_Food()+" vnđ");
         holder.txt_evaluate_history_food.setText("Chưa đánh giá");
         holder.btn_evaluate_history_food.setOnClickListener(new View.OnClickListener() {
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
        private ImageView img_history_food;
        private TextView txt_name_history_food,txt_quantity_history_food,txt_prince_history_food,txt_evaluate_history_food;
        private Button btn_evaluate_history_food;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            img_history_food = itemView.findViewById(R.id.img_history_food);
            txt_name_history_food = itemView.findViewById(R.id.txt_name_history_food);
            txt_quantity_history_food = itemView.findViewById(R.id.txt_quantity_history_food);
            txt_prince_history_food = itemView.findViewById(R.id.txt_prince_history_food);
            txt_evaluate_history_food = itemView.findViewById(R.id.txt_evaluate_history_food);
            btn_evaluate_history_food = itemView.findViewById(R.id.btn_evaluate_history_food);
        }
    }
}
