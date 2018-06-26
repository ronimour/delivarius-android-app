package com.delivarius.app.android.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.delivarius.delivarius_api.dto.User;
import com.delivarius.delivarius_api.service.exception.ServiceException;

import java.net.ConnectException;

import com.delivarius.app.R;
import com.delivarius.app.android.view.helper.ViewHelper;

public class StartActivity extends DelivariusActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

        try {
            SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
            if (preferences.contains(REMEMBER_LOGIN) && preferences.getBoolean(REMEMBER_LOGIN, false)) {
                Long userId = preferences.getLong(USER_ID, 0);
                if (userId > 0) {
                    getAutoLoginAsyncTask().execute(userId.toString());
                }
            }
        } catch (ConnectException e) {
            showToastLong(getString(R.string.connetion_server_failed));
        }
    }

    public void register(View view) {
        Intent registrationIntent = new Intent("com.delivarius.app.REGISTRATION");
        startActivityForResult(registrationIntent, REGISTER_REQUEST_CODE);
    }


    public void login(View view) {
        EditText loginText = (EditText) findViewById(R.id.loginEditText);
        EditText passwordText = (EditText) findViewById(R.id.passwordEditText);
        if (ViewHelper.isAnyEmpty(loginText, passwordText)) {
            showToastLong(getString(R.string.login_password_empty_error));
        } else {
            try {
                getLoginAsyncTask().execute(loginText.getText().toString(), passwordText.getText().toString());
            } catch (ConnectException e) {
                showToastLong(getString(R.string.connetion_server_failed));
            }
        }
    }

    private class AutoLoginAsyncTask extends LoginAsyncTask {

        @Override
        protected User doInBackground(String... strings) {
            User user = null;
            Long userId = Long.parseLong(strings[0]);

            try {
                user = getUserService().getUser(userId);
            } catch (ServiceException e) {
                e.printStackTrace();
            }

            return user;
        }
    }

    private LoginAsyncTask getLoginAsyncTask() throws ConnectException {
        verifyInternetConnection();
        return new LoginAsyncTask();
    }

    private AutoLoginAsyncTask getAutoLoginAsyncTask() throws ConnectException {
        verifyInternetConnection();
        return new AutoLoginAsyncTask();
    }

    private class LoginAsyncTask extends AsyncTask<String, Void, User> {


        ProgressDialog progressDialog = new ProgressDialog(StartActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage(getString(R.string.login_message_progress));
            progressDialog.show();
        }

        @Override
        protected User doInBackground(String... strings) {
            String login = strings[0];
            String password = strings[1];
            User user = null;
            try {
                user = getUserService().login(login, password);
            } catch (ServiceException e) {
                e.printStackTrace();
            }

            return user;
        }

        @Override
        protected void onPostExecute(User user) {
            progressDialog.dismiss();
            if (user != null) {
                checkSaveLogin(user);
                Intent homeIntent = new Intent("com.delivarius.app.HOME");
                homeIntent.putExtra(USER, user);
                startActivityForResult(homeIntent, HOME_REQUEST_CODE);
            } else {
                showToastLong(getString(R.string.login_failed_message));
            }

            super.onPostExecute(user);
        }
    }

    private void checkSaveLogin(User user) {
        CheckBox rememberLoginCheckBox = (CheckBox) findViewById(R.id.rembemberLoginCheckBox);

        SharedPreferences.Editor editor = this.getPreferences(Context.MODE_PRIVATE).edit();
        editor.putLong(USER_ID, user.getId());
        editor.putBoolean(REMEMBER_LOGIN, rememberLoginCheckBox.isChecked());
        editor.apply();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_LOGOUT) {
            SharedPreferences.Editor editor = this.getPreferences(Context.MODE_PRIVATE).edit();
            editor.putBoolean(REMEMBER_LOGIN, false);
            editor.apply();
        }
        String message = data != null ? data.getStringExtra(RESULT_MESSAGE) : "";
        switch (requestCode) {

            case REGISTER_REQUEST_CODE:
                if (resultCode == RESULT_CANCELED) {
                    showToastShort(message);
                } else if (resultCode == RESULT_FAIL || resultCode == RESULT_SUCCESS) {
                    showToastLong(message);
                }
                break;
            case HOME_REQUEST_CODE:
                if (resultCode == RESULT_SUCCESS) {
                    showToastShort(message);
                }
                break;

            default:
                showToastShort("?");
        }

    }
}
