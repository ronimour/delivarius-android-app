package com.delivarius.android.app.view.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.delivarius.android.R;
import com.delivarius.android.app.model.dominio.ItemPedido;
import com.delivarius.android.app.model.dominio.Produto;
import com.delivarius.android.app.view.helper.ImageHelper;

import java.util.ArrayList;

public class ItemPedidoAdapter extends ArrayAdapter<ItemPedido> {

    private final Context context;
    private ArrayList<ItemPedido> lista = new ArrayList<>();


    public ItemPedidoAdapter(Context context, ArrayList<ItemPedido> lista) {
        super(context, R.layout.item_pedido_layout, lista );
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.item_pedido_layout, parent, false);

        TextView nomeTxtVw = (TextView) rowView.findViewById(R.id.txtvw_nome_produto_item_pedido);
        TextView precoTxtVw = (TextView) rowView.findViewById(R.id.txtvw_preco_item_pedido);
        TextView qtdItTxtVw = (TextView) rowView.findViewById(R.id.txtvw_qtd_item_pedido);
        ImageView logoImgVw = (ImageView) rowView.findViewById(R.id.imgvw_produto_item_pedido);
        ImageView removeImgVw = (ImageView) rowView.findViewById(R.id.imgvw_decrementar_qtd_item_pedido);
        ImageView addImgVw = (ImageView) rowView.findViewById(R.id.imgvw_incrementar_qtd_item_pedido);

        ItemPedido itemPedido = lista.get(position);
        Produto produto = itemPedido.getProduto();

        nomeTxtVw.setText(produto.getNome());
        precoTxtVw.setText(itemPedido.getValorFormatado());
        qtdItTxtVw.setText(String.valueOf(itemPedido.getQuantidade()));
        ImageHelper.setImageViewProduto(logoImgVw, produto.getImagem());
        removeImgVw.setTag(position);
        addImgVw.setTag(position);

        return rowView;
    }
}
