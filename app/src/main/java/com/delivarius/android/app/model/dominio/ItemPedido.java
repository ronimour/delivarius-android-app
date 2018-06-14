package com.delivarius.android.app.model.dominio;


import com.delivarius.android.app.view.helper.FormatHelper;

import java.math.BigDecimal;

public class ItemPedido implements Entidade {

    private long id;

    private transient Produto produto;

    private long produtoId;

    private int quantidade;

    private BigDecimal valor;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getValorFormatado(){
        return FormatHelper.formatarMoeda(getValorTotalCalculado());
    }

    public BigDecimal getValorTotalCalculado(){
        return FormatHelper.round2Decimal(produto.getValor().multiply(new BigDecimal(quantidade)));
    }

    public long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(long produtoId) {
        this.produtoId = produtoId;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this)
            return true;
        if(obj instanceof  ItemPedido){
            if(this.id == ((ItemPedido) obj).id && this.id > 0)
                return true;
        }

        return false;
    }
}
