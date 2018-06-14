package com.delivarius.android.app.view.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.delivarius.android.R;
import com.delivarius.android.app.model.database.Database;
import com.delivarius.android.app.model.dominio.Pedido;
import com.delivarius.android.app.view.adapter.PedidoAdapter;

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
