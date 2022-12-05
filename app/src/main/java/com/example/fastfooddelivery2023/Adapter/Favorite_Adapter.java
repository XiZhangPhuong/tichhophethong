package com.example.fastfooddelivery2023.Adapter;

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

public class Favorite_Adapter extends RecyclerView.Adapter<Favorite_Adapter.ViewHolder> {
    private List<Food> listFood;
    public interface IClickFavorite{
        void ClickLike(Food food,ImageView img_favorite);
        void ClickView(Food food);
    }
    private IClickFavorite iClickFavorite;

    public Favorite_Adapter(List<Food> listFood, IClickFavorite iClickFavorite) {
        this.listFood = listFood;
        this.iClickFavorite = iClickFavorite;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Favorite_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Favorite_Adapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Favorite_Adapter.ViewHolder holder, int position) {
       Food food = this.listFood.get(position);
       if(food==null){
           return;
       }
       Picasso.get().load(food.getImage_Food()).into(holder.imageView_Food);
       holder.txt_name_food.setText(food.getName_Food());
       holder.txt_category_food.setText(food.getCategory_Food());
       holder.txt_price_food.setText(food.getPrice_Food()+"");
       holder.img_favorite.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               iClickFavorite.ClickLike(food,holder.img_favorite);
           }
       });
       holder.relative_clickFood.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               iClickFavorite.ClickView(food);
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
        private ImageView  imageView_Food,img_favorite;
        private TextView txt_name_food,txt_category_food,txt_price_food;
        private RelativeLayout relative_clickFood;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView_Food = itemView.findViewById(R.id.imageView_Food);
            img_favorite = itemView.findViewById(R.id.img_favorite);
            txt_name_food = itemView.findViewById(R.id.txt_name_food);
            txt_category_food = itemView.findViewById(R.id.txt_category_food);
            txt_price_food = itemView.findViewById(R.id.txt_price_food);
            relative_clickFood = itemView.findViewById(R.id.relative_clickFood);
        }
    }
}
