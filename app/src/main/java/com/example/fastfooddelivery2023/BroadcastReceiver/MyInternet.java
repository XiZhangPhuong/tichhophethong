package com.example.fastfooddelivery2023.BroadcastReceiver;

import static com.example.fastfooddelivery2023.Fragment.Control.TEMPS.checkInternet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class MyInternet extends BroadcastReceiver {
    public static boolean checkInternet;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
            if(checkInternet(context)){
                Toast.makeText(context,"Internet",Toast.LENGTH_LONG).show();
                checkInternet = true;
            }else{
                Toast.makeText(context,"Internet Disconnected",Toast.LENGTH_LONG).show();
                checkInternet =  false;
            }
        }
    }
}
