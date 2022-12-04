package com.example.fastfooddelivery2023.Dialog;

import static com.example.fastfooddelivery2023.Control.TEMPS.checkPlaceOrder;
import static com.example.fastfooddelivery2023.Control.TEMPS.convertBase64ToImage;
import static com.example.fastfooddelivery2023.Control.TEMPS.convertImage_toBase64;
import static com.example.fastfooddelivery2023.Fragment.HomeFragment.OBJECT_FOOD;
import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Adapter.Comment_Adapter;
import com.example.fastfooddelivery2023.Control.TEMPS;
import com.example.fastfooddelivery2023.Model.Comment;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BottomSheetDialogFragment extends com.google.android.material.bottomsheet.BottomSheetDialogFragment {
  private View mView;
  private Food food;
  private ImageView img_back,img_getFood,img_likeFood;
  private TextView txt_getNameFood,txt_getInfFood,txt_getPriceFood,txt_getLikeFood,txt_likeComment;
  private TextView txt_km_Food;
  private Button btn_addTOCart;
  private DatabaseReference dataCart = FirebaseDatabase.getInstance().getReference("Cart");
  private DatabaseReference dataTotal = FirebaseDatabase.getInstance().getReference("TotalFood");
  private DatabaseReference dataLike = FirebaseDatabase.getInstance().getReference("Favorite");
  private Boolean flag = true;
  private Boolean flag_like = true;
  private int count_Like = 0;
  private int quantity = 0;
  private Comment_Adapter comment_adapter;
  public static List<Food> listCart = new ArrayList<>();
  public static List<Food> listFavorite = new ArrayList<>();
  private RecyclerView rcv_comment_Food;
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
                if(checkPlaceOrder(food.getId_Food(),listCart)){
                    Toast.makeText(getContext(),"Đã có trong giỏ hàng",Toast.LENGTH_SHORT).show();
                    bottomSheetDialog.dismiss();
                    return;
                }
                    bottomSheetDialog.dismiss();
                    Toast.makeText(getContext(),"Thêm thành công",Toast.LENGTH_SHORT).show();
                    dataCart.child(user.getId()).child(food.getId_Food()).setValue(food);

            }
        });

        // count like
        img_likeFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag==true){
                    img_likeFood.setImageResource(R.drawable.ic_like_24);
                    count_Like = count_Like + 1;
                    txt_getLikeFood.setText(count_Like+" like");
                    Toast.makeText(getContext(),"Đã thêm vào mục yêu thích",Toast.LENGTH_SHORT).show();
                    dataLike.child(user.getId()).child(food.getId_Food()).setValue(food);
                    flag = false;
                    dataLike.child(user.getId()).child(food.getId_Food()).setValue(food);

                }else{
                    img_likeFood.setImageResource(R.drawable.ic_favorite_24);
                    count_Like = count_Like - 1;
                    txt_getLikeFood.setText(count_Like+" like");
                    Toast.makeText(getContext(),"Bạn đã xóa",Toast.LENGTH_SHORT).show();
                    dataLike.child(user.getId()).child(food.getId_Food()).removeValue();
                    flag = true;
                    dataLike.child(user.getId()).child(food.getId_Food()).removeValue();
                }
            }
        });


        return bottomSheetDialog;

    }

    public static BottomSheetDialogFragment newInstance(Food food){
    BottomSheetDialogFragment bottomSheetDialogFragment = new BottomSheetDialogFragment();
//    Bundle bundle = new Bundle();
//    bundle.putSerializable(OBJECT_FOOD,food);
//    bottomSheetDialogFragment.setArguments(bundle);
    return bottomSheetDialogFragment;
  }


   private void setDataFood(){
       Picasso.get().load(food.getImage_Food()).into(img_getFood);
       txt_getNameFood.setText(food.getName_Food());
       txt_getInfFood.setText(food.getInformation_Food());
       txt_getLikeFood.setText(food.getLike());
       txt_getPriceFood.setText(food.getPrice_Food()+" VND");

       comment_adapter = new Comment_Adapter(food.getListComment(), new Comment_Adapter.ClickCmtFood() {
           @Override
           public void ClickLike(Comment comment) {

           }
       });
       LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
       rcv_comment_Food.setLayoutManager(linearLayoutManager);
       rcv_comment_Food.setAdapter(comment_adapter);
       rcv_comment_Food.setHasFixedSize(true);
       comment_adapter.notifyDataSetChanged();
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
              rcv_comment_Food = view.findViewById(R.id.rcv_comment_Food);
          }


}