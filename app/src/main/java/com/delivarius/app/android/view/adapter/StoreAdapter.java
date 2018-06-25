package com.delivarius.app.android.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.delivarius.delivarius_api.dto.Store;

public class StoreAdapter extends ArrayAdapter<Store> {

    public StoreAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
