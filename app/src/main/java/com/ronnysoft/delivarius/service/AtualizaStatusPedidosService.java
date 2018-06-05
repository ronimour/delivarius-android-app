package com.ronnysoft.delivarius.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.ronnysoft.delivarius.model.database.Database;

public class AtualizaStatusPedidosService extends Service {

    private static final String TAG = "AtualizaStatusPedidosService";

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;

    public AtualizaStatusPedidosService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Database.atualizarStatusPedidos(this.context);

        Log.i(TAG, "Serviço de Atulização de Status dos Pedidos executado");
        return Service.START_NOT_STICKY;
    }


}
