package com.delivarius.android.app.view.helper;

import android.support.annotation.NonNull;

import java.util.Date;

public class TempoHelper {

    @NonNull
    public static long segundosBetween(Date date1, Date date2){
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        long differenceInSeconds = Math.abs(time1 - time2)/1000;
        return differenceInSeconds;
    }
}
