package com.delivarius.app.android.view.helper;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.delivarius.api.dto.ItemOrder;
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

    public static void setVisibility(@NonNull View viewBase, int resource, boolean visible){
        View view = viewBase.findViewById(resource);
        if(view != null){
            view.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    public static void setTextOnTextView(@NonNull View viewBase, int resource, String text){
        TextView textView = viewBase.findViewById(resource);
        if(textView != null){
            textView.setText(text);
        }
    }

    public static void setPictureOnImageView(@NonNull View viewBase, int resource, String picture){
        ImageView imageView = viewBase.findViewById(resource);
        if(imageView != null){
            ImageViewHelper.setImageView(imageView, picture);
        }
    }

    public static void setPictureOnImageView(@NonNull View viewBase, int resource, String picture, int resourceHeight, int resourceWidth, Context context){
        ImageView imageView = viewBase.findViewById(resource);
        if(imageView != null){
            ImageViewHelper.setImageView(imageView, picture);
            imageView.getLayoutParams().height = (int) context.getResources().getDimension(resourceHeight);
            imageView.getLayoutParams().width = (int) context.getResources().getDimension(resourceWidth);
        }
    }

    public static void setTag(@NonNull View viewBase, int resource, Object tag){
        View view = viewBase.findViewById(resource);
        if(view != null){
            view.setTag(tag);
        }
    }



    public static void inflateView(View rowView, Store store){
        //TODO refactor to ViewHelper
        TextView nameTextView = (TextView) rowView.findViewById(R.id.storeNameTextView);
        TextView addressTextView = (TextView) rowView.findViewById(R.id.storeAddressTextView);
        ImageView pictureImageView = (ImageView) rowView.findViewById(R.id.storePictureImageView);
        ImageView selectStoreImageView = (ImageView) rowView.findViewById(R.id.selectStoreImageView);

        nameTextView.setText(store.getName());
        addressTextView.setText(store.getAddress().getStreet()+", "+store.getAddress().getZipCode());
        ImageViewHelper.setImageView(pictureImageView,store.getPicture());
        selectStoreImageView.setTag(store);
    }

    public static void inflateView(View rowView, Product product) {
        ViewHelper.setTextOnTextView(rowView, R.id.productNameTextView, product.getName());
        ViewHelper.setTextOnTextView(rowView, R.id.productDescriptionTextView, product.getDescription());
        ViewHelper.setTextOnTextView(rowView, R.id.productPriceTextView, product.getPrice().toString());
        ViewHelper.setPictureOnImageView(rowView, R.id.productPictureImageView, product.getPicture());

        ViewHelper.setTag(rowView, R.id.addCartImageView, product);
    }

    public static void inflateView(View rowView, ItemOrder itemOrder, Context context) {
        ViewHelper.setTextOnTextView(rowView, R.id.productNameTextView, itemOrder.getProduct().getName());
        ViewHelper.setTextOnTextView(rowView, R.id.productPriceTextView, itemOrder.getProduct().getPrice().toString());
        ViewHelper.setTextOnTextView(rowView, R.id.amountProductTextView, OrderHelper.getAmount(itemOrder));
        ViewHelper.setPictureOnImageView(rowView, R.id.productPictureImageView, itemOrder.getProduct().getPicture(),
                R.dimen.medium_dim, R.dimen.medium_dim, context);

        ViewHelper.setTag(rowView, R.id.removeCartImageView, itemOrder);
        ViewHelper.setTag(rowView, R.id.incrementProductImageView, itemOrder);
        ViewHelper.setTag(rowView, R.id.decrementProductImageView, itemOrder);
    }


}
