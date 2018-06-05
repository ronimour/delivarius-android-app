package com.ronnysoft.delivarius.model.database;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ronnysoft.delivarius.model.dominio.ItemPedido;
import com.ronnysoft.delivarius.model.dominio.Loja;
import com.ronnysoft.delivarius.model.dominio.MovimentacaoPedido;
import com.ronnysoft.delivarius.model.dominio.Pedido;
import com.ronnysoft.delivarius.model.dominio.Produto;
import com.ronnysoft.delivarius.model.dominio.StatusPedido;
import com.ronnysoft.delivarius.view.helper.GsonHelper;
import com.ronnysoft.delivarius.view.helper.MovimentacaoPedidoHelper;
import com.ronnysoft.delivarius.view.helper.TempoHelper;
import com.ronnysoft.delivarius.view.helper.ValidatorHelper;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

public class Database {
    private static final String TAG = "Database";

    public static final int INTERVALO_ATUALIZACAO_SEGUNDOS = 30;



    private static ArrayList<Loja> lojas = new ArrayList<>();
    private static ArrayList<Produto> produtos = new ArrayList<>();
    private static ArrayList<Pedido> pedidos = new ArrayList<>();
    private static Pedido pedidoAberto = null;
    private static Loja lojaAtual = null;

    static {
        int cont = 0;

        Produto cocaCola = new Produto(++cont,"Coca-cola 2L", new BigDecimal(4.79),10,
                "coca_cola_2l.png",
                "Cola-cola em garrafa de plástico com 2 litros");

        Produto paoForma = new Produto(++cont,"Pão de forma integral Pullman 550G", new BigDecimal(6.25),5,
                "pao_forma_integral_pullman_550g.png",
                "pão de forma integral da marca Pullman, pacote com 10 fatis, pesando 550 gramas ao todo");

        Produto carvao = new Produto(++cont,"Carvão Cariocão saco 5kg", new BigDecimal(8.12),20,
                "carvao_cariocao.png",
                "Saco de carvão pronto para churrasco, com 5 kilogramas");

        Produto gelo = new Produto(++cont,"Gelo Qualitá 5kg", new BigDecimal(3.6),20,
                "gelo_qualita_5kg.png",
                "Saco de gelo 5 kilogramas");


        Produto aguaMin1 = new Produto(++cont,"Agua mineral Hydrate 20L", new BigDecimal(6),20,
                "agua_mineral_hydrate_20l.png",
                "Agua mineral em garrafão de plástico com 20 litros. Obs.: vasilhame não incluso");
        Produto aguaMin2 = new Produto(++cont,"Agua mineral Fontes Debelém 20L", new BigDecimal(8),10,
                "agua_mineral_fontes_debelem_20l.png",
                "Agua mineral em garrafão de plástico com 2 litros. Obs.: vasilhame não incluso");
        Produto aguaMin3 = new Produto(++cont,"Agua mineral Indaiá 20L", new BigDecimal(7),40,
                "agua_mineral_indaia_20l.png",
                "Agua mineral em garrafão de plástico com 2 litros. Obs.: vasilhame não incluso");
        Produto aguaMin4 = new Produto(++cont,"Agua mineral Ingá 20L", new BigDecimal(6),60,
                "agua_mineral_inga_20l.png",
                "Agua mineral em garrafão de plástico com 2 litros. Obs.: vasilhame não incluso");
        Produto aguaMin5 = new Produto(++cont,"Agua mineral Sarandí 20L", new BigDecimal(5),35,
                "agua_mineral_sarandi_20l.png",
                "Agua mineral em garrafão de plástico com 2 litros. Obs.: vasilhame não incluso");
        Produto aguaMin6 = new Produto(++cont,"Agua mineral Santa Maria 20L", new BigDecimal(5),12,
                "agua_mineral_santa_maria_20l.png",
                "Agua mineral em garrafão de plástico com 2 litros. Obs.: vasilhame não incluso");



        Produto cervejaLata1 = new Produto(++cont,"Cerveja Itaipava lata 350ML", new BigDecimal(3.5),10,
                "cerveja_itaipava_lata_350ml.png",
                "Cerveja em lata Itaipava, unidade");

        Produto cervejaLata2 = new Produto(++cont,"Cerveja Antartica lata 350ML", new BigDecimal(3),10,
                "cerveja_antartica_lata_350ml.png",
                "Cerveja em lata Itaipava, unidade");

        Produto cervejaLata3 = new Produto(++cont,"Cerveja Brahma lata 350ML", new BigDecimal(2.5),10,
                "cerveja_brahma_lata_350ml.png",
                "Cerveja em lata Brahma, unidade");

        Produto cervejaLata4 = new Produto(++cont,"Cerveja Budweiser lata 350ML", new BigDecimal(5),10,
                "cerveja_budweiser_lata_350ml.png",
                "Cerveja em lata Budweiser, unidade");

        Produto cervejaLata5 = new Produto(++cont,"Cerveja Devassa lata 269ML", new BigDecimal(4),10,
                "cerveja_devassa_lata_269ml.png",
                "Cerveja em lata Devassa, unidade");

        Produto cervejaLata6 = new Produto(++cont,"Cerveja Eisebahn lata 350ML", new BigDecimal(6),10,
                "cerveja_eisenbahn_lata_350ml.png",
                "Cerveja em lata Eisenbahn, unidade");

        Produto cervejaLata7 = new Produto(++cont,"Cerveja Heineken lata 350ML", new BigDecimal(6),10,
                "cerveja_heineken_lata_350ml.png",
                "Cerveja em lata Heineken, unidade");

        Produto cervejaLata8 = new Produto(++cont,"Cerveja Skol lata 350ML", new BigDecimal(3.5),10,
                "cerveja_skol_lata_350ml.png",
                "Cerveja em lata Skol, unidade");


        produtos.add(cocaCola);
        produtos.add(paoForma);
        produtos.add(carvao);
        produtos.add(gelo);

        produtos.add(aguaMin1);
        produtos.add(aguaMin2);
        produtos.add(aguaMin3);
        produtos.add(aguaMin4);
        produtos.add(aguaMin5);
        produtos.add(aguaMin6);

        produtos.add(cervejaLata1);
        produtos.add(cervejaLata2);
        produtos.add(cervejaLata3);
        produtos.add(cervejaLata4);
        produtos.add(cervejaLata5);
        produtos.add(cervejaLata6);
        produtos.add(cervejaLata7);
        produtos.add(cervejaLata8);

        Loja l1 = new Loja(1, "Conveniência Gelo e Gela 24 horas",
                "Conveniência que funciona 24 horas, para tomar aquela gela. Telefone: (84) 2020-8930. ",
                "Av. São Miguel dos Caribés, 8 - Neópolis, Natal - RN, 59088-500",
                "conveniencia_gelo_e_gela_24h.png");


        Loja l2 = new Loja(2, "Super Conveniência Horizonte",
                "Conveniência que funciona todos os dias, das 06 às 22 horas. Telefone: (84) 3217-7778. ",
                "Av. Dão Silveira, 3712 - Candelária, Natal - RN, 59066-180",
                "super_conveniencia_horizonte.png");



        Loja l3 = new Loja(3, "Empório Conveniência 24h",
                "Conveniência que funciona 24 horas. Telefone: (84) 2010-8252. ",
                "Av. Prudente de Morais, 6399 - Lagoa Nova, Natal - RN, 59064-630",
                "emporio_conveniencia_24h.png");


        for(int i = 4; i < 10; i++)
            l3.getProdutos().add(produtos.get(i));

        for(int i = 0; i < 4; i++)
            l2.getProdutos().add(produtos.get(i));

        for(int i = 10; i < produtos.size(); i++)
            l1.getProdutos().add(produtos.get(i));

        lojas.add(l1);
        lojas.add(l2);
        lojas.add(l3);

    }

    public static Loja findLojaById(long id){
/*        List<Loja> result = lojas.stream()
                .filter( l -> l.getId() == id )
                .collect(Collectors.toList());

        return !result.isEmpty() ? result.get(0) : null;*/
        for(Loja loja : lojas) {
            if (loja.getId() == id) {
                return loja;
            }
        }
        return null;
    }

    public static Produto findProdutoById(long id){
/*        List<Produto> result = produtos.stream()
                .filter( p -> p.getId() == id )
                .collect(Collectors.toList());

        return !result.isEmpty() ? result.get(0) : null;*/
        for(Produto produto : produtos) {
            if (produto.getId() == id) {
                return produto;
            }
        }
        return null;
    }

    public static final String FILENAME_PEDIDOS = "pedidos.json";


    public static ArrayList<Produto> getProdutos(){
        return produtos;
    }

    public static ArrayList<Pedido> getPedidos(){
        return pedidos;
    }

    public static ArrayList<Loja> getLojas(){
        return lojas;
    }

    public static void decrementarQtdItemPedido(ItemPedido item){
        item.setQuantidade(item.getQuantidade()-1);
    }

    public static void incrementarQtdItemPedido(ItemPedido item){
        item.setQuantidade(item.getQuantidade()+1);
    }


    public static void removerItemPedido(ItemPedido item){
        if(pedidoAberto.getItens() != null){
            pedidoAberto.getItens().remove(item);
        }
    }
    public static void addItemPedido(Produto produto, int qtd){
        if(pedidoAberto == null){
           pedidoAberto = new Pedido();
           pedidoAberto.setId(pedidos.size()+1);
           MovimentacaoPedidoHelper.addMovimentacaoAbertura(pedidoAberto);
           pedidoAberto.setLoja(lojaAtual);
        }

        pedidoAberto.addItem(produto,qtd);

    }

    public static Pedido getPedidoAberto(){
        return pedidoAberto;
    }

    public static void removerPedidoAberto(){
        pedidoAberto = null;
    }

    public static void setLojaAtual(Loja atual){
        lojaAtual = atual;
    }

    public static Loja getLojaAtual(){
        return lojaAtual;
    }

    public static void removerLojaAtual(){
        lojaAtual = null;
    }


    public static Pedido salvarPedidoAberto(String nome, String endereco, String telefone, Context context) {
        pedidoAberto.setValor(pedidoAberto.getValorTotalCalculado());

        pedidoAberto.setNomeCliente(nome);
        pedidoAberto.setEnderecoEntrega(endereco);
        pedidoAberto.setTelefone(telefone);

        MovimentacaoPedidoHelper.addMovimentacaoCadastro(pedidoAberto);

        pedidoAberto.setLojaId(pedidoAberto.getLoja().getId());

        for(ItemPedido it: pedidoAberto.getItens()){
            it.setValor(it.getValorTotalCalculado());
            it.setProdutoId(it.getProduto().getId());
        }

        Pedido pedidoSalvo = pedidoAberto;
        pedidos.add(pedidoSalvo);
        removerPedidoAberto();

        salvarPedidosToJSONFile(GsonHelper.defaultGson.toJson(pedidos),context);

        return pedidoSalvo;

    }

    public static void carregarPedidos(Context context) {
        String jsonPedidos = carregarPedidosFromJSONFile(context);
        if(jsonPedidos != null) {
            try {
                ArrayList<Pedido> pedidos_ = GsonHelper.getPedidosFromJSON(jsonPedidos);
                inicializar(pedidos_);
                pedidos.clear();
                pedidos.addAll(pedidos_);
                Log.i(TAG, "Pedidos carregados");
            } catch (JSONException e) {
                Log.e(TAG, "Erro ao ler arquivo json", e);
            }
        }

    }

    @NonNull
    private static void inicializar(ArrayList<Pedido> pedidos) {
        for(Pedido p: pedidos){
           p.setLoja(findLojaById(p.getLojaId()));
           for(MovimentacaoPedido mov : p.getMovimentacoes()){
               mov.setStatus(StatusPedido.getById(mov.getStatusId()));
           }
           for(ItemPedido it : p.getItens()){
               it.setProduto(findProdutoById(it.getProdutoId()));
           }
        }
    }


    private static void salvarPedidosToJSONFile(String json, Context context) {
        try {
            File folder = getDefaultStorage(context);
            File file = new File(folder, FILENAME_PEDIDOS);

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(json.getBytes());
            fos.close();
            Log.i(TAG, "Arquivo salvo");

        } catch (Exception e) {
            Log.e(TAG, "Erro salvando arquivo", e);
        }
    }


    private static String carregarPedidosFromJSONFile(Context context) {
        try {
            File folder = getDefaultStorage(context);
            File file = new File(folder,FILENAME_PEDIDOS);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder json = new StringBuilder();
            String line;

            while((line = br.readLine()) != null ){
                json.append(line);
            }

            br.close();
            isr.close();
            fis.close();
            Log.i(TAG, "Arquivo carregado");

            return json.toString();

        } catch (FileNotFoundException e) {
            Log.e(TAG, "Arquivo não encontrado",e);
            return null;
        } catch (IOException e) {
            Log.e(TAG, "Erro ao ler arquivo",e);
            return null;
        }
    }

    private static File getDefaultStorage(Context context) {
        return context.getFilesDir();
    }

    public static void atualizarStatusPedidos(Context context){

        carregarPedidos(context);

        if(ValidatorHelper.isNotEmpty(pedidos)){
            Date now = new Date();
            for(Pedido pedido : pedidos){
                MovimentacaoPedido ultima = pedido.getUltimaMovimentacao();
                StatusPedido status = ultima != null ? ultima.getStatus() : StatusPedido.ABERTO;
                if(!status.equals(StatusPedido.ABERTO) && !status.equals(StatusPedido.FINALIZADO)){
                    Date dataMov = ultima.getData();
                    if(TempoHelper.segundosBetween(dataMov,now) > INTERVALO_ATUALIZACAO_SEGUNDOS){
                        MovimentacaoPedidoHelper.criarProxima(pedido);
                    }
                }
            }
        }

        salvarPedidosToJSONFile(GsonHelper.defaultGson.toJson(pedidos),context);

        Log.i(TAG, "Os status dos pedidos foram atualizados");

    }


    private static boolean isExternalStorageMounted(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
