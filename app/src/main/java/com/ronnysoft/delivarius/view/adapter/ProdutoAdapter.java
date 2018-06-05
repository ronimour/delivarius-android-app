package com.ronnysoft.delivarius.view.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ronnysoft.delivarius.R;
import com.ronnysoft.delivarius.model.database.Database;
import com.ronnysoft.delivarius.model.dominio.Loja;
import com.ronnysoft.delivarius.model.dominio.Produto;
import com.ronnysoft.delivarius.view.helper.ImageHelper;

import java.util.ArrayList;

public class ProdutoAdapter extends ArrayAdapter<Produto> {

    private final Context context;
    ArrayList<Produto> lista = new ArrayList<>();
    private Loja loja = null;

    public ProdutoAdapter(Context context, ArrayList<Produto> lista, Loja loja) {
        super(context, R.layout.loja_layout, lista );
        this.context = context;
        this.lista = lista;
        this.loja = loja;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.produto_layout, parent, false);

        TextView nomeTxtVw = (TextView) rowView.findViewById(R.id.txtvw_nome_produto);
        TextView descTxtVw = (TextView) rowView.findViewById(R.id.txtvw_descricao_produto);
        TextView precoTxtVw = (TextView) rowView.findViewById(R.id.txtvw_preco_produto);
        ImageView logoimgVw = (ImageView) rowView.findViewById(R.id.imgvw_produto);
        ImageView addimgVw = (ImageView) rowView.findViewById(R.id.imgvw_add_icon);

        Produto produto = lista.get(position);

        nomeTxtVw.setText(produto.getNome());
        descTxtVw.setText(produto.getDescricao());
        precoTxtVw.setText(produto.getPreco());
        ImageHelper.setImageViewProduto(logoimgVw,produto.getImagem());
        addimgVw.setTag(position);
        addimgVw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                new AlertDialog.Builder( context )
                        .setTitle( "Confirmação" )
                        .setMessage( "Deseja realmente adicionar esse produto ao carrinho?" )
                        .setPositiveButton( "Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                ImageView imageView = (ImageView) view;
                                int position = (Integer) imageView.getTag();
                                Produto p = lista.get(position);
                                Database.addItemPedido(p,1);
                                Toast toast = Toast.makeText(context,
                                        p.getNome()+" adicionado ao carrinho",
                                        Toast.LENGTH_SHORT);
                                toast.show();

                            }
                        })
                        .setNegativeButton( "Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        } )
                        .show();

            }
        });



        return rowView;
    }
}
