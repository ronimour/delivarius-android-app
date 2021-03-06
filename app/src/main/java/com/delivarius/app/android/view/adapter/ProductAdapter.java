package com.delivarius.app.android.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.delivarius.api.dto.Product;
import com.delivarius.app.android.view.helper.ViewHelper;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private List<Product> list = null;
    private LayoutInflater inflater = null;
    private int layoutResource;

    public ProductAdapter(@NonNull Context context, int layoutResource, @NonNull List<Product> objects) {
        super(context, layoutResource, objects);
        this.layoutResource = layoutResource;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rowView = inflater.inflate(this.layoutResource, parent, false);
        Product product = list.get(position);

        ViewHelper.inflateView(rowView, product);

        return rowView;
    }


}
