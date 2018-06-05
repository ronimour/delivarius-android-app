package com.ronnysoft.delivarius.model.dominio;

import java.util.ArrayList;
import java.util.List;

public class Loja implements Entidade, Descritivel {

    public static final String LOJA_INFO = "LOJA_INFO";

    private long id;
    private String nome;
    private String descricao;
    private String endereco;
    private String imagem;
    private List<String> categorias;
    private List<Produto> produtos;

    private List<Menu> menus;

    public Loja(long id, String nome, String descricao, String endereco, String imagem) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.endereco = endereco;
        this.imagem = imagem;
        this.setProdutos(new ArrayList<Produto>());
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public String getDescricao() {
        return descricao;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getCategoriasString(){
        StringBuilder builder = new StringBuilder();
        if(categorias != null){
            int count = 1;
            for(String cat : categorias) {
                if(count < categorias.size()) {
                    builder.append(cat + ",");
                } else {
                    builder.append(cat + ".");
                }
                count++;
            }
        }
        return builder.toString();
    }

    public List<Menu> getMenus() {

        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
