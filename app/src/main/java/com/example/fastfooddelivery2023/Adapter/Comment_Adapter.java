package com.example.fastfooddelivery2023.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfooddelivery2023.Model.Comment;
import com.example.fastfooddelivery2023.Model.Comment_FB;
import com.example.fastfooddelivery2023.R;

import java.util.List;

public class Comment_Adapter extends RecyclerView.Adapter<Comment_Adapter.CommentViewHolder> {
    private List<Comment_FB> listComment;


    public Comment_Adapter(List<Comment_FB> listComment) {
        this.listComment = listComment;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Comment_Adapter.CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CommentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cmt_food,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Comment_Adapter.CommentViewHolder holder, int position) {
       Comment_FB cmt = this.listComment.get(position);
       holder.txt_name_Comment.setText(cmt.getUser().getFullName());
       holder.txt_Comment.setText(cmt.getInformation());
       holder.txt_date_cmt.setText(cmt.getDate());
       holder.ratingBar.setRating(cmt.getLike());
      // holder.ratingBar.setNumStars(cmt.getLike());
    }

    @Override
    public int getItemCount() {
        if(listComment.size()!=0){
            return listComment.size();
        }
        return 0;
    }


    public class CommentViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_name_Comment,txt_Comment,txt_date_cmt,txt_likeComment,txt_repcmt,txt_countLikeCMT;
        private RatingBar ratingBar;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name_Comment = itemView.findViewById(R.id.txt_name_Comment);
            txt_Comment = itemView.findViewById(R.id.txt_Comment);
            txt_date_cmt = itemView.findViewById(R.id.txt_date_cmt);
            txt_likeComment = itemView.findViewById(R.id.txt_likeComment);
            txt_repcmt = itemView.findViewById(R.id.txt_repcmt);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }


}
