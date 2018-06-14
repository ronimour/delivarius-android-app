package com.delivarius.android.app.model.dominio;

import java.util.List;

public class Menu implements Entidade, Descritivel {

    private long id;
    private String descricao;

    List<Produto> produtos;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

}
