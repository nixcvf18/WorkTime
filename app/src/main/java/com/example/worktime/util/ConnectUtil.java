package com.example.worktime.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectUtil {

    //检查是否为联网状态
    public static boolean isNetworkConnecting(Context context) {
        if (context != null){
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null)
                return info.isAvailable();
        }


        return false;
    }










    //检查是否为wifi状态
    public static boolean isWifiConnected(Context context) {

        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


            if (networkInfo != null) {


                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {


                    return networkInfo.isAvailable();
                }

            }


        }


        return false;
    }


    //检查是否为移动数据状态
    public static boolean isMobileDataConnected(Context context) {


        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();


            if (networkInfo != null) {
                if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {


                    return true;
                }
            }


        }

        return false;
    }















}
