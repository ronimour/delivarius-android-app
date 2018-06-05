package com.ronnysoft.delivarius.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ronnysoft.delivarius.R;
import com.ronnysoft.delivarius.model.database.Database;
import com.ronnysoft.delivarius.model.dominio.ItemPedido;
import com.ronnysoft.delivarius.model.dominio.Loja;
import com.ronnysoft.delivarius.model.dominio.Pedido;
import com.ronnysoft.delivarius.view.adapter.ItemPedidoAdapter;
import com.ronnysoft.delivarius.view.adapter.LojaAdapter;
import com.ronnysoft.delivarius.view.helper.FormatHelper;

import java.util.ArrayList;

public class PedidoItensActivity extends Activity {

    private ItemPedidoAdapter adaptadorLista;
    private ArrayList<ItemPedido> itensPedido = new ArrayList<>();
    private ItemPedido item = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.listagem_itens_pedido_activity);

        ListView listView = (ListView) findViewById(R.id.lstvw_itens_pedido);

        itensPedido = (ArrayList<ItemPedido>) Database.getPedidoAberto().getItens();

        adaptadorLista = new ItemPedidoAdapter(this, itensPedido);

        listView.setAdapter(adaptadorLista);

        atualizarValorTotalView();

    }

    public void decrementarItemPedido(View view){
        setItemFromView((ImageView) view);

        if(item.getQuantidade() - 1 <= 0) {
            new AlertDialog.Builder(this)
                    .setTitle("Confirmação")
                    .setMessage("Deseja realmente remover esse produto do carrinho?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        Database.removerItemPedido(item);
                        adaptadorLista.notifyDataSetChanged();
                        atualizarValorTotalView();
                        Toast toast = Toast.makeText(PedidoItensActivity.this,
                                item.getProduto().getNome() + " removido do carrinho",
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
            Database.decrementarQtdItemPedido(item);
            adaptadorLista.notifyDataSetChanged();
            atualizarValorTotalView();
        }



    }

    public void incrementarItemPedido(View view){
        setItemFromView((ImageView) view);

        if(item.getQuantidade()+1 > item.getProduto().getQtdEstoque()){
            Toast toast = Toast.makeText(PedidoItensActivity.this,
                    "Quantidade excede o limite do produto em estoque",
                    Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Database.incrementarQtdItemPedido(item);
            adaptadorLista.notifyDataSetChanged();
            atualizarValorTotalView();
        }


    }

    public void atualizarValorTotalView(){
        TextView totalTxtVw = (TextView) findViewById(R.id.txtvw_total_item_pedido);
        totalTxtVw.setText(FormatHelper.formatarMoeda(Database.getPedidoAberto().getValorTotalCalculado()));
    }

    public void concluirPedido(View view){
        Intent acao = new Intent("ronnysoft.delivarius.DADOS_PESSOAIS_PEDIDO");
        startActivity(acao);
    }

    public void excluirPedido(View view){
        new AlertDialog.Builder(this)
                .setTitle("Confirmação")
                .setMessage("Deseja realmente remover esse produto do carrinho?")
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Database.removerPedidoAberto();
                        Toast toast = Toast.makeText(PedidoItensActivity.this,"Pedido excluído", Toast.LENGTH_SHORT);
                        toast.show();
                        abrirActivityListaProdutos(null);
                    }
                })
                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();


    }

    public void buscarMaisProdutos(View view){
        abrirActivityListaProdutos(Database.getPedidoAberto());
    }

    private void abrirActivityListaProdutos(Pedido pedido){
        Intent acao = new Intent("ronnysoft.delivarius.LISTA_PRODUTOS");
        acao.putExtra(Loja.LOJA_INFO, Database.getLojaAtual());
        if(pedido != null)
            acao.putExtra(Pedido.PEDIDO_INFO, pedido);
        startActivity(acao);
    }

    private void setItemFromView(ImageView view) {
        ImageView imageView = view;
        int position = (Integer) imageView.getTag();
        item = itensPedido.get(position);
    }

}
