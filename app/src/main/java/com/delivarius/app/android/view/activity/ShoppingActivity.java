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
        if(currentOrder.getItems().size() > 0){
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

            OrderHelper.setStore(currentOrder, selectedStore);

        }
    }

    public void addProductToCart(View view) {
        addProductToCartOnClickListener.setView(view);
        showDialogYesOrNo(addProductToCartOnClickListener, cancelDialog,
                getString(R.string.add_product_cart_message));
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
                currentOrder.getItems().remove(item);
                if(currentOrder.getItems().size() == 0){
                    getView().setTag(currentOrder.getStore());
                    selectStore(getView());
                } else {
                    itemOrderAdapter.notifyDataSetChanged();
                }
                showToastLong(getString(R.string.removed_success_product));

            }
        }
    }

    private class AddProductToCartOnClickListener extends ProductCartOnClickListener {

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Product product = (Product) getView().getTag();
            if (product != null) {
                OrderHelper.addProductToOrder(currentOrder, product, 1);
                showToastLong(product.getName() + " " + getString(R.string.product_name_added_success));
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
