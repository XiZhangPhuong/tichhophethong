package com.example.fastfooddelivery2023.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.fastfooddelivery2023.Adapter_New.MessAdapter;
import com.example.fastfooddelivery2023.Adapter_New.RecommendMessAdapter;
import com.example.fastfooddelivery2023.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdminActivity extends AppCompatActivity {
    private ImageView img_return, img_sent;
    private CircleImageView profile_image, img_call;
    private MessAdapter messAdapter;
    private RecommendMessAdapter recommendMessAdapter;
    private RecyclerView rcv_mess,rcv_recommend;
    private TextView txt_name;
    private EditText edt_input;
    private ScrollView scrollView;
    private   List<String> list = new ArrayList<>();;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_admin);
        try {
            setWindow();
            initView();
            callAdmin();
            loadDataMess();
            loadData_Recommend();
            back();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void callAdmin(){
        if (ContextCompat.checkSelfPermission(ContactAdminActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ContactAdminActivity.this,new String[]{Manifest.permission.CALL_PHONE},100);

            img_call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ContactAdminActivity.this);
                    builder.setTitle("Thông báo")
                            .setMessage("Xác nhận gọi Admin ? ")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(Intent.ACTION_CALL);
                                    intent.setData(Uri.parse("tel:"+"0123456784"));
                                    startActivity(intent);
                                }
                            }).setNegativeButton("No",null).create().show();
                }
            });
        }

    }
    private void initView() {
        scrollView = findViewById(R.id.scrollView);
        img_return = findViewById(R.id.img_return);
        img_call = findViewById(R.id.img_call);
        Picasso.get().load("https://assets.stickpng.com/images/5a452601546ddca7e1fcbc87.png").into(img_call);
        profile_image = findViewById(R.id.profile_image);
        img_sent = findViewById(R.id.img_sent);
        Picasso.get().load("https://cdn-icons-png.flaticon.com/512/60/60525.png").into(img_sent);
        Picasso.get().load("https://assets.goal.com/v3/assets/bltcc7a7ffd2fbf71f5/blt02b53380eb3edd11/62b192221b51ad0fbb595cae/GHP_HAALAND-CITY_2-1.jpg?auto=webp&fit=crop&format=jpg&height=800&quality=60&width=1200").into(profile_image);
        txt_name = findViewById(R.id.txt_name);
        rcv_mess = findViewById(R.id.rcv_mess);
        edt_input = findViewById(R.id.edt_input);
        rcv_recommend = findViewById(R.id.rcv_recommend);

        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ContactAdminActivity.this,LinearLayoutManager.VERTICAL,false);
       // linearLayoutManager.setReverseLayout(true);
        rcv_mess.setLayoutManager(linearLayoutManager);
        rcv_mess.setHasFixedSize(true);
        messAdapter = new MessAdapter(ContactAdminActivity.this,list);
        rcv_mess.setAdapter(messAdapter);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(View.FOCUS_DOWN);
            }
        });
    }
    private void loadData_Recommend(){
        List<String > listRcm = new ArrayList<>();
        listRcm.add("Kiểm tra đơn hàng");
        listRcm.add("Liên hệ vận chuyển");
        listRcm.add("Trả tiền/Hoàn tiền");
        listRcm.add("Hủy đơn hàng");
        listRcm.add("Đổi số điện thoại đăng ký");
        listRcm.add("Ưu đãi hót");
        rcv_recommend.setLayoutManager(new LinearLayoutManager(ContactAdminActivity.this,LinearLayoutManager.HORIZONTAL,false));
        rcv_recommend.setHasFixedSize(true);
        recommendMessAdapter = new RecommendMessAdapter(ContactAdminActivity.this, listRcm, new RecommendMessAdapter.ClickItems() {
            @Override
            public void Click(String string) {
                 list.add(string);
                 messAdapter.notifyDataSetChanged();
            }
        });
        rcv_recommend.setAdapter(recommendMessAdapter);

    }
    private void loadDataMess(){


         img_sent.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(edt_input.length()>0){
                     String mess = edt_input.getText().toString().trim();
                     list.add(mess);
                     edt_input.setText("");
                     edt_input.requestFocus();
                     messAdapter.notifyDataSetChanged();
                     scrollView.post(new Runnable() {
                         @Override
                         public void run() {
                             scrollView.fullScroll(View.FOCUS_DOWN);
                         }
                     });
                 }
             }
         });
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