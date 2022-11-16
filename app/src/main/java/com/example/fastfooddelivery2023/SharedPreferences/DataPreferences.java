package com.example.fastfooddelivery2023.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataPreferences {
    private static final String MY_KEY = "MY_KEY";

    public static void setUser(Context context, User user, String KEY){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(KEY,json);
        editor.apply();
    }
    public static  User getUser(Context context, String KEY){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_KEY, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(KEY,"");
        Gson gson = new Gson();
        User user = gson.fromJson(json,User.class);
        return user;
    }

    public static void setString(Context context,String str, String KEY){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(str);
        editor.putString(KEY,json);
        editor.apply();
    }
    public static  String getString(Context context, String KEY){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_KEY, Context.MODE_PRIVATE);
        String json = sharedPreferences.getString(KEY,"");
        return json;
    }

    public static void setListFood(Context context, List<Food> list,String KEY){
        Gson gson = new Gson();
        String jsonString = gson.toJson(list);

        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY, jsonString);
        editor.apply();
    }
    public static List<Food> getListFood(Context context,String KEY){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_KEY, Context.MODE_PRIVATE);
        String jsonString = sharedPreferences.getString(KEY, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Food>>(){}.getType();
        List<Food> list = gson.fromJson(jsonString, type);
        return list;
    }

}
