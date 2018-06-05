package com.ronnysoft.delivarius.view.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ronnysoft.delivarius.R;
import com.ronnysoft.delivarius.model.dominio.Loja;
import com.ronnysoft.delivarius.view.helper.ImageHelper;

import java.util.ArrayList;

public class LojaAdapter extends ArrayAdapter<Loja> {

    private final Context context;
    ArrayList<Loja> lista = new ArrayList<>();

    public LojaAdapter(Context context, ArrayList<Loja> lista) {
        super(context, R.layout.loja_layout, lista );
        this.context = context;
        this.lista = lista;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.loja_layout, parent, false);
        Loja loja = lista.get(position);

        inflateView(rowView,loja);

        return rowView;
    }

    public static void inflateView(View rowView, Loja loja){

        TextView nomeTxtVw = (TextView) rowView.findViewById(R.id.txtvw_nome_loja);
        TextView endrTxtVw = (TextView) rowView.findViewById(R.id.txtvw_endereco_loja);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgvw_logo_loja);

        nomeTxtVw.setText(loja.getNome());
        endrTxtVw.setText(loja.getEndereco());
        ImageHelper.setImageViewloja(imageView,loja.getImagem());

    }
}
