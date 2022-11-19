package com.example.fastfooddelivery2023.Activity;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.Comment;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.Model.VideoShort;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {
private ImageView imageView;
private List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        imageView = findViewById(R.id.imageView3);

        //start service

        final DatabaseReference dataCMT = FirebaseDatabase.getInstance().getReference("Comment");
        List<Comment> listComment  = new ArrayList<>();
        listComment.add(new Comment("Food111111","Lê Thành Đạt ","Ngon bổ rẻ","11-7-2022 | 12:12",5));
        listComment.add(new Comment("Food111111","Lê Thu Phượng ","Quá là ngon","11-7-2022 | 12:12",5));
        listComment.add(new Comment("Food111111","Trương Đình Quyền ","Rẻ,hợp giá tiền","11-7-2022 | 12:12",5));

//        for(Comment cmt : list){
//            dataCMT.child(cmt.getIdFood()).child(cmt.getName()).setValue(cmt);
//        }
        final DatabaseReference dataFood = FirebaseDatabase.getInstance().getReference("Food");
        List<Food> listFood = new ArrayList<>();
        listFood.add(new Food("Food123456","Cơm chiên trứng",
                "https://i.ytimg.com/vi/6u5wA6YNw5Q/maxresdefault.jpg","Cơm",
                "Món này rất ngon",listComment,"3 like",1,45000));
        listFood.add(new Food("Food145236","Mì sảo hải sản",
                "https://i.ytimg.com/vi/Z-2DBXPsg2M/maxresdefault.jpg",
                "Mì","Món này rất ngon",listComment,"3 like",1,55000));
        listFood.add(new Food("Food123457","Trà sửa",
                "http://cdn.tgdd.vn/Files/2021/08/10/1374160/hoc-cach-pha-tra-sua-o-long-dai-loan-thom-ngon-chuan-vi-ai-cung-me-202108100039248020.jpg",
                "Đồ uống","Món này rất ngon",listComment,"3 like",1,25000));
        listFood.add(new Food("Food14785222","Khoai tây chiên",
                "https://www.thatlangon.com/wp-content/uploads/2020/06/cach-lam-khoai-tay-chien-8561img_5eda0ba306f26.jpg",
                "Ăn vặt","Món này rất ngon",listComment,"3 like",1,30000));
        listFood.add(new Food("Food9999991","Khoai tây chiên",
                "https://www.thatlangon.com/wp-content/uploads/2020/06/cach-lam-khoai-tay-chien-8561img_5eda0ba306f26.jpg",
                "Ăn vặt","Món này rất ngon",listComment,"3 like",1,30000));
        listFood.add(new Food("Food9999992","Khoai tây chiên",
                "https://www.thatlangon.com/wp-content/uploads/2020/06/cach-lam-khoai-tay-chien-8561img_5eda0ba306f26.jpg",
                "Ăn vặt","Món này rất ngon",listComment,"3 like",1,30000));
        listFood.add(new Food("Food9999993","Khoai tây chiên",
                "https://www.thatlangon.com/wp-content/uploads/2020/06/cach-lam-khoai-tay-chien-8561img_5eda0ba306f26.jpg",
                "Ăn vặt","Món này rất ngon",listComment,"3 like",1,30000));
        listFood.add(new Food("Food9999994","Khoai tây chiên",
                "https://www.thatlangon.com/wp-content/uploads/2020/06/cach-lam-khoai-tay-chien-8561img_5eda0ba306f26.jpg",
                "Ăn vặt","Món này rất ngon",listComment,"3 like",1,30000));
        listFood.add(new Food("Food9999995","Khoai tây chiên",
                "https://www.thatlangon.com/wp-content/uploads/2020/06/cach-lam-khoai-tay-chien-8561img_5eda0ba306f26.jpg",
                "Ăn vặt","Món này rất ngon",listComment,"3 like",1,30000));
//        for(Food f : listFood){
//            dataFood.child(f.getId_Food()).setValue(f);
//        }

        final DatabaseReference dataVideo = FirebaseDatabase.getInstance().getReference("VideoShort");
        List<VideoShort> listVideo=  new ArrayList<>();
//        listVideo.add(new VideoShort("Video12345","https://v16-webapp.tiktok.com/f74171d8b3ee891e3dff649390a70971/637525c8/video/tos/useast2a/tos-useast2a-pve-0037c001-aiso/411a3b128d034c8db54a0a76a89f1ba4/?a=1988&ch=0&cr=0&dr=0&lr=tiktok&cd=0%7C0%7C1%7C0&cv=1&br=2558&bt=1279&cs=0&ds=3&ft=kLO5qy-gZmo0Pu5hDBkVQI1xDiHKJdmC0&mime_type=video_mp4&qs=0&rc=NjZlaWhkNTs2OGg0aGQ0N0BpMzN0ajQ6ZnlxZzMzZjgzM0AyMzVeLi8vXy0xYWM1Yl9gYSNrY2I1cjRnM2tgLS1kL2Nzcw%3D%3D&l=202211161202180102440291841B0A1C60&btag=80000","VanPhuongn","Haland",3));
//        listVideo.add(new VideoShort("Video12345555","https://v16-webapp.tiktok.com/628c7a103c140f4b3c196ef23578d098/63754062/video/tos/useast2a/tos-useast2a-pve-0037-aiso/2514c9a1f0bf4c3e99079b3e072ecb9e/?a=1988&ch=0&cr=0&dr=0&lr=tiktok&cd=0%7C0%7C1%7C0&cv=1&br=1200&bt=600&cs=0&ds=3&ft=kLO5qy-gZmo0PthNDBkVQGcxDiHKJdmC0&mime_type=video_mp4&qs=0&rc=ZTQ1aTo2Njc1aTg8aTc7O0BpM29mbGY6Zjc8ZzMzZjgzM0AzYWMxYGNgXl8xNjUuYzIuYSNpZ2hrcjQwYjZgLS1kL2Nzcw%3D%3D&l=202211161356030102440770740E193F5E&btag=80000","VanPhuongn","Haland",3));
//        listVideo.add(new VideoShort("Video12345555","https://v16-webapp.tiktok.com/ff2f87aff9619b638c1d6910b7328ed2/637542c4/video/tos/useast2a/tos-useast2a-ve-0068c003/6b27f5fe83af4e15a6d8626577cb3423/?a=1988&ch=0&cr=0&dr=0&lr=tiktok_m&cd=0%7C0%7C1%7C0&cv=1&br=3376&bt=1688&cs=0&ds=3&ft=kLO5qy-gZmo0PNNNDBkVQwMxDiHKJdmC0&mime_type=video_mp4&qs=0&rc=ODVmOjg3N2Y2Zjk0NDozZUBpM3R1bDw6ZnV5ZjMzNzczM0AyLjAxMjYvNmMxYDBgLV5hYSNoaW9ucjQwZjVgLS1kMTZzcw%3D%3D&l=20221116140609010245144225231A68FE&btag=80000","VanPhuongn","Haland",3));
        for(VideoShort video : listVideo){
            dataVideo.child(video.getId()).setValue(video);
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
        },1000);
    }


}