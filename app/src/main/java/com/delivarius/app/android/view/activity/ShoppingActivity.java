package com.delivarius.app.android.view.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import com.delivarius.api.service.exception.ServiceException;
import com.delivarius.app.R;
import com.delivarius.app.android.view.adapter.StoreAdapter;
import com.delivarius.api.dto.Store;

import java.net.ConnectException;
import java.util.List;

public class ShoppingActivity extends DelivariusActivity {

    private StoreAdapter storeAdapter = null;
    private List<Store> storeList = null;
    private ListView storeListView = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_store_activity);

        try {
            getLoadStoresAsyncTask().execute();
        } catch (ConnectException e) {
            showToastLong(getString(R.string.connetion_server_failed));
        }

    }

    public void selectStore(View view){
        Store selectedStore = (Store) view.getTag();
        if(selectedStore != null){

        }
    }


    private LoadStoresAsyncTask getLoadStoresAsyncTask() throws ConnectException {
        verifyInternetConnection();
        return new LoadStoresAsyncTask();
    }

    private class LoadStoresAsyncTask extends AsyncTask<Void,Void, List<Store>> {

        /*public LoadStoresAsyncTask(){
            setWaitMessage(getString(R.string.loading_stores_wait_message));
        }*/

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
            super.onPostExecute(_storeList);
            if(_storeList != null){
                storeList = _storeList;
                storeAdapter = new StoreAdapter(ShoppingActivity.this, storeList );
                storeListView = findViewById(R.id.storeListView);
                storeListView.setAdapter(storeAdapter);
            } else {
                showToastLong(getString(R.string.fail_load_stores));
            }
        }
    }






}
