package com.example.fastfooddelivery2023.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
        void ClickAddToCart(Food food,ImageView img_check);
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
       holder.txt_name_favorite.setText(food.getName_Food());
       holder.txt_prince_favorite.setText(food.getPrice_Food()+"");
       Picasso.get().load(food.getImage_Food()).into(holder.img_items_favorite);
       holder.img_favorite.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               iClickFavorite.ClickLike(food,holder.img_favorite);
           }
       });
       holder.img_add_Cart_favorite.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               iClickFavorite.ClickAddToCart(food,holder.img_add_Cart_favorite);
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
        private ImageView  img_items_favorite,img_favorite,img_add_Cart_favorite;
        private TextView txt_name_favorite,txt_prince_favorite;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_items_favorite = itemView.findViewById(R.id.img_items_favorite);
            img_favorite = itemView.findViewById(R.id.img_favorite);
            img_add_Cart_favorite = itemView.findViewById(R.id.img_add_Cart_favorite);
            txt_name_favorite = itemView.findViewById(R.id.txt_name_favorite);
            txt_prince_favorite = itemView.findViewById(R.id.txt_prince_favorite);
        }
    }
}
