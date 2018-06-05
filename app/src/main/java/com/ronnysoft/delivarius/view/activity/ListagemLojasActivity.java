package com.ronnysoft.delivarius.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ronnysoft.delivarius.R;
import com.ronnysoft.delivarius.model.dominio.Loja;
import com.ronnysoft.delivarius.model.database.Database;
import com.ronnysoft.delivarius.view.adapter.LojaAdapter;

import java.util.ArrayList;

public class ListagemLojasActivity extends Activity {

    private LojaAdapter adaptadorLista;
    ArrayList<Loja> lojas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listagem_lojas_activity);

        ListView listView = (ListView) findViewById(R.id.lstvw_lojas);

        lojas = Database.getLojas();

        adaptadorLista = new LojaAdapter(this, lojas);

        listView.setAdapter(adaptadorLista);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Loja selecionada = lojas.get(i);
                Intent acao = new Intent("ronnysoft.delivarius.LISTA_PRODUTOS");
                acao.putExtra(Loja.LOJA_INFO, selecionada);
                startActivity(acao);
            }
        });

    }

}
