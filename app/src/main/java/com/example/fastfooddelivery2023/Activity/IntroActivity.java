package com.example.fastfooddelivery2023.Activity;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.camera2.TotalCaptureResult;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Adapter.FunctionUser_Adapter;
import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.Advertisement;
import com.example.fastfooddelivery2023.Model.Comment;
import com.example.fastfooddelivery2023.Model.Comment_FB;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.FunctionUser;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.Model.VideoShort;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
private ImageView imageView;
    Food f = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        imageView = findViewById(R.id.imageView3);
        User user = DataPreferences.getUser(IntroActivity.this,"KEY_USER");

        final DatabaseReference dataVideo = FirebaseDatabase.getInstance().getReference("Video");
        List<VideoShort> list = new ArrayList<>();
        for(int i  = 1;i<=5;i++){
            list.add(new VideoShort(i,""));
        }
        for(VideoShort video : list){
            dataVideo.child(String.valueOf(video.getId())).setValue(video);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                User user = DataPreferences.getUser(IntroActivity.this,KEY_USER);
                if(user==null){
                    startActivity(new Intent(IntroActivity.this, Login_SignUpActivity.class));
                    finishAffinity();
                }else{
                    startActivity(new Intent(IntroActivity.this, MainActivity.class));
                    finishAffinity();
                }
            }
        },0);
    }




}