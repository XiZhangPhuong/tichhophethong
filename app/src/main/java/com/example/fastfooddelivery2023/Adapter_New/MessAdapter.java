package com.example.fastfooddelivery2023.Adapter_New;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfooddelivery2023.R;

import java.util.List;

public class MessAdapter extends RecyclerView.Adapter<MessAdapter.ViewHolder> {
    private Context context;
    private List<String> listMess;

    public MessAdapter(Context context, List<String> listMess) {
        this.context = context;
        this.listMess = listMess;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mess,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessAdapter.ViewHolder holder, int position) {
        String str = this.listMess.get(position);
        holder.txt_mess.setText(str);
    }

    @Override
    public int getItemCount() {
        if(listMess.size()!=0){
            return listMess.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_mess;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_mess = itemView.findViewById(R.id.txt_mess);
        }
    }
}
