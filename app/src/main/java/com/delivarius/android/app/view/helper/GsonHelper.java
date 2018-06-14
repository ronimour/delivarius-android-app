package com.delivarius.android.app.view.helper;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.delivarius.android.app.model.dominio.ItemPedido;
import com.delivarius.android.app.model.dominio.MovimentacaoPedido;
import com.delivarius.android.app.model.dominio.Pedido;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GsonHelper {

    private static final String TAG = "GsonHelper";

    public static final Gson defaultGson ;

    private static final String jsonDateFormat = "dd-MM-yyyy'T'HH:mm:ssz";

    private static final DateFormat formatadorDate ;

    static{
        defaultGson = new GsonBuilder().setDateFormat(jsonDateFormat).create();
        formatadorDate = new SimpleDateFormat(jsonDateFormat);
    }

    public static ArrayList<Pedido> getPedidosFromJSON(String listaProdutosJson) throws JSONException {
        ArrayList<Pedido> pedidos = new ArrayList<>();

        JSONArray jsonArray = new JSONArray(listaProdutosJson);

        for( int i = 0;  i< jsonArray.length(); i++){
            JSONObject jsonObjPedido = jsonArray.getJSONObject(i);
            Pedido pedido = new Pedido();
            pedido.setId(jsonObjPedido.getLong("id"));
            pedido.setNomeCliente(jsonObjPedido.getString("nomeCliente"));
            pedido.setEnderecoEntrega(jsonObjPedido.getString("enderecoEntrega"));
            pedido.setTelefone(jsonObjPedido.getString("telefone"));
            pedido.setValor(new BigDecimal(jsonObjPedido.getDouble("valor")));
            pedido.setLojaId(jsonObjPedido.getInt("lojaId"));

            ArrayList<ItemPedido> itens = new ArrayList<>();
            String jsonItensString = jsonObjPedido.getString("itens");
            JSONArray itensJsonArray = new JSONArray(jsonItensString);
            for(int j = 0; j < itensJsonArray.length(); j++){
                JSONObject jsonObjItem = itensJsonArray.getJSONObject(j);
                ItemPedido item = new ItemPedido();
                item.setId(jsonObjItem.getLong("id"));
                item.setProdutoId(jsonObjItem.getLong("produtoId"));
                item.setQuantidade(jsonObjItem.getInt("quantidade"));
                item.setValor(new BigDecimal(jsonObjItem.getDouble("valor")));
                itens.add(item);
            }
            pedido.setItens(itens);

            ArrayList<MovimentacaoPedido> movimentacoes = new ArrayList<>();
            String jsonMovsString = jsonObjPedido.getString("movimentacoes");
            JSONArray movsJsonArray = new JSONArray(jsonMovsString);
            for(int k = 0; k < movsJsonArray.length(); k++){
                JSONObject jsonObjMov = movsJsonArray.getJSONObject(k);
                MovimentacaoPedido mov = new MovimentacaoPedido();
                mov.setId(jsonObjMov.getLong("id"));
                mov.setStatusId(jsonObjMov.getInt("statusId"));
                try {
                    mov.setData(parseDateFromString(jsonObjMov.getString("data")));
                } catch (ParseException e) {
                    Log.e(TAG,"Erro no parse da data do pedido", e);
                }
                movimentacoes.add(mov);
            }
            pedido.setMovimentacoes(movimentacoes);

            pedidos.add(pedido);
        }
        return pedidos;
    }


    @NonNull
    private static Date parseDateFromString(String date) throws ParseException {
        return formatadorDate.parse(date);
    }

}
