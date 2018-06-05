package com.ronnysoft.delivarius.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ronnysoft.delivarius.R;
import com.ronnysoft.delivarius.model.database.Database;
import com.ronnysoft.delivarius.receiver.AtualizaStatusPedidosReceiver;

public class InicioActivity extends Activity {


    public static final String BROADCAST = "android.intent.action.BOOT_COMPLETED";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inicio_activity);

    }

    public void realizarPedidos(View v){
        Intent acao = new Intent("ronnysoft.delivarius.LISTA_LOJAS");
        startActivity(acao);
    }

    public void verPedidos(View v){
        Database.carregarPedidos(this);

        if(!Database.getPedidos().isEmpty()) {
            Database.atualizarStatusPedidos(this);
            Intent acao = new Intent("ronnysoft.delivarius.LISTA_PEDIDOS");
            startActivity(acao);
        } else {
            Toast toast = Toast.makeText(this, "Nenhum pedido ainda foi realizado", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


}
