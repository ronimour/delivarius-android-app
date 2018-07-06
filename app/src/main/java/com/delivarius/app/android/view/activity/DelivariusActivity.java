package com.delivarius.app.android.view.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.delivarius.api.dto.Order;
import com.delivarius.api.dto.Store;
import com.delivarius.api.dto.User;
import com.delivarius.api.service.OrderService;
import com.delivarius.api.service.ServiceLocator;
import com.delivarius.api.service.StoreService;
import com.delivarius.api.service.UserService;
import com.delivarius.api.service.exception.ServiceException;

import java.net.ConnectException;

import com.delivarius.app.R;
import com.delivarius.app.android.view.helper.ViewHelper;
import com.delivarius.app.android.view.util.ConnectionUtil;

public abstract class DelivariusActivity extends Activity {

    public static final int REGISTER_REQUEST_CODE = 1;

    public static final int HOME_REQUEST_CODE = 2;

    public static final int EDIT_REQUEST_CODE = 3;

    public static final int SHOPPING_REQUEST_CODE = 4;

    public static final int RESULT_SUCCESS = 1000;

    public static final int RESULT_FAIL = -1001;

    public static final int RESULT_LOGOUT = 1002;

    public static final int RESULT_USER_DELETED = 1003;

    public static final String RESULT_MESSAGE = "RESULT_MESSAGE";

    public static final String USER = "USER";

    public static final String USER_ID = "USER_ID";

    public static final String REMEMBER_LOGIN = "REMEMBER_LOGIN";

    protected final CancelDialogOnClickListener cancelDialog = new CancelDialogOnClickListener();

    private final LogoutOnClickListener logoutOnClickListener = new LogoutOnClickListener();

    protected UserService userService = null;

    protected StoreService storeService = null;

    protected OrderService orderService = null;

    protected static User currentUser = null;

    protected static Order currentOrder = null;

    protected static Store currentStore = null;

    private ProgressDialog progressDialog = null;


    protected void verifyInternetConnection() throws ConnectException{
        if(ConnectionUtil.isDelivariusServerAvailable(this))
            throw new ConnectException();
        
    }

    public OrderService getOrderService() throws ServiceException {
        if(orderService == null){
            orderService = (OrderService) ServiceLocator.getInstance().getService(OrderService.class);
            orderService.setUrlBase(ConnectionUtil.DELIVARIUS_ADDRESS);
        }
        return orderService;
    }

    public UserService getUserService() throws ServiceException {
        if(userService == null){
            userService = (UserService) ServiceLocator.getInstance().getService(UserService.class);
            userService.setUrlBase(ConnectionUtil.DELIVARIUS_ADDRESS);
        }
        return userService;
    }

    public StoreService getStoreService() throws ServiceException {
        if(storeService == null){
            storeService = (StoreService) ServiceLocator.getInstance().getService(StoreService.class);
            storeService.setUrlBase(ConnectionUtil.DELIVARIUS_ADDRESS);
        }
        return storeService;
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

    protected void showProcessDialog(Activity activity, String waitMessage){
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(waitMessage);
        progressDialog.show();
    }

    protected void dismissProcessDialog(){
        if(progressDialog != null)
            progressDialog.dismiss();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_LOGOUT ){
            setResult(RESULT_LOGOUT);
            finish();
        }

    }



    protected abstract class ConnectionAsyncTask extends AsyncTask<Object, Void, Object> {


        ProgressDialog progressDialog = new ProgressDialog(DelivariusActivity.this);

        private String waitMessage = getString(R.string.wait_message_default);

        public String getWaitMessage() {
            return waitMessage;
        }

        public void setWaitMessage(String waitMessage) {
            this.waitMessage = waitMessage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage(getWaitMessage());
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Object object) {
            super.onPostExecute(object);
            progressDialog.dismiss();
        }
    }


    protected void loadMenuBar(boolean showHomeMenu, boolean showCartMenu, int layoutContainerResource){
        LinearLayout layout = findViewById(layoutContainerResource);
        View view = getLayoutInflater().inflate(R.layout.menu_bar_layout,null,false);

        ViewHelper.setVisibility(view, R.id.homeMenu, showHomeMenu);
        ViewHelper.setVisibility(view, R.id.cartMenu, showCartMenu);

        if(currentUser != null){
            ViewHelper.setTextOnTextView(view, R.id.userMenu, currentUser.getLogin());
        }

        layout.addView(view);
    }

    protected void loadLogo(int layoutContainerResource){
        LinearLayout layout = findViewById(layoutContainerResource);
        View view = getLayoutInflater().inflate(R.layout.logo_layout,null,false);

        layout.addView(view);
    }





}
