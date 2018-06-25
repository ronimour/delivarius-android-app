package com.delivarius.app.android.view.helper;

import android.support.annotation.NonNull;
import android.widget.EditText;

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


}
