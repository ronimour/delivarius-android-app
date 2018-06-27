package com.delivarius.app.android.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.delivarius.api.dto.Product;
import com.delivarius.app.R;
import com.delivarius.app.android.view.helper.ViewHelper;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {

    private List<Product> list = null;
    LayoutInflater inflater = null;

    public ProductAdapter(@NonNull Context context, @NonNull List<Product> objects) {
        super(context, R.layout.product_layout, objects);
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rowView = inflater.inflate(R.layout.product_layout, parent, false);
        Product product = list.get(position);

        ViewHelper.inflateView(rowView,product);

        return rowView;
    }


}
