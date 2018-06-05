package com.ronnysoft.delivarius.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ronnysoft.delivarius.R;
import com.ronnysoft.delivarius.model.database.Database;
import com.ronnysoft.delivarius.model.dominio.Pedido;
import com.ronnysoft.delivarius.view.helper.FormatHelper;
import com.ronnysoft.delivarius.view.helper.GsonHelper;
import com.ronnysoft.delivarius.view.helper.ValidatorHelper;

public class ConclusaoPedidoActivity extends Activity {


    private EditText nomeTxtVw;
    private EditText enderecoTxtVw;
    private EditText telefoneTxtVw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dados_pessoais_pedido_activity);

        carregarViews();

        TextView totalTxtVw = (TextView) findViewById(R.id.txtvw_total_item_pedido);
        totalTxtVw.setText(FormatHelper.formatarMoeda(Database.getPedidoAberto().getValorTotalCalculado()));

    }

    public void concluir(View view){

        if(Database.getPedidoAberto() != null) {

            String nome = nomeTxtVw.getText().toString();
            String endereco = enderecoTxtVw.getText().toString();
            String telefone = telefoneTxtVw.getText().toString();

            StringBuilder msgErro = new StringBuilder();
            msgErro.append("Campo(s) obrigatório(s) não informado(s):");

            if (ValidatorHelper.isEmpty(nome))
                msgErro.append(" Nome,");

            if (ValidatorHelper.isEmpty(endereco))
                msgErro.append(" Endereço de entrega,");

            if (ValidatorHelper.isEmpty(telefone))
                msgErro.append(" Telefone para contato,");

            if (ValidatorHelper.isAnyEmpty(nome, endereco, telefone)) {
                String msgErroString = msgErro.toString();
                msgErroString = msgErroString.substring(0, msgErroString.length() - 1) + ".";
                Toast toast = Toast.makeText(this, msgErroString, Toast.LENGTH_LONG);
                toast.show();
            } else {

                Pedido pedidoSalvo = Database.salvarPedidoAberto(nome, endereco, telefone, this);
                Gson gson = GsonHelper.defaultGson;
                Log.i("JSON", gson.toJson(Database.getPedidos()));

                salvarPreferences(nome,endereco,telefone);

                Intent acao = new Intent(this, InicioActivity.class);
                startActivity(acao);

                Toast toast = Toast.makeText(this, "Pedido registrado com sucesso", Toast.LENGTH_LONG);
                toast.show();

            }
        } else {
            Toast toast = Toast.makeText(this, "Pedido já registrado", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    private void salvarPreferences(String nome, String endereco, String telefone) {
        SharedPreferences preferences = getPreferences(Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString("nome",nome);
        editor.putString("endereco",endereco);
        editor.putString("telefone",telefone);

        editor.commit();
    }

    private void carregarViews() {
        nomeTxtVw = (EditText) findViewById(R.id.edttxt_nome_cliente_pedido);
        enderecoTxtVw = (EditText) findViewById(R.id.edttxt_endereco_entrega_pedido);
        telefoneTxtVw = (EditText) findViewById(R.id.edttxt_telefone_contato_pedido);

        SharedPreferences preferences = getPreferences(Activity.MODE_PRIVATE);

        nomeTxtVw.setText(preferences.getString("nome",""));
        enderecoTxtVw.setText(preferences.getString("endereco",""));
        telefoneTxtVw.setText(preferences.getString("telefone",""));
    }

}
