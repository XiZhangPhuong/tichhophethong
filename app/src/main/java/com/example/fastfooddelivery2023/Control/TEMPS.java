package com.example.fastfooddelivery2023.Control;

import static com.example.fastfooddelivery2023.Notification.Notification_Class.CHANNEL_1_ID;

import android.content.Context;
import android.media.MediaPlayer;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;

import java.util.List;
import java.util.Random;

public class TEMPS {

    public static NotificationManagerCompat notificationManagerCompat;

    public static int count_Order(List<Food> list,String ID_Food){
        int quantity = 0;
        for(Food f : list){
            if(f.getId_Food().compareTo(ID_Food)==0){
                quantity++;
            }
        }
        return  quantity;
    }
    
    public static boolean checkPhoneNumber(List<User> list, String phone){
        for(User user : list){
            if(user.getPhoneNumber().equals(phone)){
                return true;
            }
        }
        return false;
    }

    public static int ranDomCODE(){
        int s = (int) Math.floor(((Math.random() * 899999) + 100000));
        return s;
    }

    public static void showNotification(Context context, String title, String mess){

        notificationManagerCompat  = NotificationManagerCompat.from(context);
        android.app.Notification notification = new NotificationCompat.Builder(context,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.icon_tea_50px)
                .setContentTitle(title)
                .setContentText(mess)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .build();
        notificationManagerCompat.notify(1,notification);

    }
}
