package com.ronnysoft.delivarius.model.dominio;

import com.ronnysoft.delivarius.view.helper.FormatHelper;
import com.ronnysoft.delivarius.view.helper.ValidatorHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class Pedido implements Entidade{

    public static final String PEDIDO_INFO = "PEDIDO_INFO";

    private long id;
    private List<ItemPedido> itens;
    private String enderecoEntrega;
    private BigDecimal valor;
    private String nomeCliente;
    private String telefone;
    private transient Loja loja;
    private long lojaId;
    private List<MovimentacaoPedido> movimentacoes;


    public Pedido(){
        valor = new BigDecimal(0.0);
        itens = new ArrayList<>();
        movimentacoes = new ArrayList<>();
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    public String getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(String enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Loja getLoja() {
        return loja;
    }

    public void setLoja(Loja loja) {
        this.loja = loja;
    }

    public List<MovimentacaoPedido> getMovimentacoes() {
        return movimentacoes;
    }

    public void setMovimentacoes(List<MovimentacaoPedido> movimentacoes) {
        this.movimentacoes = movimentacoes;
    }

    public void addItem(Produto produto, int qtd){
        ItemPedido item = null;
        for(ItemPedido it : itens){
            if(it.getProduto().equals(produto)){
                item = it;
                break;
            }
        }
        if(item == null){
            item = new ItemPedido();
            item.setId(itens.size()+1);
            item.setProduto(produto);
            item.setQuantidade(qtd);

            itens.add(item);
        } else {
            item.setQuantidade(item.getQuantidade()+qtd);
        }

        valor = valor.add(item.getValorTotalCalculado());
    }

    public long getLojaId() {
        return lojaId;
    }

    public void setLojaId(long lojaId) {
        this.lojaId = lojaId;
    }

    public BigDecimal getValorTotalCalculado(){
        BigDecimal total = new BigDecimal(0);
        for(ItemPedido it : itens){
            total = total.add(it.getValorTotalCalculado());
        }
        return FormatHelper.round2Decimal(total);
    }

    public String getValorFormatado() {
        return FormatHelper.formatarMoeda(valor);
    }

    public MovimentacaoPedido getUltimaMovimentacao() {
        if(ValidatorHelper.isNotEmpty(movimentacoes)) {
            Collections.sort(movimentacoes);
            Collections.reverse(movimentacoes);
            return movimentacoes.get(0);
        } else {
            return null;
        }
    }
}
