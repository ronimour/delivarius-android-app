package delivarius.com.delivarius_app.android.app.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.delivarius.delivarius_api.dto.User;
import com.delivarius.delivarius_api.service.ServiceLocator;
import com.delivarius.delivarius_api.service.UserService;
import com.delivarius.delivarius_api.service.exception.ServiceException;

import delivarius.com.delivarius_app.R;

public class DelivariusActivity extends Activity {

    public static final int REGISTER_REQUEST_CODE = 1;

    public static final int HOME_REQUEST_CODE = 2;

    public static final int RESULT_SUCCESS = 1000;

    public static final int RESULT_FAIL = -1001;

    public static final int RESULT_LOGOUT = 1002;

    public static final String RESULT_MESSAGE = "RESULT_MESSAGE";

    public static final String USER = "USER";

    public static final String USER_ID = "USER_ID";

    public static final String REMEMBER_LOGIN = "REMEMBER_LOGIN";

    protected final CancelDialogOnClickListener cancelDialog = new CancelDialogOnClickListener();

    private final LogoutOnClickListener logoutOnClickListener = new LogoutOnClickListener();

    protected UserService userService = null;

    protected static User currentUser = null;


    public UserService getUserService() throws ServiceException {
        if(userService == null){
            userService = (UserService) ServiceLocator.getInstance().getService(UserService.class);
            userService.setUrlBase("http://10.0.2.2:8081");
        }
        return userService;
    }

    protected void showToastShort(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    protected void showToastLong(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }

    protected void showDialogYesOrNo(DialogInterface.OnClickListener positive, DialogInterface.OnClickListener negative, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, positive)
                .setNegativeButton(R.string.no, negative);
        AlertDialog alert = builder.create();
        alert.show();
    }


    protected class CancelDialogOnClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }
    public void logout(View view){
        showDialogYesOrNo(logoutOnClickListener, cancelDialog, getString(R.string.logout_message_dialog));
    }

    private class LogoutOnClickListener implements DialogInterface.OnClickListener{
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            setResult(RESULT_LOGOUT);
            finish();
        }
    }


}
