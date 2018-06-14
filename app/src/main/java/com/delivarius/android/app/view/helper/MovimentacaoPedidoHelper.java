package com.delivarius.android.app.view.helper;


import android.graphics.Color;
import android.support.annotation.NonNull;

import com.delivarius.android.app.model.dominio.MovimentacaoPedido;
import com.delivarius.android.app.model.dominio.Pedido;
import com.delivarius.android.app.model.dominio.StatusPedido;

import java.util.Date;
import java.util.Random;

public class MovimentacaoPedidoHelper {

    @NonNull
    public static void criarProxima(Pedido pedido){
        Random random = new Random();
        int num = random.nextInt(10)+1;
        boolean postivo = num > 0 && num < 8;

        MovimentacaoPedido ultima = pedido.getUltimaMovimentacao();

        MovimentacaoPedido proxima = null;
        switch (ultima.getStatus()){
            case CADASTRADO:
                if(postivo){
                    proxima = criarMovimentacao(pedido,StatusPedido.ATENDIDO);
                } else {
                    proxima = criarMovimentacao(pedido,StatusPedido.NEGADO);
                }
                break;
            case ATENDIDO:
                if(postivo){
                    proxima = criarMovimentacao(pedido,StatusPedido.EM_ENTREGA);
                } else {
                    proxima = criarMovimentacao(pedido,StatusPedido.CANCELADO);
                }
                break;
            case EM_ENTREGA:
                if(postivo){
                    proxima = criarMovimentacao(pedido,StatusPedido.ENTREGUE);
                } else {
                    proxima = criarMovimentacao(pedido,StatusPedido.CANCELADO);
                }
                break;
            case ENTREGUE:
                proxima = criarMovimentacao(pedido, StatusPedido.FINALIZADO);
        }
        if(proxima != null)
            pedido.getMovimentacoes().add(proxima);
    }

    public static void addMovimentacaoAbertura(Pedido pedido){
        pedido.getMovimentacoes().add(criarMovimentacao(pedido, StatusPedido.ABERTO));
    }
    public static void addMovimentacaoCadastro(Pedido pedido){
        pedido.getMovimentacoes().add(criarMovimentacao(pedido, StatusPedido.CADASTRADO));
    }
    public static void addMovimentacaoAtendimento(Pedido pedido){
        pedido.getMovimentacoes().add(criarMovimentacao(pedido, StatusPedido.ATENDIDO));
    }
    public static void addMovimentacaoNegacao(Pedido pedido){
        pedido.getMovimentacoes().add(criarMovimentacao(pedido, StatusPedido.NEGADO));
    }
    public static void addMovimentacaoCancelamento(Pedido pedido){
        pedido.getMovimentacoes().add(criarMovimentacao(pedido, StatusPedido.CANCELADO));
    }
    public static void addMovimentacaoEmEntrega(Pedido pedido){
        pedido.getMovimentacoes().add(criarMovimentacao(pedido, StatusPedido.EM_ENTREGA));
    }
    public static void addMovimentacaoEntrega(Pedido pedido){
        pedido.getMovimentacoes().add(criarMovimentacao(pedido, StatusPedido.ENTREGUE));
    }
    public static void addMovimentacaoFinalizacao(Pedido pedido){
        pedido.getMovimentacoes().add(criarMovimentacao(pedido, StatusPedido.FINALIZADO));
    }

    private static MovimentacaoPedido criarMovimentacao(Pedido pedido, StatusPedido status){
        MovimentacaoPedido mov = new MovimentacaoPedido();
        mov.setId(getId(pedido));
        mov.setData(new Date());
        mov.setStatus(status);
        mov.setStatusId(status.getId());
        return mov;
    }

    private static long getId(Pedido pedido){
        return pedido.getMovimentacoes().size()+1;
    }

    public static int getColorFromStatus(StatusPedido status){
        switch (status){
            case CADASTRADO:
                return Color.rgb(201, 230, 237);
            case ATENDIDO:
                return Color.rgb(147, 203, 216);
            case CANCELADO:
            case NEGADO:
                return Color.rgb(244, 127, 127);
            case EM_ENTREGA:
                return Color.rgb(241, 247, 165);
            case ENTREGUE:
            case FINALIZADO:
                return Color.rgb(149, 242, 130);
            default:
                return Color.rgb(255, 255, 255);
        }
    }
}
