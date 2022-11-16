package com.example.fastfooddelivery2023.Dialog;

import static com.example.fastfooddelivery2023.Fragment.HomeFragment.OBJECT_FOOD;
import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Control.TEMPS;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetDialogFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
  private View mView;
  private Food food;
  private ImageView img_back,img_getFood,img_likeFood;
  private TextView txt_getNameFood,txt_getInfFood,txt_getPriceFood,txt_getLikeFood,txt_likeComment;
  private TextView txt_km_Food;
  private Button btn_addTOCart;

  private Boolean flag = true;
  private Boolean flag_like = true;
  private int count_Like = 0;
  private int quantity = 0;
  public static List<Food> listCart = new ArrayList<>();
  public static List<Food> listFavorite = new ArrayList<>();

@Override
   public void onCreate(@Nullable Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      Bundle bundle = getArguments();
       if(bundle!=null){
         food = (Food) bundle.get(OBJECT_FOOD);

    }
}

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        mView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_bottom_sheet_dialog,null);
        bottomSheetDialog.setContentView(mView);


        User user = DataPreferences.getUser(getContext(),KEY_USER);


        initView(mView);
        setDataFood();
        likeComment();


        // dialog dismiss on click img_back
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });

        btn_addTOCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listCart.size()==0){
                    btn_addTOCart.setText("Thêm giỏ hàng");
                    btn_addTOCart.setText("Dã thêm");
                    listCart.add(food);
                    bottomSheetDialog.dismiss();
                    Toast.makeText(getContext(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                    DataPreferences.setListFood(getContext(),listCart,String.valueOf(user.getId()));
                }else{
                    btn_addTOCart.setText("Dã thêm");
                    Toast.makeText(getContext(),"Đã có trong giỏ hàng",Toast.LENGTH_SHORT).show();
                    bottomSheetDialog.dismiss();

                }
            }
        });

        // count like
        img_likeFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==true){
                    img_likeFood.setImageResource(R.drawable.ic_like_24);
                    flag = false;
                    count_Like = count_Like + 1;
                    txt_getLikeFood.setText(count_Like+" like");
                    Toast.makeText(getContext(),"Đã thêm vào mục yêu thích",Toast.LENGTH_SHORT).show();

                }else{
                    img_likeFood.setImageResource(R.drawable.ic_favorite_24);
                    flag = true;
                    count_Like = count_Like - 1;
                    txt_getLikeFood.setText(count_Like+" like");
                    Toast.makeText(getContext(),"Bạn đã xóa",Toast.LENGTH_SHORT).show();

                }
            }
        });


        return bottomSheetDialog;

    }

    public static BottomSheetDialogFragment newInstance(Food food){
    BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheetDialogFragment();
    Bundle bundle = new Bundle();
    bundle.putSerializable(OBJECT_FOOD,food);
    bottomSheetDialogFragment.setArguments(bundle);
    return bottomSheetDialogFragment;
  }


   private void setDataFood(){
       img_getFood.setImageResource(Integer.parseInt(food.getImage_Food()));
       txt_getNameFood.setText(food.getName_Food());
       txt_getInfFood.setText(food.getInformation_Food());
       txt_getLikeFood.setText(food.getLike());
       txt_getPriceFood.setText(food.getPrice_Food()+" VND");
   }

          private void likeComment() {
              txt_likeComment.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      if (flag_like == true) {
                          txt_likeComment.setTextColor(Color.parseColor("#0066ff"));
                          txt_likeComment.setTypeface(txt_likeComment.getTypeface(), Typeface.BOLD);
                          txt_likeComment.setText("Hủy thích");
                          flag_like = false;
                      } else {
                          txt_likeComment.setTextColor(Color.parseColor("#000000"));
                          txt_likeComment.setTypeface(txt_likeComment.getTypeface(), Typeface.NORMAL);
                          txt_likeComment.setText("Thích");
                          flag_like = true;
                      }
                  }
              });

          }

          private void initView(View view) {
              img_back = view.findViewById(R.id.img_return);
              img_getFood = view.findViewById(R.id.img_getImageFood);
              img_likeFood = view.findViewById(R.id.img_like_Food);
              txt_getNameFood = view.findViewById(R.id.txt_getNameFood);
              txt_getInfFood = view.findViewById(R.id.txt_getInForFood);
              txt_getLikeFood = view.findViewById(R.id.txt_getLike);
              txt_getPriceFood = view.findViewById(R.id.txt_getPriceFood);
              txt_likeComment = view.findViewById(R.id.txt_likeComment);
              btn_addTOCart = view.findViewById(R.id.btn_addTOCart);
              txt_km_Food = view.findViewById(R.id.txt_km_Food);
          }


}