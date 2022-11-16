package com.example.fastfooddelivery2023.Adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class History_Adapter extends RecyclerView.Adapter<History_Adapter.HistoryViewHolder>  {
    @NonNull
    @Override
    public History_Adapter.HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull History_Adapter.HistoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
