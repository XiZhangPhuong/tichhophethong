package com.example.fastfooddelivery2023.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fastfooddelivery2023.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdminActivity extends AppCompatActivity {
    private ImageView img_return, img_sent;
    private CircleImageView profile_image, img_call;
    private WebView webView;
    private TextView txt_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_admin);
        try {
            setWindow();
            initView();
            back();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        img_return = findViewById(R.id.img_return);
        img_call = findViewById(R.id.img_call);
        Picasso.get().load("https://assets.stickpng.com/images/5a452601546ddca7e1fcbc87.png").into(img_call);
        profile_image = findViewById(R.id.profile_image);
        img_sent = findViewById(R.id.img_sent);
        Picasso.get().load("https://cdn-icons-png.flaticon.com/512/60/60525.png").into(img_sent);
        Picasso.get().load("https://assets.goal.com/v3/assets/bltcc7a7ffd2fbf71f5/blt02b53380eb3edd11/62b192221b51ad0fbb595cae/GHP_HAALAND-CITY_2-1.jpg?auto=webp&fit=crop&format=jpg&height=800&quality=60&width=1200").into(profile_image);
        txt_name = findViewById(R.id.txt_name);

    }



    private void back() {
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setWindow() {

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = ContactAdminActivity.this.getWindow();
            window.setStatusBarColor(ContactAdminActivity.this.getResources().getColor(android.R.color.holo_blue_dark));
            window.setNavigationBarColor(ContactAdminActivity.this.getResources().getColor(R.color.white));
        }
    }


}