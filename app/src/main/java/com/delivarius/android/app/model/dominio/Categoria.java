package com.delivarius.android.app.model.dominio;

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
