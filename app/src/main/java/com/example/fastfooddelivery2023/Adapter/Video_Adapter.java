package com.example.fastfooddelivery2023.Adapter;

import android.content.Context;
import android.media.Image;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfooddelivery2023.Model.VideoShort;
import com.example.fastfooddelivery2023.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Video_Adapter extends RecyclerView.Adapter<Video_Adapter.ViewHolder> {
    private Context context;
    private List<VideoShort> listVideo;

    public Video_Adapter(Context context, List<VideoShort> listVideo) {
        this.context = context;
        this.listVideo = listVideo;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Video_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Video_Adapter.ViewHolder holder, int position) {
        VideoShort video = this.listVideo.get(position);
        if(video==null){
            return;
        }
        Uri uri = Uri.parse(video.getUrl());
        holder.videoView_tiktok.setVideoURI(uri);
        holder.videoView_tiktok.setMediaController(new MediaController(context));
        holder.txt_music_tiktok.setSelected(true);
        holder.videoView_tiktok.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                holder.progressBar2.setVisibility(View.GONE);
                holder.videoView_tiktok.requestFocus();
                holder.videoView_tiktok.start();
            }
        });
        holder.videoView_tiktok.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                 holder.videoView_tiktok.start();
            }
        });
        Picasso.get().load("https://wallpaperaccess.com/full/1792305.jpg").into(holder.imageView);
        Picasso.get().load("https://www.incimages.com/uploaded_files/image/1920x1080/getty_626660256_2000108620009280158_388846.jpg").into(holder.img_music_tiktok);
        Picasso.get().load("https://assets.goal.com/v3/assets/bltcc7a7ffd2fbf71f5/blt7c729626ed5de52a/62a721dd40d1742cc37e6ed6/Erling_Haaland_Manchester_City_2022-23.jpg").into(holder.img_avatar_tiktok);
     //   Picasso.get().load("https://image.shutterstock.com/image-vector/comment-icon-260nw-228218761.jpg").into(holder.imageView_comment);
       // Picasso.get().load("https://cdn4.vectorstock.com/i/1000x1000/45/38/arrow-share-icon-vector-22894538.jpg").into(holder.imageView_share);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                holder.img_music_tiktok.animate().rotationBy(360).withEndAction(this).setDuration(10000)
                        .setInterpolator(new LinearInterpolator()).start();
            }
        };
        holder.img_music_tiktok.animate().rotationBy(360).withEndAction(runnable).setDuration(10000)
                .setInterpolator(new LinearInterpolator()).start();
    }

    @Override
    public int getItemCount() {
        if(listVideo.size()!=0){
            return listVideo.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar2;
        private VideoView videoView_tiktok;
        private ImageView imageView_like,imageView,imageView_share,imageView_comment;
        private TextView txt_music_tiktok;
        private CircleImageView img_avatar_tiktok,img_music_tiktok;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar2 = itemView.findViewById(R.id.progressBar2);
            imageView_like = itemView.findViewById(R.id.imageView_like);
            imageView = itemView.findViewById(R.id.imageView);
            img_avatar_tiktok = itemView.findViewById(R.id.img_avatar_tiktok);
            img_music_tiktok = itemView.findViewById(R.id.img_music_tiktok);
            videoView_tiktok = itemView.findViewById(R.id.videoView_tiktok);
            txt_music_tiktok = itemView.findViewById(R.id.txt_music_tiktok);
            imageView_share = itemView.findViewById(R.id.imageView_share);
            imageView_comment = itemView.findViewById(R.id.imageView_comment);
        }
    }
}
