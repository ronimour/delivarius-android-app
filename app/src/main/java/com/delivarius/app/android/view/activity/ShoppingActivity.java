package com.delivarius.app.android.view.activity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.delivarius.api.dto.ItemOrder;
import com.delivarius.api.dto.Order;
import com.delivarius.api.dto.Product;
import com.delivarius.api.service.exception.ServiceException;
import com.delivarius.app.R;
import com.delivarius.app.android.view.adapter.ItemOrderAdapter;
import com.delivarius.app.android.view.adapter.ProductAdapter;
import com.delivarius.app.android.view.adapter.StoreAdapter;
import com.delivarius.api.dto.Store;
import com.delivarius.app.android.view.exception.AmountNegativeOrZeroException;
import com.delivarius.app.android.view.helper.OrderHelper;
import com.delivarius.app.android.view.helper.ViewHelper;

import java.net.ConnectException;
import java.util.List;

public class ShoppingActivity extends DelivariusActivity {

    private StoreAdapter storeAdapter = null;
    private List<Store> storeList = null;
    private ListView storeListView = null;

    private ProductAdapter productAdapter = null;
    private List<Product> productList = null;
    private ListView productListView = null;

    private ItemOrderAdapter itemOrderAdapter = null;
    private List<ItemOrder> itemOrderList = null;
    private ListView itemOrderListView = null;

    private final ReturnToStoreOnClickListener returnToStoreOnClickListener = new ReturnToStoreOnClickListener();

    private final AddProductToCartOnClickListener addProductToCartOnClickListener = new AddProductToCartOnClickListener();

    private final RemoveProductFromCartOnClickListener removeProductFromCartOnClickListener = new RemoveProductFromCartOnClickListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getLoadStoresAsyncTask().execute();
        } catch (ConnectException e) {
            showToastLong(getString(R.string.connetion_server_failed));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shopping_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.cartItem:
                loadCart();
                break;
            case R.id.logoutItem:
                setResult(RESULT_LOGOUT);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void loadCart(View view){
        loadCart();
    }

    private void loadCart() {
        if(currentOrder != null && currentOrder.getItems().size() > 0){
            setContentView(R.layout.cart_activity);
            loadMenuBar(true,false, R.id.menuBarLayout);

            itemOrderList = currentOrder.getItems();
            itemOrderAdapter = new ItemOrderAdapter(ShoppingActivity.this, R.layout.item_order_product_layout, itemOrderList);
            itemOrderListView = findViewById(R.id.itemOrderListView);
            itemOrderListView.setAdapter(itemOrderAdapter);

            findViewById(R.id.searchProductsTextView).setTag(currentOrder.getStore());
            updateTotalPriceOrder();

        } else {
            showToastLong(getString(R.string.empty_cart_message));
        }
    }

    private void updateTotalPriceOrder() {
        TextView totalOrderTextView = (TextView) findViewById(R.id.totalOrderTextView);
        if(totalOrderTextView != null)
            totalOrderTextView.setText(OrderHelper.getTotal(currentOrder).toString());
    }

    public void selectStore(View view) {
        Store selectedStore = (Store) view.getTag();
        if (selectedStore != null) {
            setContentView(R.layout.list_product_activity);
            loadMenuBar(true,true, R.id.menuBarLayout);

            LayoutInflater inflater = getLayoutInflater();
            View storeView = inflater.inflate(R.layout.store_layout, null, false);
            ImageView actionIcon = storeView.findViewById(R.id.selectStoreImageView);
            actionIcon.setImageDrawable(getDrawable(R.drawable.ic_reply_s));
            actionIcon.setOnClickListener(returnToStoreOnClickListener);
            ViewHelper.inflateView(storeView, selectedStore);
            LinearLayout storeLinearLayout = findViewById(R.id.storeLinearLayout);
            storeLinearLayout.addView(storeView);

            productList = selectedStore.getProducts();
            productAdapter = new ProductAdapter(ShoppingActivity.this, R.layout.product_layout, productList);
            productListView = findViewById(R.id.productListView);
            productListView.setAdapter(productAdapter);

            currentStore = selectedStore;
        }
    }

    public void addProductToCart(View view) {
        addProductToCartOnClickListener.setView(view);
        String message = null;
        if(currentOrder != null && !currentStore.equals(currentOrder.getStore())){
            message = getString(R.string.add_product_cart_message_remove_current_order);
            addProductToCartOnClickListener.setRemoveCurrentOrder(true);
        } else {
            message = getString(R.string.add_product_cart_message);
            addProductToCartOnClickListener.setRemoveCurrentOrder(false);
        }
        showDialogYesOrNo(addProductToCartOnClickListener, cancelDialog,
                message);
    }

    public void incrementProduct(View view){
        ItemOrder itemOrder = (ItemOrder) view.getTag();
        if(itemOrder != null){
            OrderHelper.increment(itemOrder, 1);
            updateTotalPriceOrder();
            itemOrderAdapter.notifyDataSetChanged();
        }
    }

    public void decrementProduct(View view){
        ItemOrder itemOrder = (ItemOrder) view.getTag();
        if(itemOrder != null){
            try {
                OrderHelper.decrement(itemOrder, 1);
                updateTotalPriceOrder();
                itemOrderAdapter.notifyDataSetChanged();
            } catch (AmountNegativeOrZeroException e){
                removeProduct(view);
            }
        }
    }

    public void removeProduct(View view){
        removeProductFromCartOnClickListener.setView(view);
        showDialogYesOrNo(removeProductFromCartOnClickListener, cancelDialog,
                getString(R.string.remove_product_cart_message));
    }

    private abstract class ProductCartOnClickListener implements Dialog.OnClickListener{

        private View view;

        public void setView(View view) {
            this.view = view;
        }

        public View getView() {
            return view;
        }
    }

    private class RemoveProductFromCartOnClickListener extends ProductCartOnClickListener{

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            ItemOrder item = (ItemOrder) getView().getTag();
            if(item != null){


            }
        }
    }

    private class AddProductToCartOnClickListener extends ProductCartOnClickListener {

        private boolean removeCurrentOrder;

        public void setRemoveCurrentOrder(boolean removeCurrentOrder) {
            this.removeCurrentOrder = removeCurrentOrder;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Product product = (Product) getView().getTag();
            if (product != null) {
                try {
                    AddItemOrderAsyncTack addItemOrderAsyncTack = getAddItemOrderAsyncTack();
                    addItemOrderAsyncTack.setRemoveCurrentOrder(removeCurrentOrder);

                    ItemOrder itemOrder = new ItemOrder();
                    itemOrder.setProduct(product);
                    itemOrder.setAmount(1);
                    OrderHelper.updateTotalPrice(itemOrder);

                    addItemOrderAsyncTack.execute(itemOrder);

                } catch (ConnectException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private class ReturnToStoreOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            loadStores(storeList);
        }
    }

    private LoadStoresAsyncTask getLoadStoresAsyncTask() throws ConnectException {
        verifyInternetConnection();
        return new LoadStoresAsyncTask();
    }

    private AddItemOrderAsyncTack getAddItemOrderAsyncTack() throws ConnectException {
        verifyInternetConnection();
        return new AddItemOrderAsyncTack();
    }

    private class RemoveItemOrderAsyncTack extends AsyncTask<ItemOrder, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProcessDialog(ShoppingActivity.this, getString(R.string.removing_product_from_order_wait_message));
        }

        @Override
        protected Boolean doInBackground(ItemOrder... itemOrders) {
            ItemOrder item = itemOrders[0];
            boolean itemRemoved = false;
            try {
                 itemRemoved = getOrderService().removeItem(item);
                 if(itemRemoved) {
                     currentOrder.getItems().remove(item);
                 }
            } catch (ServiceException e) {
                e.printStackTrace();
                cancel(true);
            }

            return itemRemoved;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            dismissProcessDialog();
            showToastLong(getString(R.string.fail_to_remove_product));
        }

        @Override
        protected void onPostExecute(Boolean itemRemoved) {
            super.onPostExecute(itemRemoved);
            dismissProcessDialog();
            if (itemRemoved) {
                showToastLong(getString(R.string.removed_success_product));
                if(currentOrder.getItems().size() == 0){
                    View view = new View(ShoppingActivity.this);
                    view.setTag(currentOrder.getStore());
                    selectStore(view);
                } else {
                    itemOrderAdapter.notifyDataSetChanged();
                }
            } else{
                cancel(false);
            }
        }
    }

    private class AddItemOrderAsyncTack extends AsyncTask<ItemOrder, Void, ItemOrder> {

        private boolean removeCurrentOrder;

        public boolean isRemoveCurrentOrder() {
            return removeCurrentOrder;
        }

        public void setRemoveCurrentOrder(boolean removeCurrentOrder) {
            this.removeCurrentOrder = removeCurrentOrder;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProcessDialog(ShoppingActivity.this, getString(R.string.adding_product_to_order_wait_message));
        }

        @Override
        protected ItemOrder doInBackground(ItemOrder... itemOrders) {
            ItemOrder itemOrder = itemOrders[0];
            if(isRemoveCurrentOrder() || currentOrder == null){
                try {
                    boolean isNewOrderOrCurrentOrderWasRemoved = currentOrder == null ? true : getOrderService().removeOrder(currentOrder);

                    if(isNewOrderOrCurrentOrderWasRemoved){
                        currentOrder = new Order();
                        currentOrder.setStore(currentStore);
                        currentOrder.setUser(currentUser);
                        itemOrder.setOrderId(null);
                        currentOrder.getItems().add(itemOrder);
                        currentOrder = getOrderService().createOrder(currentOrder);
                        if(currentOrder != null) {
                            itemOrder = currentOrder.getItems().get(0);
                        } else {
                            cancel(true);
                        }
                    } else{
                        cancel(true);
                    }

                } catch (ServiceException e) {
                    e.printStackTrace();
                    cancel(true);
                }
            } else {
                try {
                    itemOrder.setOrderId(currentOrder.getId());
                    itemOrder = getOrderService().addItem(itemOrder);
                    if(itemOrder != null) {
                        currentOrder.getItems().add(itemOrder);
                    } else {
                        cancel(true);
                    }
                } catch (ServiceException e) {
                    e.printStackTrace();
                    cancel(true);
                }
            }
            return itemOrder;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            dismissProcessDialog();
            showToastLong(getString(R.string.fail_to_add_product_to_cart));
        }

        @Override
        protected void onPostExecute(ItemOrder itemOrder) {
            super.onPostExecute(itemOrder);
            dismissProcessDialog();
            showToastLong(itemOrder.getProduct().getName()+" "+getString(R.string.product_name_added_success));
        }
    }

    private class LoadStoresAsyncTask extends AsyncTask<Void, Void, List<Store>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProcessDialog(ShoppingActivity.this, getString(R.string.loading_stores_wait_message));
        }

        @Override
        protected List<Store> doInBackground(Void... voids) {
            List<Store> storeList = null;

            try {
                storeList = getStoreService().getAll();
            } catch (ServiceException e) {
                e.printStackTrace();
            }

            return storeList;
        }

        @Override
        protected void onPostExecute(List<Store> _storeList) {
            dismissProcessDialog();
            super.onPostExecute(_storeList);
            if (_storeList != null) {
                loadStores(_storeList);
            } else {
                showToastLong(getString(R.string.fail_load_stores));
            }
        }
    }

    private void loadStores(List<Store> _storeList) {
        setContentView(R.layout.list_store_activity);
        loadMenuBar(true,true, R.id.menuBarLayout);
        storeList = _storeList;
        storeAdapter = new StoreAdapter(ShoppingActivity.this, storeList);
        storeListView = findViewById(R.id.storeListView);
        storeListView.setAdapter(storeAdapter);
    }


}
