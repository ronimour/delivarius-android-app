package com.ronnysoft.delivarius.model.dominio;

/**
 * Created by logan on 06/05/2018.
 */

public class Cliente implements Entidade {

    private long id;
    private String nome;
    private String login;
    private String senha;
    private String endereco;

    @Override
    public long getId() {
        return 0;
    }
}
