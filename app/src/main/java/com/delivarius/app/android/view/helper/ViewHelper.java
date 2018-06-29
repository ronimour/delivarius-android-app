package com.delivarius.app.android.view.helper;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.delivarius.api.dto.Product;
import com.delivarius.app.R;
import com.delivarius.api.dto.Store;

public class ViewHelper {

    public static boolean isAnyEmpty(@NonNull EditText... editTexts){
        for (EditText et: editTexts){
            if(et.getText() != null && et.getText().toString().isEmpty())
                return true;
        }

        return false;
    }

    public static boolean isEmpty(@NonNull EditText editText){
        return editText.getText() != null && editText.getText().toString().isEmpty();
    }

    public static void inflateView(View rowView, Store store){

        TextView nameTextView = (TextView) rowView.findViewById(R.id.storeNameTextView);
        TextView addressTextView = (TextView) rowView.findViewById(R.id.storeAddressTextView);
        ImageView pictureImageView = (ImageView) rowView.findViewById(R.id.storePictureImageView);
        ImageView selectStoreImageView = (ImageView) rowView.findViewById(R.id.selectStoreImageView);

        nameTextView.setText(store.getName());
        addressTextView.setText(store.getAddress().getStreet()+", "+store.getAddress().getZipCode());
        ImageViewHelper.setImageViewStore(pictureImageView,store.getPicture());
        selectStoreImageView.setTag(store);
    }

    public static void inflateView(View rowView, Product product, int layoutResource){

        //shared views
        TextView nameTextView = (TextView) rowView.findViewById(R.id.productNameTextView);
        TextView priceTextView = (TextView) rowView.findViewById(R.id.productPriceTextView);
        ImageView pictureImageView = (ImageView) rowView.findViewById(R.id.productPictureImageView);

        nameTextView.setText(product.getName());
        priceTextView.setText(product.getPrice().toString());
        ImageViewHelper.setImageViewProduct(pictureImageView, product.getPicture());

        //specific views
        switch (layoutResource) {
            case R.layout.product_layout:
                TextView descriptionTextView = (TextView) rowView.findViewById(R.id.productDescriptionTextView);
                ImageView addCartImageView = (ImageView) rowView.findViewById(R.id.addCartImageView);

                descriptionTextView.setText(product.getDescription());
                addCartImageView.setTag(product);
                break;
            case R.layout.item_order_product_layout:
                ImageView removeCartImageView = (ImageView) rowView.findViewById(R.id.removeCartImageView);
                ImageView incrementProductImageView = (ImageView) rowView.findViewById(R.id.incrementProductImageView);
                ImageView decrementProductImageView = (ImageView) rowView.findViewById(R.id.decrementProductImageView);

                removeCartImageView.setTag(product);
                incrementProductImageView.setTag(product);
                decrementProductImageView.setTag(product);
                break;
            default:
                break;

        }
    }


}
