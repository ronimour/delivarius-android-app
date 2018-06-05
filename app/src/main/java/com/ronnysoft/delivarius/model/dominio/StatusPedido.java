package com.ronnysoft.delivarius.model.dominio;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum StatusPedido {

    ABERTO(0,"Aberto"),
    CADASTRADO(1,"Cadastrado"),
    ATENDIDO(2,"Atendido"),
    NEGADO(3,"Negado"),
    CANCELADO(4,"Cancelado"),
    EM_ENTREGA(5,"Em Entrega"),
    ENTREGUE(6,"Entregue"),
    FINALIZADO(7,"Finalizado");

    StatusPedido(int id, String descricao){
        this.id = id;
        this.descricao = descricao;
    }
    int id;
    String descricao;

    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public static StatusPedido getById(int id){
        /*List<StatusPedido> list = Arrays.asList(values())
                .stream().filter(s -> s.id == id)
                .collect(Collectors.toList());
        return !list.isEmpty() ? list.get(0) : null;*/
        for(StatusPedido status: values()){
            if(status.getId() == id)
                return status;
        }
        return null;
    }

}
