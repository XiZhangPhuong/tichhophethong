package com.example.fastfooddelivery2023.Adapter_New;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfooddelivery2023.R;

import java.util.List;

public class RecommendMessAdapter extends RecyclerView.Adapter<RecommendMessAdapter.ViewHolder> {
    private Context context;
    private List<String> list;
    public interface ClickItems{
        void Click(String string);
    }
    private ClickItems clickItems;

    public RecommendMessAdapter(Context context, List<String> list, ClickItems clickItems) {
        this.context = context;
        this.list = list;
        this.clickItems = clickItems;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecommendMessAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend_mess,parent,false);
       return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendMessAdapter.ViewHolder holder, int position) {
       String str = this.list.get(position);
       if(str==null){
           return;
       }
       holder.txt_mess.setText(str);
       holder.view_click.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               clickItems.Click(str);
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
        private RelativeLayout  view_click;
        private TextView  txt_mess;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);;
            txt_mess = itemView.findViewById(R.id.txt_mess);
            view_click = itemView.findViewById(R.id.view_click);
        }
    }
}
