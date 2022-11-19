package com.example.fastfooddelivery2023.Control;

import static com.example.fastfooddelivery2023.Notification.Notification_Class.CHANNEL_1_ID;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Base64;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.Order;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;

import java.io.ByteArrayOutputStream;
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

    public static boolean checkPlaceOrder(String idFood,List<Food> list){
        for(int i = 0;i<list.size();i++){
            if(idFood.equals(list.get(i).getId_Food())){
                return true;
            }
        }
        return false;
    }

    public static int checkOrderStatus(List<Order> list){
        for(Order order : list){
            if(order.getCheck()==1){
                return 1;
            }else if(order.getCheck()==2){
                return 2;
            }else if(order.getCheck()==3){
                return 3;
            }
        }
        return 0;
    }

    public static User checkUser(String phone,List<User> list){
        for(User user : list){
            if(user.getPhoneNumber().equals(phone)){
                return user;
            }
        }
        return null;
    }

    public static int ranDomCODE(){
        int s = (int) Math.floor(((Math.random() * 899999) + 100000));
        return s;
    }

    public static void showNotification(Context context, String title, String mess){
        notificationManagerCompat  = NotificationManagerCompat.from(context);
        android.app.Notification notification = new NotificationCompat.Builder(context,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_like_24)
                .setContentTitle(title)
                .setContentText(mess)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setAutoCancel(true)
                .build();
        notificationManagerCompat.notify(1,notification);

    }

    public static Bitmap convertBase64ToImage(String str){
        byte[] bytes = Base64.decode(str,Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        return bitmap;
    }
    public static String convertImage_toBase64(Bitmap bitmap){
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] bytes=stream.toByteArray();
        String encodeToString = Base64.encodeToString(bytes,Base64.DEFAULT);
        return encodeToString;
    }

    public static Bitmap convertImageResourceToBitMap(int image){
        Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(),image);
        return bitmap;
    }



}
