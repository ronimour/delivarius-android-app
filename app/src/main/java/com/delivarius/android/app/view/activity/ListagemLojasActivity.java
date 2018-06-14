package com.delivarius.android.app.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.delivarius.android.R;
import com.delivarius.android.app.model.dominio.Loja;
import com.delivarius.android.app.model.database.Database;
import com.delivarius.android.app.view.adapter.LojaAdapter;

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
