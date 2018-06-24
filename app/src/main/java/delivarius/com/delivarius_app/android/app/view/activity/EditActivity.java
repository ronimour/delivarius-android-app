package delivarius.com.delivarius_app.android.app.view.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.delivarius.delivarius_api.dto.Address;
import com.delivarius.delivarius_api.dto.User;
import com.delivarius.delivarius_api.service.UserService;
import com.delivarius.delivarius_api.service.exception.ServiceException;

import java.net.ConnectException;

import delivarius.com.delivarius_app.R;
import delivarius.com.delivarius_app.android.app.view.helper.ViewHelper;

public class EditActivity extends DelivariusActivity {

    private final DeleteUserOnClickListener positiveDeleteUser = new DeleteUserOnClickListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_menu_activity);
    }


    public void editAddress(View view){
        if(currentUser != null){
            setContentView(R.layout.edit_address_activity);
            setViewFromUserAddressInfo();
        }
    }

    public void updateAddress(View view){
        if(setUserFromViewAddressInfo()){
            try {
                getUserAsyncTask().execute(currentUser);
            } catch (ConnectException e) {
                showToastLong(getString(R.string.connetion_server_failed));
            }
        }

    }

    public void deleteUser(View view){
        showDialogYesOrNo(positiveDeleteUser, cancelDialog, getString(R.string.delete_user_message_dialog));
    }

    private class DeleteUserOnClickListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            try {
                getDeleteUserAsyncTask().execute(currentUser);
            } catch (ConnectException e) {
                showToastLong(getString(R.string.connetion_server_failed));
            }
        }
    }

    private DeleteUserAsyncTask getDeleteUserAsyncTask() throws ConnectException {
        verifyInternetConnection();
        return new DeleteUserAsyncTask();
    }

    private class DeleteUserAsyncTask extends AsyncTask<User, Void, Boolean>{

        ProgressDialog progressDialog = new ProgressDialog(EditActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage(getString(R.string.deleting_user_message_progress));
            progressDialog.show();
        }

        @Override
        protected Boolean doInBackground(User... params) {
            User userToDelete = params[0];
            Boolean deleted = false;
            try {
                deleted = getUserService().deleteUser(userToDelete.getId());
            } catch (ServiceException e){
                e.printStackTrace();
            }

            return deleted;
        }

        @Override
        protected void onPostExecute(Boolean deleted) {
            progressDialog.dismiss();
            if(deleted){
                finishActivity(RESULT_USER_DELETED, getString(R.string.success_delete_user));
            } else{
                showToastLong(getString(R.string.fail_delete_user));
            }
        }

    }

    public void returnToHome(View view) {
        finishActivity(RESULT_OK, null);
    }

    public void finishActivity(int resultCode, String message){
        getIntent().putExtra(RESULT_MESSAGE, message);
        setResult(resultCode,getIntent());
        finish();
    }

    private EditUserAsyncTask getUserAsyncTask() throws ConnectException {
        verifyInternetConnection();
        return new EditUserAsyncTask();
    }

    private class EditUserAsyncTask extends AsyncTask<User, Void, User>{

        ProgressDialog progressDialog = new ProgressDialog(EditActivity.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage(getString(R.string.registering_user_message_progress));
            progressDialog.show();
        }

        @Override
        protected User doInBackground(User... params) {
            User userToUpdate = params[0];
            User userUpdated = null;
            try {
                userUpdated = getUserService().updateUser(userToUpdate);
            } catch (ServiceException e){
                e.printStackTrace();
            }

            return userUpdated;
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
            progressDialog.dismiss();
            if(user != null && user.getId() != null && user.getId() > 0){
                returnToEditMenu(getString(R.string.success_update_user), RESULT_SUCCESS);
            } else{
                returnToEditMenu(getString(R.string.fail_update_user), RESULT_FAIL);
            }

        }
    }

    private void returnToEditMenu(String message, int resultCode){
        setContentView(R.layout.edit_menu_activity);
        if(resultCode == RESULT_FAIL) {
            showToastLong(message);
        } else if(resultCode == RESULT_SUCCESS){
            showToastShort(message);
        }
    }

    private boolean setUserFromViewAddressInfo() {

        EditText streetEditText = (EditText) findViewById(R.id.streetEditText);
        EditText referenceEditText = (EditText) findViewById(R.id.referenceEditText);
        EditText zipcodeEditText = (EditText) findViewById(R.id.zipcodeEditText);
        EditText cityEditText = (EditText) findViewById(R.id.cityEditText);

        if(ViewHelper.isAnyEmpty(streetEditText,referenceEditText,zipcodeEditText,cityEditText)){
            showToastShort("Fields can not be empty");
            return false;
        }


        if(currentUser.getAddress() == null)
            currentUser.setAddress(new Address());

        currentUser.getAddress().setStreet(streetEditText.getText().toString());
        currentUser.getAddress().setReference(referenceEditText.getText().toString());
        currentUser.getAddress().setZipCode(zipcodeEditText.getText().toString());

        String[] cityState = cityEditText.getText().toString().split(",");
        if(cityState.length == 2) {
            currentUser.getAddress().setCity(cityState[0]);
            currentUser.getAddress().setState(cityState[1]);
        } else {
            showToastShort("Cidade deve está no padrão: Cidade, Estado.");
            return false;
        }

        return true;
    }

    private void setViewFromUserAddressInfo() {

        EditText streetEditText = (EditText) findViewById(R.id.streetEditText);
        EditText referenceEditText = (EditText) findViewById(R.id.referenceEditText);
        EditText zipcodeEditText = (EditText) findViewById(R.id.zipcodeEditText);
        EditText cityEditText = (EditText) findViewById(R.id.cityEditText);

        if(currentUser.getAddress() != null) {
            streetEditText.setText(currentUser.getAddress().getStreet());
            referenceEditText.setText(currentUser.getAddress().getReference());
            zipcodeEditText.setText(currentUser.getAddress().getZipCode());
            cityEditText.setText(currentUser.getAddress().getCity() + ", " + currentUser.getAddress().getState());
        }

    }

}
