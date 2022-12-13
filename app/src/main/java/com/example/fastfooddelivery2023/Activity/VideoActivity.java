package com.example.fastfooddelivery2023.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.example.fastfooddelivery2023.Adapter.Video_Adapter;
import com.example.fastfooddelivery2023.Model.VideoShort;
import com.example.fastfooddelivery2023.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VideoActivity extends AppCompatActivity {
private ImageView img_back;
private ViewPager2 viewPager2;
private Video_Adapter video_adapter;
private final DatabaseReference dataVideo = FirebaseDatabase.getInstance().getReference("Video");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        img_back = findViewById(R.id.img_back);
        viewPager2 = findViewById(R.id.viewPager2);
        try {
            setWindow();
            ClickBack();
            loadDataVideo();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void ClickBack(){
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private void loadDataVideo(){
        List<VideoShort> list = new ArrayList<>();
        list.add(new VideoShort(4,"android.resource://"+getPackageName()+"/"+R.raw.video4));
        list.add(new VideoShort(1,"android.resource://"+getPackageName()+"/"+R.raw.video1));
        list.add(new VideoShort(2,"android.resource://"+getPackageName()+"/"+R.raw.video2));
        list.add(new VideoShort(3,"android.resource://"+getPackageName()+"/"+R.raw.video3));
        list.add(new VideoShort(5,"android.resource://"+getPackageName()+"/"+R.raw.video5));
        video_adapter = new Video_Adapter(VideoActivity.this,list);
        viewPager2.setAdapter(video_adapter);
        video_adapter.notifyDataSetChanged();
    }
    private void setWindow(){
        if(Build.VERSION.SDK_INT>=21){
           Window window = VideoActivity.this.getWindow();
            window.setStatusBarColor(VideoActivity.this.getResources().getColor(R.color.black));
            window.setNavigationBarColor(VideoActivity.this.getResources().getColor(R.color.black));
        }
    }
}