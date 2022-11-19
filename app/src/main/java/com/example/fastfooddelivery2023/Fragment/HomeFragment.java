package com.example.fastfooddelivery2023.Fragment;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.fastfooddelivery2023.Adapter.Category_Adapter;
import com.example.fastfooddelivery2023.Adapter.Recommend_Adapter;
import com.example.fastfooddelivery2023.Adapter.Sale_Adapter;
import com.example.fastfooddelivery2023.Control.TEMPS;
import com.example.fastfooddelivery2023.Dialog.BottomSheetDialogFragment;
import com.example.fastfooddelivery2023.Model.Category;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.Order;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class HomeFragment extends Fragment {
private View mView;
private AutoCompleteTextView singleComplete;
private MultiAutoCompleteTextView mutiComplete;
private ViewFlipper viewFlipper;
private RecyclerView rcv_category,rcv_recommend,rcv_sale_food;
private Category_Adapter category_adapter;
private Recommend_Adapter recommend_adapter;
private Sale_Adapter sale_adapter;
public static final String OBJECT_FOOD = "object_food";
private User user;
public static List<Food> listFood = new ArrayList<>();
public static List<Food> listFoodFb = new ArrayList<>();
private Category category;
private DatabaseReference dataFood =  FirebaseDatabase.getInstance().getReference("Food");
private final DatabaseReference dataOrder = FirebaseDatabase.getInstance().getReference("Order");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_home, container, false);
        singleComplete = mView.findViewById(R.id.txt_search);
        mutiComplete = mView.findViewById(R.id.mutitextview);
        viewFlipper = mView.findViewById(R.id.viewflipper);
        rcv_category = mView.findViewById(R.id.rcv_category);
        rcv_recommend = mView.findViewById(R.id.rcv_recommend);
        rcv_sale_food = mView.findViewById(R.id.rcv_sale_food);


        // get data food from FireBase


        user = DataPreferences.getUser(getContext(),KEY_USER);

        new Thread(new Runnable() {
            @Override
            public void run() {
                dataFood.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listFoodFb.clear();
                        for(DataSnapshot ds  : snapshot.getChildren()){
                            Food f  = ds.getValue(Food.class);
                            listFoodFb.add(f);
                        }
                        recommend_adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }).start();
        // load data cart from firebase

        ViewFliperAnimation();
        SearchEditText();
        Category_Food();
        Recommend_Food();
        sale_Food();





        return mView;
    }

    private void sale_Food(){
        sale_adapter = new Sale_Adapter(listFoodFb, new Sale_Adapter.ICLickFood() {
            @Override
            public void Click_Add_Cart(Food food) {

            }

            @Override
            public void Click_View_Food(Food food) {
                BottomSheetDialogFragment bottomSheetDialogFragment = BottomSheetDialogFragment.newInstance(food);
                bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rcv_sale_food.setLayoutManager(linearLayoutManager);
        rcv_sale_food.setAdapter(sale_adapter);
        rcv_sale_food.setHasFixedSize(true);
    }

    private void Recommend_Food(){
        recommend_adapter = new Recommend_Adapter(listFoodFb, new Sale_Adapter.ICLickFood() {
            @Override
            public void Click_Add_Cart(Food food) {

            }

            @Override
            public void Click_View_Food(Food food) {
                BottomSheetDialogFragment bottomSheetDialogFragment = BottomSheetDialogFragment.newInstance(food);
                bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false);
        rcv_recommend.setLayoutManager(linearLayoutManager);
        rcv_recommend.setAdapter(recommend_adapter);
        rcv_recommend.setHasFixedSize(true);


    }



    private void SearchEditText(){
        String arr[] = {"Cơm","Bún","Ăn vặt","Trà sửa","Mỳ","Gà rán","Cơm chiên trứng","Cơm xào bò","Cơm xường nướng"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,arr);
        singleComplete.setAdapter(adapter);
        mutiComplete.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1,arr));
        mutiComplete.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());


        singleComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void ViewFliperAnimation() {
        int images [] = {R.drawable.sale,R.drawable.food1,R.drawable.food2};
        for(int i = 0;i<images.length;i++){
            fliperimage(images[i]);
        }
    }

    private void fliperimage(int image) {
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }

    private void Category_Food(){
        List<Category> listCategory = new ArrayList<>();
        final DatabaseReference dataCategory = FirebaseDatabase.getInstance().getReference("Category");
        new Thread(new Runnable() {
            @Override
            public void run() {
                dataCategory.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listCategory.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            category = ds.getValue(Category.class);
                            listCategory.add(category);
                        }
                        category_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
        category_adapter = new Category_Adapter(listCategory, new Category_Adapter.IClickCategory() {
            @Override
            public void Click(Category category) {

            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4);
        rcv_category.setLayoutManager(gridLayoutManager);
        rcv_category.setAdapter(category_adapter);
        rcv_category.setHasFixedSize(true);

    }










}