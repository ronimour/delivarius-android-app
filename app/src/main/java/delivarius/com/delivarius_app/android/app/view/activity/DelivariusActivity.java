package delivarius.com.delivarius_app.android.app.view.activity;

import android.app.Activity;
import android.widget.Toast;

public class DelivariusActivity extends Activity {

    protected void showToastShort(String message){
        Toast toast = Toast.makeText(this, message,Toast.LENGTH_SHORT);
        toast.show();
    }
}
