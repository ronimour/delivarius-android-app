package com.delivarius.app.android.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.delivarius.api.dto.ItemOrder;
import com.delivarius.app.android.view.helper.ViewHelper;

import java.util.List;

public class ItemOrderAdapter extends ArrayAdapter<ItemOrder> {

    private List<ItemOrder> list = null;
    private LayoutInflater inflater = null;
    private int layoutResource;
    private Context context = null;

    public ItemOrderAdapter(@NonNull Context context, int layoutResource, @NonNull List<ItemOrder> objects) {
        super(context, layoutResource, objects);
        this.context = context;
        this.layoutResource = layoutResource;
        this.inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rowView = inflater.inflate(this.layoutResource, parent, false);
        ItemOrder itemOrder = list.get(position);

        ViewHelper.inflateView(rowView, itemOrder, context);

        return rowView;
    }


}
