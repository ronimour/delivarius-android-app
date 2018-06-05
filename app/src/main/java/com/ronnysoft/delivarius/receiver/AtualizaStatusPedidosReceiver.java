package com.ronnysoft.delivarius.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ronnysoft.delivarius.service.ScheduleJobUtil;

public class AtualizaStatusPedidosReceiver extends BroadcastReceiver{

    private static final String TAG = "AtualizaStatusPedidosReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        ScheduleJobUtil.scheduleJob(context);
        Log.i(TAG,"Broadcast Receiver executado");
    }
}
