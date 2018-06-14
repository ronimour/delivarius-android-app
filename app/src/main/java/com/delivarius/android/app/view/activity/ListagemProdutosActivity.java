package com.delivarius.android.app.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.delivarius.android.R;
import com.delivarius.android.app.model.database.Database;
import com.delivarius.android.app.model.dominio.Loja;
import com.delivarius.android.app.model.dominio.Pedido;
import com.delivarius.android.app.model.dominio.Produto;
import com.delivarius.android.app.view.adapter.LojaAdapter;
import com.delivarius.android.app.view.adapter.ProdutoAdapter;

import java.util.ArrayList;

public class ListagemProdutosActivity extends Activity {

    private static final String TAG = "ListagemProdutosActivity";

    private ProdutoAdapter produtoAdapter;
    ArrayList<Produto> lista = new ArrayList<>();
    private Loja loja = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listagem_produtos_activity);

        loja = (Loja) getIntent().getSerializableExtra(Loja.LOJA_INFO);
        Database.setLojaAtual(loja);
        Pedido pedido = (Pedido) getIntent().getSerializableExtra(Pedido.PEDIDO_INFO);
        if(pedido == null) {
            Database.removerPedidoAberto();
        }

        ListView listView = (ListView) findViewById(R.id.lstvw_produtos);

        produtoAdapter = new ProdutoAdapter(this, (ArrayList<Produto>) loja.getProdutos(), loja);

        listView.setAdapter(produtoAdapter);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.loja_layout, null, false);

        LojaAdapter.inflateView(rowView, loja);

        LinearLayout layout = (LinearLayout) findViewById(R.id.lnrlyt_loja_produto);
        layout.addView(rowView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_produto_barra, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.itm_carrinho:
                if(Database.getPedidoAberto() != null) {
                    Intent acao = new Intent("ronnysoft.delivarius.LISTA_ITEM_PEDIDO");
                    startActivity(acao);
                } else {
                    Toast toast = Toast.makeText(this,"Carrinho vazio",Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
            case R.id.itm_limpar_carrinho:
                if(Database.getPedidoAberto() != null) {
                    new AlertDialog.Builder(this)
                            .setTitle("Confirmação")
                            .setMessage("Deseja realmente esvaziar o carrinho?")
                            .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    Database.removerPedidoAberto();
                                    Toast toast = Toast.makeText(ListagemProdutosActivity.this,
                                            "Carrinho esvaziado",
                                            Toast.LENGTH_SHORT);
                                    toast.show();

                                }
                            })
                            .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .show();

                } else {
                    Toast toast = Toast.makeText(this,"Carrinho já está vazio",Toast.LENGTH_SHORT);
                    toast.show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_produto_acoes,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Produto produto = produtoAdapter.getItem(info.position);
        switch (item.getItemId()){
            case R.id.itm_detalhar_produto:

        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}
