package delivarius.com.delivarius_app.android.app.view.helper;

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
}
