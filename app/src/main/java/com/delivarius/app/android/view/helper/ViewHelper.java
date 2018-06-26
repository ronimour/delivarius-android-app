package com.delivarius.app.android.view.helper;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.delivarius.app.R;
import com.delivarius.delivarius_api.dto.Store;

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
        ImageView storePictureTextView = (ImageView) rowView.findViewById(R.id.storePictureImageView);

        nameTextView.setText(store.getName());
        addressTextView.setText(store.getAddress().getStreet()+", "+store.getAddress().getZipCode());
        ImageViewHelper.setImageViewStore(storePictureTextView,store.getPicture());
    }


}
