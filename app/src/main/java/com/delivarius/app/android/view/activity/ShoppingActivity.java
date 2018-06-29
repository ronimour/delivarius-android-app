package com.delivarius.app.android.view.activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.delivarius.api.dto.Product;
import com.delivarius.api.service.exception.ServiceException;
import com.delivarius.app.R;
import com.delivarius.app.android.view.adapter.ProductAdapter;
import com.delivarius.app.android.view.adapter.StoreAdapter;
import com.delivarius.api.dto.Store;
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

    private final ReturnToStoreOnClickListener returnToStoreOnClickListener = new ReturnToStoreOnClickListener();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            getLoadStoresAsyncTask().execute();
        } catch (ConnectException e) {
            showToastLong(getString(R.string.connetion_server_failed));
        }

    }

    public void selectStore(View view){
        Store selectedStore = (Store) view.getTag();
        if(selectedStore != null){
            setContentView(R.layout.list_product_activity);


            LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View storeView = inflater.inflate(R.layout.store_layout, null, false);
            ImageView actionIcon = storeView.findViewById(R.id.selectStoreImageView);
            actionIcon.setImageDrawable(getDrawable(R.drawable.ic_reply));
            actionIcon.setOnClickListener(returnToStoreOnClickListener);
            ViewHelper.inflateView(storeView,selectedStore);
            LinearLayout storeLinearLayout = findViewById(R.id.storeLinearLayout);
            storeLinearLayout.addView(storeView);

            productList = selectedStore.getProducts();
            productAdapter = new ProductAdapter(ShoppingActivity.this, productList);
            productListView = findViewById(R.id.productListView);
            productListView.setAdapter(productAdapter);

            OrderHelper.setStore(currentOrder, selectedStore);

        }
    }

    public void addProductToCart(View view){


    }

    private class ReturnToStoreOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            loadStores(storeList);
        }
    }

    private LoadStoresAsyncTask getLoadStoresAsyncTask() throws ConnectException {
        verifyInternetConnection();
        return new LoadStoresAsyncTask();
    }

    private class LoadStoresAsyncTask extends AsyncTask<Void,Void, List<Store>> {

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
            if(_storeList != null){
                loadStores(_storeList);
            } else {
                showToastLong(getString(R.string.fail_load_stores));
            }
        }
    }

    private void loadStores(List<Store> _storeList){
        setContentView(R.layout.list_store_activity);
        storeList = _storeList;
        storeAdapter = new StoreAdapter(ShoppingActivity.this, storeList );
        storeListView = findViewById(R.id.storeListView);
        storeListView.setAdapter(storeAdapter);
    }





}
