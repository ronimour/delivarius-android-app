package delivarius.com.delivarius_app.android.app.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.delivarius.delivarius_api.dto.User;

import org.w3c.dom.Text;

import delivarius.com.delivarius_app.R;

public class HomeActivity extends DelivariusActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra(USER);
        if(currentUser != null){
            TextView userMenuHome = (TextView) findViewById(R.id.userHomeMenu);
            userMenuHome.setText(currentUser.getLogin());
        }

    }


}
