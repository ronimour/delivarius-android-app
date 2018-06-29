package com.delivarius.app.android.view.helper;

import com.delivarius.api.dto.ItemOrder;
import com.delivarius.api.dto.Order;
import com.delivarius.api.dto.Store;

import java.util.ArrayList;

public class OrderHelper {

    public static void setStore(Order order, Store store){
        if(order.getStore() == null || !order.getStore().equals(store)){
            order.setStore(store);
            order.setItems(new ArrayList<ItemOrder>());
        }
    }

}
