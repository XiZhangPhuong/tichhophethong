package com.example.fastfooddelivery2023.Fragment;

import static com.example.fastfooddelivery2023.Fragment.LoginSignUp.LoginFragment.KEY_USER;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfooddelivery2023.Activity.ChangeUserActivity;
import com.example.fastfooddelivery2023.Activity.ContactAdminActivity;
import com.example.fastfooddelivery2023.Activity.HistoryActivity;
import com.example.fastfooddelivery2023.Activity.Login_SignUpActivity;
import com.example.fastfooddelivery2023.Activity.VideoActivity;
import com.example.fastfooddelivery2023.Activity.WaitingActivity;
import com.example.fastfooddelivery2023.Adapter.FunctionUser_Adapter;
import com.example.fastfooddelivery2023.MainActivity;
import com.example.fastfooddelivery2023.Model.FunctionUser;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;
import com.example.fastfooddelivery2023.SharedPreferences.DataPreferences;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserFragment extends Fragment {
    private View mView;
    private TextView txt_name_user,txt_phone_user,txt_id_user,txt_update;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ImageView img_back;
    private RecyclerView rcv_function_user;
    private FunctionUser_Adapter functionUser_adapter;
    private DatabaseReference data_Function_user ;
    private MainActivity mainActivity;
    private List<FunctionUser> listFun = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView =inflater.inflate(R.layout.fragment_user, container, false);
        initView(mView);
        mainActivity = (MainActivity) getActivity();

        try {
            setDataUser();
            loadDataFunction_User();
        }catch (Exception e){
            e.printStackTrace();
        }







        return mView;
    }
    private void setDataUser(){
        User user = DataPreferences.getUser(getContext(),KEY_USER);
        txt_phone_user.setText(user.getPhoneNumber());
        txt_name_user.setText(user.getFullName());
        txt_id_user.setText(user.getId());
    }
    private void loadDataFunction_User(){
        data_Function_user = FirebaseDatabase.getInstance().getReference("Function_User");
        new Thread(new Runnable() {
            @Override
            public void run() {
                data_Function_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        listFun.clear();
                        for(DataSnapshot ds : snapshot.getChildren()){
                            FunctionUser f = ds.getValue(FunctionUser.class);
                            listFun.add(f);
                        }
                        functionUser_adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        }).start();
        functionUser_adapter = new FunctionUser_Adapter(listFun, new FunctionUser_Adapter.ClickFunction() {
            @Override
            public void Click(FunctionUser functionUser) {
                  int index = Integer.parseInt(functionUser.getId());
                  switch (index){
                      case 1 : startActivity(new Intent(getContext(), WaitingActivity.class));
                         break;

                      case 2 : //MainActivity.viewPager2.setCurrentItem(1);

                          break;

                      case 3 :startActivity(new Intent(getContext(),HistoryActivity.class));
                          break;

                      case 4 :
                          break;

                      case 5:  Intent intent = new Intent(getContext(), ChangeUserActivity.class);
                               startActivity(intent);
                          break;

                      case 6:  startActivity(new Intent(getContext(), VideoActivity.class));
                          break;

                      case 7:  Intent intent1 = new Intent(getContext(), ContactAdminActivity.class);
                               startActivity(intent1);
                          break;

                      case 8:
                          AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                          builder.setTitle("Thông báo").setMessage("Bạn có muốn đăng xuất ? ").setPositiveButton("Yes",
                                  new DialogInterface.OnClickListener() {
                                      @Override
                                      public void onClick(DialogInterface dialogInterface, int i) {
                                          startActivity(new Intent(getContext(), Login_SignUpActivity.class));
                                          DataPreferences.setUser(mainActivity,null,KEY_USER);
                                          mainActivity.finishAffinity();
                                      }
                                  }).setNegativeButton("No",null).create().show();
                          break;
                  }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        rcv_function_user.setLayoutManager(linearLayoutManager);
        rcv_function_user.setAdapter(functionUser_adapter);
        rcv_function_user.setHasFixedSize(true);



    }

    private void initView(View view){
        txt_name_user = view.findViewById(R.id.tv_name_user);
        txt_id_user = view.findViewById(R.id.tv_id_user);
        txt_phone_user = view.findViewById(R.id.tv_phone_user);
        rcv_function_user = view.findViewById(R.id.rcv_function_user);

    }


}