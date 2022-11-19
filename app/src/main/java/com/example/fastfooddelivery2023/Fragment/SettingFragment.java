package com.example.fastfooddelivery2023.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fastfooddelivery2023.Adapter.Video_Adapter;
import com.example.fastfooddelivery2023.Model.VideoShort;
import com.example.fastfooddelivery2023.Notification.Notification_Class;
import com.example.fastfooddelivery2023.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SettingFragment extends Fragment {
    private View mView;
    private ViewPager2 viewPager2;
    private Video_Adapter video_adapter ;
    private List<VideoShort> listVideo = new ArrayList<>();
    private boolean flag = true;
    private int like = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_setting, container, false);
        viewPager2 = mView.findViewById(R.id.view_pager_video);
        getDataVideoFromFireBase();
        return mView;
    }

    private void getDataVideoFromFireBase(){
        final DatabaseReference dataVideo = FirebaseDatabase.getInstance().getReference("VideoShort");
        dataVideo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listVideo.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    VideoShort video = ds.getValue(VideoShort.class);
                    listVideo.add(video);
                }
                video_adapter = new Video_Adapter(getContext(), listVideo, new Video_Adapter.ClickLikeVideo() {
                    @Override
                    public void ClickLike(VideoShort videoShort, TextView txt_like, ImageView imageView) {
                        if(flag==true){
                            imageView.setImageResource(R.drawable.ic_baseline_favorite_50_red);
                            like++;
                            txt_like.setText(like+" ");
                            flag = false;
                        }else{
                            imageView.setImageResource(R.drawable.ic_baseline_favorite_50_white);
                            like--;
                            txt_like.setText(like+"");
                            flag = true;
                        }
                    }
                });
                viewPager2.setAdapter(video_adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}