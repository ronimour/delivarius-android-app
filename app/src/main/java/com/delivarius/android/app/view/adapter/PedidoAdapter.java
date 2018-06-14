package com.delivarius.android.app.view.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.delivarius.android.R;
import com.delivarius.android.app.model.dominio.MovimentacaoPedido;
import com.delivarius.android.app.model.dominio.Pedido;
import com.delivarius.android.app.view.helper.FormatHelper;
import com.delivarius.android.app.view.helper.MovimentacaoPedidoHelper;
import com.delivarius.android.app.view.helper.ViewHelper;

import java.util.ArrayList;

public class PedidoAdapter extends ArrayAdapter<Pedido> {

    private final Context context;
    private ArrayList<Pedido> lista = new ArrayList<>();


    public PedidoAdapter(Context context, ArrayList<Pedido> lista) {
        super(context, R.layout.pedido_layout, lista );
        this.context = context;
        this.lista = lista;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.pedido_layout, parent, false);

        TextView dataTxtVw = (TextView) rowView.findViewById(R.id.txtvw_data_pedido);
        TextView precoTxtVw = (TextView) rowView.findViewById(R.id.txtvw_valor_pedido);
        TextView statusTxtVw = (TextView) rowView.findViewById(R.id.txtvw_status_pedido);
        ImageView detalhesImgVw = (ImageView) rowView.findViewById(R.id.imgvw_detalhar_pedido);

        Pedido pedido = lista.get(position);

        MovimentacaoPedido mov = pedido.getUltimaMovimentacao();

        dataTxtVw.setText(FormatHelper.formatarDataHora(mov.getData()));
        precoTxtVw.setText(pedido.getValorFormatado());
        statusTxtVw.setText(mov.getStatus().getDescricao());
        detalhesImgVw.setTag(position);

        int color = MovimentacaoPedidoHelper.getColorFromStatus(mov.getStatus());

        ViewHelper.setBackgroundColor(color, rowView,dataTxtVw,precoTxtVw,statusTxtVw,detalhesImgVw);

        return rowView;
    }
}
