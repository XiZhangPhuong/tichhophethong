package com.example.fastfooddelivery2023.Fragment;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import android.content.Intent;
import android.os.Build;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import com.example.fastfooddelivery2023.Activity.CategoryActivity;
import com.example.fastfooddelivery2023.Activity.InforActivity;
import com.example.fastfooddelivery2023.Activity.SearchActivity;
import com.example.fastfooddelivery2023.Adapter.Category_Adapter;
import com.example.fastfooddelivery2023.Adapter_New.FoodAdapter;
import com.example.fastfooddelivery2023.Adapter_New.ObjectFoodAdapter;
import com.example.fastfooddelivery2023.Adapter_New.SearchAdapter;
import com.example.fastfooddelivery2023.Dialog.BottomSheetDialogFragment;
import com.example.fastfooddelivery2023.Model.Category;
import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.ObjectFood;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class HomeFragment extends Fragment {
private View mView;
private AutoCompleteTextView singleComplete;
private MultiAutoCompleteTextView mutiComplete;
private ViewFlipper viewFlipper;
private RecyclerView rcv_category,rcv_recommend,rcv_sale_food,rcv_object_food,rcv_food;
private Category_Adapter category_adapter;
private ScrollView scrollview;
private FloatingActionButton floating;
private EditText edt_search;
private ProgressBar progressBar_Home_Food;
public static final String OBJECT_FOOD = "object_food";
private User user;
public static List<Food> listFood = new ArrayList<>();
public static List<Food> listFoodFb = new ArrayList<>();
private Category category;
private SearchAdapter searchAdapter;
private ObjectFoodAdapter objectFoodAdapter;
private DatabaseReference dataFood =  FirebaseDatabase.getInstance().getReference("Food");
private final DatabaseReference dataOrder = FirebaseDatabase.getInstance().getReference("Order");

//new update
public static List<Food> listFoodNew = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_home, container, false);
        viewFlipper = mView.findViewById(R.id.viewflipper);
        rcv_category = mView.findViewById(R.id.rcv_category);
        rcv_object_food = mView.findViewById(R.id.rcv_object_food);
        edt_search = mView.findViewById(R.id.edt_search);
        rcv_food = mView.findViewById(R.id.rcv_food);
        floating = mView.findViewById(R.id.floating);
        scrollview = mView.findViewById(R.id.scrollview);


        user = DataPreferences.getUser(getContext(),KEY_USER);

        // load data cart from firebase
        try {
            setWindow();
            showFloatingButton();
            ClickEdittext();
            loadDataObjectFood();
            loadDataRcvFood();
            ViewFliperAnimation();
            Category_Food();
        }catch (Exception e){
            e.printStackTrace();
        }
        return mView;
    }
    private void showFloatingButton(){
        floating.setVisibility(View.GONE);
        scrollview.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
           @Override
           public void onScrollChanged() {
               if(scrollview.getScrollY()>1000){
                   floating.setVisibility(View.VISIBLE);
                   floating.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           scrollview.scrollTo(0,0);
                       }
                   });
               }else{
                   floating.setVisibility(View.GONE);
               }
           }
       });
    }
    private void ViewFliperAnimation() {
        String []images = {"https://thietbiducthanh.vn/wp-content/uploads/2020/02/th%E1%BB%B1c-%C4%91%C6%A1n-1-711x400.jpg",
                "https://ss-images.saostar.vn/w1200/pc/1663143418617/saostar-pecai6vwmqohghnp.jpg",
                "https://afamilycdn.com/150157425591193600/2022/8/26/4-16614841580711371899482-1661488118094-16614881181784877563.jpg"};
        for(int i = 0;i<images.length;i++){
            fliperimage(images[i]);
        }
    }
    private void fliperimage(String image) {
        ImageView imageView = new ImageView(getContext());
        Picasso.get().load(image).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        viewFlipper.addView(imageView);
        viewFlipper.setFlipInterval(4000);
        viewFlipper.setAutoStart(true);
        viewFlipper.setInAnimation(getContext(), android.R.anim.slide_in_left);
        viewFlipper.setOutAnimation(getContext(), android.R.anim.slide_out_right);;
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
                  Intent intent = new Intent(getContext(), CategoryActivity.class);
                  intent.putExtra("KEY_CATEGORY",category.getId_category());
                  startActivity(intent);
            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),4);
        rcv_category.setLayoutManager(gridLayoutManager);
        rcv_category.setAdapter(category_adapter);
        rcv_category.setHasFixedSize(true);

    }
    private List<Food> getListFoodFb(){
        List<Food> list = new ArrayList<>();
        final  DatabaseReference dataFood = FirebaseDatabase.getInstance().getReference("Food");
        new Thread(new Runnable() {
            @Override
            public void run() {
                dataFood.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            Food f = ds.getValue(Food.class);
                            list.add(f);
                            Collections.shuffle(list);
                        }
                        objectFoodAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }).start();
        return list;
    }
    private void loadDataObjectFood(){
        List<ObjectFood> list = new ArrayList<>();
        rcv_object_food.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_object_food.setHasFixedSize(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                list.add(new ObjectFood("Đề xuất","https://thietbiducthanh.vn/wp-content/uploads/2020/02/th%E1%BB%B1c-%C4%91%C6%A1n-1-711x400.jpg",getListFoodFb()));
                list.add(new ObjectFood("Đang giảm giá","https://ss-images.saostar.vn/w1200/pc/1663143418617/saostar-pecai6vwmqohghnp.jpg",getListFoodFb()));
                list.add(new ObjectFood("Ăn vô cực - Khao đến 50%","https://afamilycdn.com/150157425591193600/2022/8/26/4-16614841580711371899482-1661488118094-16614881181784877563.jpg",getListFoodFb()));
                list.add(new ObjectFood("Quán mới phải thử","https://ss-images.saostar.vn/w1200/pc/1663143418617/saostar-pecai6vwmqohghnp.jpg",getListFoodFb()));
                list.add(new ObjectFood("Đang gần bạn","https://bizweb.dktcdn.net/100/090/383/files/01-doitac-now-20-percentage-1920x1080-01-percentage-percentage-percentage-percentage-haisancua-com-copy-2-copy.jpg?v=1623431451380",getListFoodFb()));
                objectFoodAdapter = new ObjectFoodAdapter(getContext(), list, new ObjectFoodAdapter.ClickObjectFood() {
                    @Override
                    public void Click(Food food) {
                        Intent intent = new Intent(getContext(), InforActivity.class);
                        intent.putExtra("KEY_FOOD",food);
                        startActivity(intent);
                    }
                });
                rcv_object_food.setAdapter(objectFoodAdapter);
                objectFoodAdapter.notifyDataSetChanged();
            }
        }).start();
    }

    private void ClickEdittext(){
       edt_search.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getContext(), SearchActivity.class);
                        startActivity(intent);
                    }
                }).start();
           }
       });
    }

    private List<Food> getFoodFB(){
        List<Food> list = new ArrayList<>();
        final DatabaseReference dataFood = FirebaseDatabase.getInstance().getReference("Food");
        new Thread(new Runnable() {
            @Override
            public void run() {
                dataFood.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot ds : snapshot.getChildren()) {
                            Food f = ds.getValue(Food.class);
                            list.add(f);
                            Collections.shuffle(list);
                        }
                        searchAdapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        }).start();
        return list;

    }
    private void loadDataRcvFood(){
        rcv_food.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        rcv_food.setHasFixedSize(true);
        searchAdapter = new SearchAdapter(getContext(), getFoodFB(), new SearchAdapter.ClickSearchFood() {
            @Override
            public void Click(Food food) {
                Intent intent = new Intent(getContext(), InforActivity.class);
                intent.putExtra("KEY_FOOD",food);
                startActivity(intent);
                getActivity().finish();
            }
        });
        rcv_food.setAdapter(searchAdapter);
        searchAdapter.notifyDataSetChanged();
    }
    private void setWindow(){
        if(Build.VERSION.SDK_INT>=21){
            Window window = getActivity().getWindow();
            window.setStatusBarColor(getContext().getResources().getColor(android.R.color.holo_blue_dark));
            window.setNavigationBarColor(getActivity().getResources().getColor(android.R.color.holo_blue_dark));
        }
    }
    private void swipeBar(){

    }

}