package com.example.fastfooddelivery2023.Adapter;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfooddelivery2023.Model.VideoShort;
import com.example.fastfooddelivery2023.R;

import java.util.List;

public class Video_Adapter extends RecyclerView.Adapter<Video_Adapter.ViewHolder> {
    private Context context;
    private List<VideoShort> listVideo;
    private ClickLikeVideo clickLikeVideo;
    public interface ClickLikeVideo{
        void ClickLike(VideoShort videoShort, TextView txt_like, ImageView imageView);
    }

    public Video_Adapter(Context context,List<VideoShort> list,ClickLikeVideo clickLikeVideo) {
        this.context = context;
        this.listVideo = list;
        this.clickLikeVideo = clickLikeVideo;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Video_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Video_Adapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Video_Adapter.ViewHolder holder, int position) {
        VideoShort video = this.listVideo.get(position);
        holder.txt_like.setText(video.getLike()+"");
       // holder.videoView.setVideoURI(Uri.parse(video.getVideo()));
        holder.videoView.setVideoPath(video.getVideo());
        holder.videoView.setMediaController(new MediaController(context));
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                holder.progressBar3.setVisibility(View.GONE);
                holder.videoView.requestFocus();
                holder.videoView.start();
            }
        });
        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                holder.videoView.start();
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.videoView.requestFocus();
                clickLikeVideo.ClickLike(video,holder.txt_like,holder.imageView);
            }
        });
    }

    @Override
    public int getItemCount() {
      if(listVideo.size()!=0){
          return listVideo.size();
      }
      return 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private VideoView videoView;
        private ProgressBar progressBar3;
        private ImageView imageView;
        private TextView txt_like;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.videoView);
            progressBar3 = itemView.findViewById(R.id.progressBar3);
            imageView = itemView.findViewById(R.id.imageview_like);
            txt_like = itemView.findViewById(R.id.txt_like);
        }
    }
}
