package com.delivarius.android.app.model.dominio;


import java.util.Date;

public class MovimentacaoPedido implements Entidade, Comparable {

    private long id;
    private Date data;
    private transient StatusPedido status;
    private int statusId;

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    @Override
    public int compareTo(Object mov) {
        if(mov instanceof MovimentacaoPedido){
            return data.compareTo(((MovimentacaoPedido) mov).data);
        }
        return 0;
    }
}
