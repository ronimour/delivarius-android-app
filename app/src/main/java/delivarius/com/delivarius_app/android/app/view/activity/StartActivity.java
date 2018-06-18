package delivarius.com.delivarius_app.android.app.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import delivarius.com.delivarius_app.R;

public class StartActivity extends DelivariusActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);
    }

    public void register(View view ){
        Intent intent = new Intent("com.delivarius.app.REGISTRATION");
        startActivity(intent);
    }

}
