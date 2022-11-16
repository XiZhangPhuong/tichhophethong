package com.example.fastfooddelivery2023.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfooddelivery2023.Model.Category;
import com.example.fastfooddelivery2023.R;

import java.util.List;

public class Category_Adapter extends RecyclerView.Adapter<Category_Adapter.CategoryViewHolder> {
    private List<Category> listCate;
    public interface IClickCategory{
        void Click(Category category);
    }
    private IClickCategory iClickCategory;

    public Category_Adapter(List<Category> mListCate,Category_Adapter.IClickCategory iClickCategory){
        this.listCate = mListCate;
        this.iClickCategory = iClickCategory;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public Category_Adapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Category_Adapter.CategoryViewHolder holder, int position) {
        Category category = listCate.get(position);
        if(category==null){
            return;
        }
        holder.image_Category.setImageResource(Integer.parseInt(category.getImage_Category()));
        holder.name_Category.setText(category.getName_Category());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickCategory.Click(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(listCate.size()!=0){
            return listCate.size();
        }
        return 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView image_Category;
        private TextView name_Category;
        private LinearLayout linearLayout;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            image_Category = itemView.findViewById(R.id.img_category);
            name_Category = itemView.findViewById(R.id.txt_name_category);
            linearLayout = itemView.findViewById(R.id.linear_Category);
        }
    }
}
