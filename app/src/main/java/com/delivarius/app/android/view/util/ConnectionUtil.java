package com.delivarius.app.android.view.util;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;

import java.net.InetAddress;

public class ConnectionUtil {

    //public static final String DELIVARIUS_ADDRESS = "https://delivarius.herokuapp.com";
   public static final String DELIVARIUS_ADDRESS = "http://192.168.0.28:8081";
   //public static final String DELIVARIUS_ADDRESS = "http://10.0.2.2:8081";

    private static boolean isNetworkConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static boolean isInternetAvailable(Activity activity) {
        return isAddressAvailable("google.com", activity);
    }

    public static boolean isDelivariusServerAvailable(Activity activity) {
        return isAddressAvailable(DELIVARIUS_ADDRESS, activity);
    }

    private static boolean isAddressAvailable(String address, Activity activity){
        try {
            if(isNetworkConnected(activity)) {
                InetAddress ipAddr = InetAddress.getByName(address);
                return !ipAddr.equals("");
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }
    }



}
