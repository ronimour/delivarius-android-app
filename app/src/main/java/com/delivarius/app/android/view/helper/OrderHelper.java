package com.delivarius.app.android.view.helper;

import com.delivarius.api.dto.ItemOrder;
import com.delivarius.api.dto.Order;
import com.delivarius.api.dto.Product;
import com.delivarius.api.dto.Store;
import com.delivarius.app.android.view.exception.AmountNegativeOrZeroException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderHelper {

    public static void setStore(Order order, Store store){
        if(order.getStore() == null || !order.getStore().equals(store)){
            order.setStore(store);
            order.setItems(new ArrayList<ItemOrder>());
        }
    }

    public static void addProductToOrder(Order order, Product product, int amount){
        ItemOrder item = getItemByProduct(order, product);
        if(item == null){//adding a new item
            item = new ItemOrder();
            item.setProduct(product);
            item.setAmount(amount);
            order.getItems().add(item);
        } else {//updating the existing one
            item.setAmount(item.getAmount()+amount);
        }
        updateTotalPrice(item);
    }

    private static ItemOrder getItemByProduct(Order order, Product product) {
        ItemOrder item = null;
        for(ItemOrder it : order.getItems()){
            if(it.getProduct().equals(product)){
                item = it;
                break;
            }
        }
        return item;
    }

    public static List<Product> getProducts(Order order) {
        List<Product> productList = new ArrayList<>();
        for(ItemOrder it : order.getItems()){
            productList.add(it.getProduct());
        }
        return productList;
    }

    public static BigDecimal getTotal(Order order) {
        BigDecimal total = new BigDecimal(0.0);
        for(ItemOrder it: order.getItems()){
            total = total.add(it.getTotalPrice());
        }
        return total;
    }

    public static void increment(ItemOrder item, int amount) {
        item.setAmount(item.getAmount()+amount);
        updateTotalPrice(item);
    }

    public static void decrement(ItemOrder item, int amount) throws AmountNegativeOrZeroException {
        if(item.getAmount() - amount > 0) {
            item.setAmount(item.getAmount() - amount);
            updateTotalPrice(item);
        } else {
            throw new AmountNegativeOrZeroException();
        }
    }

    public static void updateTotalPrice(ItemOrder item){
        item.setTotalPrice(item.getProduct().getPrice().multiply(new BigDecimal(item.getAmount())));
    }

    public static String getAmount(ItemOrder itemOrder) {
        return String.format("%02d",itemOrder.getAmount());
    }
}
