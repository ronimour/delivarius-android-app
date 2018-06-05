package com.ronnysoft.delivarius.model.dominio;

public class Categoria implements Entidade, Descritivel {

    private long id;
    private String descricao;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }
}
