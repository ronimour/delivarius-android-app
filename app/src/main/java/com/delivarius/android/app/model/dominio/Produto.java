package com.delivarius.android.app.model.dominio;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Produto implements Entidade, Descritivel{

    private long id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private int qtdEstoque;
    private Categoria categoria;
    private String imagem;


    public Produto(long id, String nome, BigDecimal valor, int qtdEstoque, String imagem, String descricao) {
        this.id = id;
        this.nome = nome;
        this.nome = nome;
        this.valor = valor;
        this.qtdEstoque = qtdEstoque;
        this.imagem = imagem;
        this.descricao = descricao;
    }

    @Override
    public long getId() {
        return this.id;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPreco(){
        DecimalFormat decFormat = new DecimalFormat("'R$ ' 0.##");
        return decFormat.format(valor);
    }


}
