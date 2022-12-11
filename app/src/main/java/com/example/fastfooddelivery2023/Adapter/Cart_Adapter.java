package com.example.fastfooddelivery2023.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfooddelivery2023.Control.TEMPS;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Cart_Adapter extends RecyclerView.Adapter<Cart_Adapter.CartViewHolder> {
    private List<Food> mList;
    private IClickCart mIClickCart;
    public interface IClickCart {
        void ClickPlusCart(TextView tv_number_cart,TextView tv_total_item, Food food);
        void ClickMinusCart(TextView tv_number_cart, TextView tv_total_item, Food food);
        void ClickImage(Food food);
    }

    public Cart_Adapter(List<Food> mList, IClickCart mIClickCart) {
        this.mList = mList;
        this.mIClickCart = mIClickCart;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Cart_Adapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CartViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Cart_Adapter.CartViewHolder holder, int position) {
        Food food = mList.get(position);
        if(food==null){
            return;
        }
        Picasso.get().load(food.getImage_Food()).into(holder.img_cart_photo);
        holder.tv_name_cart.setText(food.getName_Food());
        holder.tv_number_cart.setText(String.valueOf(food.getQuantity()));
        holder.tv_total_item.setText(String.valueOf(food.getPrice_Food()));
        holder.tv_name_inf_cart.setText(food.getCategory_Food());
        holder.imb_minus_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickCart.ClickMinusCart(holder.tv_number_cart, holder.tv_total_item, food);
            }
        });
        holder.imb_plus_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIClickCart.ClickPlusCart(holder.tv_number_cart, holder.tv_total_item, food);
            }
        });
        holder.img_cart_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIClickCart.ClickImage(food);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mList.size()!=0){
            return mList.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_cart_photo;
        private TextView tv_name_cart,tv_total_item,tv_number_cart,tv_name_inf_cart;
        private ImageView imb_minus_cart,imb_plus_cart,imb_delete;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            img_cart_photo = itemView.findViewById(R.id.img_cart_photo);
            tv_name_cart = itemView.findViewById(R.id.tv_name_cart);
            tv_total_item = itemView.findViewById(R.id.tv_total_item);
            tv_number_cart = itemView.findViewById(R.id.tv_number_cart);
            imb_minus_cart = itemView.findViewById(R.id.imb_minus_cart);
            imb_plus_cart = itemView.findViewById(R.id.imb_plus_cart);
            tv_name_inf_cart = itemView.findViewById(R.id.tv_name_inf_cart);

        }
    }
}
