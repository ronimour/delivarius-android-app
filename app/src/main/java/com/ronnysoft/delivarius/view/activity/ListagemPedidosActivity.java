package com.ronnysoft.delivarius.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ronnysoft.delivarius.R;
import com.ronnysoft.delivarius.model.database.Database;
import com.ronnysoft.delivarius.model.dominio.ItemPedido;
import com.ronnysoft.delivarius.model.dominio.Pedido;
import com.ronnysoft.delivarius.view.adapter.ItemPedidoAdapter;
import com.ronnysoft.delivarius.view.adapter.PedidoAdapter;
import com.ronnysoft.delivarius.view.helper.FormatHelper;

import java.util.ArrayList;

public class ListagemPedidosActivity extends Activity {

    private PedidoAdapter adaptadorLista;
    private ArrayList<Pedido> lista = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listagem_pedidos_activity);

        ListView listView = (ListView) findViewById(R.id.lstvw_pedidos);

        lista = Database.getPedidos();

        adaptadorLista = new PedidoAdapter(this, lista);

        listView.setAdapter(adaptadorLista);

    }

    public void verDetalhes(View view){

    }



}
