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
import com.example.fastfooddelivery2023.Dialog.BottomSheetDialogFragment;
import com.example.fastfooddelivery2023.Model.Category;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
private View mView;
private AutoCompleteTextView singleComplete;
private MultiAutoCompleteTextView mutiComplete;
private ViewFlipper viewFlipper;
private RecyclerView rcv_category,rcv_recommend;
private Category_Adapter category_adapter;
private Recommend_Adapter recommend_adapter;
public static final String OBJECT_FOOD = "object_food";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_home, container, false);
        singleComplete = mView.findViewById(R.id.txt_search);
        mutiComplete = mView.findViewById(R.id.mutitextview);
        viewFlipper = mView.findViewById(R.id.viewflipper);
        rcv_category = mView.findViewById(R.id.rcv_category);
        rcv_recommend = mView.findViewById(R.id.rcv_recommend);

        ViewFliperAnimation();
        SearchEditText();
        Category_Food();
        Recommend_Food();


        return mView;
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
        int images [] = {R.drawable.slide1,R.drawable.slide2};
        for(int i = 0;i<images.length;i++){
            fliperimage(images[i]);
        }
    }

    private void fliperimage(int image) {
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);
    }
    private void Recommend_Food(){
        List<Food> list = new ArrayList<>();
        list.add(new Food("123456","Cơm chiên",String.valueOf(R.drawable.comchientrung),"Cơm","Cơm chiên rất ngon nha","Luôn ủng hộ","0",1,450000));
        recommend_adapter  = new Recommend_Adapter(list, new Recommend_Adapter.IClickFood() {
            @Override
            public void Click(Food food) {
                BottomSheetDialogFragment bottomSheetDialogFragment = BottomSheetDialogFragment.newInstance(food);
                bottomSheetDialogFragment.show(getActivity().getSupportFragmentManager(), bottomSheetDialogFragment.getTag());

            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
        rcv_recommend.setLayoutManager(linearLayoutManager);
        rcv_recommend.setAdapter(recommend_adapter);
        rcv_recommend.setHasFixedSize(true);
    }
    private void Category_Food(){
        List<Category> listCategory = new ArrayList<>();
//        listCategory.add(new Category(String.valueOf(R.drawable.icon_rice_50px),"Cơm"));
//        listCategory.add(new Category(String.valueOf(R.drawable.icon_noodle_50px),"Mỳ"));
        listCategory.add(new Category(String.valueOf(R.drawable.icon_bread_50px),"Gà rán"));
        listCategory.add(new Category(String.valueOf(R.drawable.icon_bread_50px),"Đồ uống"));
        listCategory.add(new Category(String.valueOf(R.drawable.icon_bread_50px),"Trà sửa"));
        listCategory.add(new Category(String.valueOf(R.drawable.icon_bread_50px),"Ăn vặt"));

        listCategory.add(new Category(String.valueOf(R.drawable.icon_bread_50px),"Gà rán"));
        listCategory.add(new Category(String.valueOf(R.drawable.icon_bread_50px),"Đồ uống"));
        listCategory.add(new Category(String.valueOf(R.drawable.icon_bread_50px),"Trà sửa"));
        listCategory.add(new Category(String.valueOf(R.drawable.icon_bread_50px),"Ăn vặt"));

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