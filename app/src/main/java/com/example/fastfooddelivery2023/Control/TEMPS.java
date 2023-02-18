package com.example.fastfooddelivery2023.Control;

import static com.example.fastfooddelivery2023.Notification.Notification_Class.CHANNEL_1_ID;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Base64;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.fastfooddelivery2023.Model.Food;
import com.example.fastfooddelivery2023.Model.Order_FB;
import com.example.fastfooddelivery2023.Model.User;
import com.example.fastfooddelivery2023.R;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

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

    public static int checkOrderStatus(List<Order_FB> list){
        for(Order_FB order : list){
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
                .setSmallIcon(R.drawable.ic_baseline_check_circle_outline_24)
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
    public static String stringToBase64(String str){
        byte[] data = str.getBytes(StandardCharsets.UTF_8);
        String base64 = Base64.encodeToString(data, Base64.DEFAULT);
        return base64;
    }


    public static String base64ToString(String str){
        byte[] data = Base64.decode(str, Base64.DEFAULT);
        String text = new String(data, StandardCharsets.UTF_8);
        return text;
    }

    public static Bitmap convertImageResourceToBitMap(int image){
        Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(),image);
        return bitmap;
    }

    public static boolean checkInternet(Context context){
        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return (networkInfo!=null && networkInfo.isConnected());
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }






}
