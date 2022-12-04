package com.example.fastfooddelivery2023.Activity;

import static com.example.fastfooddelivery2023.Activity.HistoryActivity.OBJECT_FOOD;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.R;
import com.squareup.picasso.Picasso;

public class EvaluateActivity extends AppCompatActivity {
private ImageView img_evaluate,img_return_evaluate;
private TextView tv_name_evaluate,tv_name_inf_evaluate,txt_total_evaluate,txt_evaluate;
private RatingBar ratingBar_evaluate;
private EditText edt_evaluate;
private Button btn_evaluate;
private int star = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate);
        initView();

        try {
         //   loadDataBundleFood();
            OnClickRatingBar();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void OnClickRatingBar(){
         ratingBar_evaluate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
             @Override
             public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                 int rating = (int) v;
                 String mess= " ";
                 star =  (int)ratingBar.getRating();
                 switch (rating){
                     case 1 : mess = "Bạn đã đánh giá 1 sao";
                     break;
                     case 2 : mess = "Bạn đã đánh giá 2 sao";
                         break;
                     case 3 : mess = "Bạn đã đánh giá 3 sao";
                         break;
                     case 4 : mess = "Bạn đã đánh giá 4 sao";
                         break;
                     case 5 : mess = "Bạn đã đánh giá 5 sao";
                         break;

                 }
                 txt_evaluate.setText(mess);
             }
         });
    }
    private void loadDataBundleFood(){
       Food food =(Food) getIntent().getSerializableExtra(OBJECT_FOOD);
       if(food==null) {
           return;
       }
       tv_name_evaluate.setText(food.getName_Food());
       tv_name_inf_evaluate.setText(food.getInformation_Food());
       txt_total_evaluate.setText(food.getPrice_Food()+" vnd");
       Picasso.get().load(food.getImage_Food()).into(img_evaluate);
    }

    private void initView(){
        img_evaluate = findViewById(R.id.img_evaluate);
        img_return_evaluate = findViewById(R.id.img_return_evaluate);
        tv_name_evaluate = findViewById(R.id.tv_name_evaluate);
        tv_name_inf_evaluate = findViewById(R.id.tv_name_inf_evaluate);
        txt_total_evaluate = findViewById(R.id.txt_total_evaluate);
        txt_evaluate = findViewById(R.id.txt_evaluate);
        ratingBar_evaluate = findViewById(R.id.ratingBar_evaluate);
        edt_evaluate = findViewById(R.id.edt_evaluate);
        btn_evaluate = findViewById(R.id.btn_evaluate);
    }
}