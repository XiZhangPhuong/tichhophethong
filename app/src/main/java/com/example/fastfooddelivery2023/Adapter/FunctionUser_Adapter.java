package com.example.fastfooddelivery2023.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfooddelivery2023.Model.FunctionUser;
import com.example.fastfooddelivery2023.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FunctionUser_Adapter extends RecyclerView.Adapter<FunctionUser_Adapter.ViewHolder> {
    private List<FunctionUser> list;
    public interface ClickFunction{
        void Click(FunctionUser functionUser);
    }
    private ClickFunction clickFunction;

    public FunctionUser_Adapter(List<FunctionUser> list, ClickFunction clickFunction) {
        this.list = list;
        this.clickFunction = clickFunction;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FunctionUser_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FunctionUser_Adapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_function_user,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionUser_Adapter.ViewHolder holder, int position) {
        FunctionUser fun = this.list.get(position);
        if(fun==null){
            return;
        }
        Picasso.get().load(fun.getImage()).into(holder.img_function_user);
        holder.txt_function_user.setText(fun.getName());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickFunction.Click(fun);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(list.size()!=0){
            return list.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout relativeLayout;
        private ImageView img_function_user;
        private TextView txt_function_user;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img_function_user = itemView.findViewById(R.id.img_function_user);
            txt_function_user = itemView.findViewById(R.id.txt_function_user);
            relativeLayout  = itemView.findViewById(R.id.relative_function_user);
        }
    }
}
