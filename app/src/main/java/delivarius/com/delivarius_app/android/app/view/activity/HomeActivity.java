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

    private User user = null;

    private final LogoutOnClickListener logoutOnClickListener = new LogoutOnClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);

        Intent intent = getIntent();
        this.user = (User) intent.getSerializableExtra(USER);
        if(user != null){
            TextView userMenuHome = (TextView) findViewById(R.id.userHomeMenu);
            userMenuHome.setText(user.getLogin());
        }

    }

    public void logout(View view){
        showDialogYesOrNo(logoutOnClickListener, cancelDialog, getString(R.string.logout_message_dialog));
    }

    private class LogoutOnClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            setResult(RESULT_LOGOUT);
            user = null;
            finish();
        }
    }
}
