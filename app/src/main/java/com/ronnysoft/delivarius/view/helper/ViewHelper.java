package com.ronnysoft.delivarius.view.helper;

import android.view.View;

public class ViewHelper {
    public static void setBackgroundColor(int color, View... views){
        if(views != null){
            for(View view : views){
                view.setBackgroundColor(color);
            }
        }
    }

}
