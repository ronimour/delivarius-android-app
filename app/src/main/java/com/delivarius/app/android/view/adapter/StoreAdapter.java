package com.delivarius.app.android.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.delivarius.app.R;
import com.delivarius.app.android.view.helper.ViewHelper;
import com.delivarius.delivarius_api.dto.Store;


import java.util.ArrayList;

public class StoreAdapter extends ArrayAdapter<Store> {

    private ArrayList<Store> list = new ArrayList<>();
    LayoutInflater inflater = null;

    public StoreAdapter(@NonNull Context context, @NonNull ArrayList<Store> objects) {
        super(context, R.layout.store_layout, objects);
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rowView = inflater.inflate(R.layout.store_layout, parent, false);
        Store store = list.get(position);

        ViewHelper.inflateView(rowView,store);

        return rowView;
    }


}
