package delivarius.com.delivarius_app.android.app.view.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.delivarius.delivarius_api.dto.Address;
import com.delivarius.delivarius_api.dto.Phone;
import com.delivarius.delivarius_api.dto.User;
import com.delivarius.delivarius_api.service.ServiceLocator;
import com.delivarius.delivarius_api.service.UserService;
import com.delivarius.delivarius_api.service.exception.ServiceException;

import java.io.IOException;

import delivarius.com.delivarius_app.R;
import delivarius.com.delivarius_app.android.app.view.helper.ViewHelper;


public class RegistrationActivity extends DelivariusActivity {

    private User user = null;

    private String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if(savedInstanceState != null && savedInstanceState.get("user") != null ){
            this.user = (User) savedInstanceState.get("user");
            this.password = savedInstanceState.getString("password");
        } else {
            this.user = new User();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity_personal_info);

    }

    public void cancel(View view){

    }

    public void register(View view){

        if(isValid(this.user)) {
            setUserFromViewLoginInfo();
            CreateUser createUser = new CreateUser();
            createUser.execute(this.user, this.password);
        }
    }

    private boolean isValid(User user) {
        return true;
    }


    private class CreateUser extends AsyncTask<Object, Void, User> {

        @Override
        protected User doInBackground(Object... params) {
            User user = (User) params[0];
            String password = (String) params[1];
            User userCreated = null;
            try {
                UserService userService = (UserService) ServiceLocator.getInstance().getService(UserService.class);
                userCreated = userService.createClientUser(user, password );
            } catch (ServiceException e) {
                e.printStackTrace();
            }

            return userCreated;
        }

        @Override
        protected void onPostExecute(User user) {
            if(user != null && user.getId() != null && user.getId() > 0){
                Intent intent = new Intent("android.intent.action.MAIN");
                startActivity(intent);
                showToastShort("User created.");
            } else{
                showToastShort("Fail to create the user.");
            }
            super.onPostExecute(user);
        }
    }

    public void enterLoginInfo(View view){

        if(setUserFromViewAddressInfo()) {
            setContentView(R.layout.registration_activity_login_info);
            setViewFromUserLoginInfo();
        }

    }

    public void enterAddressInfo(View view){
        int id = view.getId();

        if(id == R.id.nextPersonalButton) {
            if (setUserFromViewPersonalInfo()) {
                setContentView(R.layout.registration_activity_address_info);
                setViewFromUserAddressInfo();
            }
        } else if(id == R.id.previousLoginButton) {
            setUserFromViewLoginInfo();
            setContentView(R.layout.registration_activity_address_info);
            setViewFromUserAddressInfo();
        }


    }

    public void enterPersonalInfo(View view){
        setUserFromViewAddressInfo();
        setContentView(R.layout.registration_activity_personal_info);
        setViewFromUserPersonalInfo();
    }

    private boolean setUserFromViewLoginInfo(){

        EditText loginEditText = (EditText) findViewById(R.id.loginEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        EditText passwordRepeatEditText = (EditText) findViewById(R.id.passwordRepeatEditText);

        if(ViewHelper.isAnyEmpty(loginEditText,passwordEditText,passwordRepeatEditText)){
            showToastShort("Fields can not be empty");
            return false;
        }

        user.setLogin(loginEditText.getText().toString());

        if(!passwordEditText.getText().toString().equals(passwordRepeatEditText.getText().toString())){
            showToastShort("passwords does not match");
            return false;
        } else {
            if(passwordEditText.getText().toString().length() < 6){
                showToastShort("password should have at least 6 character");
                return false;
            }
        }

        this.password = passwordEditText.getText().toString();

        return true;
    }

    private boolean setViewFromUserLoginInfo(){

        EditText loginEditText = (EditText) findViewById(R.id.loginEditText);
        EditText passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        EditText passwordRepeatEditText = (EditText) findViewById(R.id.passwordRepeatEditText);

        loginEditText.setText(user.getLogin());
        passwordEditText.setText(this.password);
        passwordRepeatEditText.setText(this.password);

        return true;
    }

    private void  setViewFromUserPersonalInfo() {

        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        EditText birthdateEditText = (EditText) findViewById(R.id.birthdateEditText);
        EditText phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        CheckBox isWhatsapp = (CheckBox) findViewById(R.id.isWhatsappCheckBox);
        CheckBox isCelphone = (CheckBox) findViewById(R.id.isCelphoneCheckBox);

        nameEditText.setText(user.getName());
        emailEditText.setText(user.getEmail());
        birthdateEditText.setText(user.getBirthDate());

        if(user.getPhone() != null) {
            phoneEditText.setText(user.getPhone().getNumber());
            isWhatsapp.setChecked(user.getPhone().getWhatsapp());
            isCelphone.setChecked(user.getPhone().getCelphone());
        }
    }

    private boolean setUserFromViewPersonalInfo() {

        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        EditText birthdateEditText = (EditText) findViewById(R.id.birthdateEditText);
        EditText phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        CheckBox isWhatsapp = (CheckBox) findViewById(R.id.isWhatsappCheckBox);
        CheckBox isCelphone = (CheckBox) findViewById(R.id.isCelphoneCheckBox);

        if(ViewHelper.isAnyEmpty(nameEditText,emailEditText,birthdateEditText,phoneEditText)){
            showToastShort("Fields can not be empty");
            return false;
        }

        user.setName(nameEditText.getText().toString());
        user.setEmail(emailEditText.getText().toString());
        user.setBirthDate(birthdateEditText.getText().toString());

        if(user.getPhone() == null)
            user.setPhone(new Phone());

        user.getPhone().setNumber(phoneEditText.getText().toString());
        user.getPhone().setWhatsapp(isWhatsapp.isChecked());
        user.getPhone().setCelphone(isCelphone.isChecked());

        return true;
    }

    private void setViewFromUserAddressInfo() {

        EditText streetEditText = (EditText) findViewById(R.id.streetEditText);
        EditText referenceEditText = (EditText) findViewById(R.id.referenceEditText);
        EditText zipcodeEditText = (EditText) findViewById(R.id.zipcodeEditText);
        EditText cityEditText = (EditText) findViewById(R.id.cityEditText);

        if(user.getAddress() != null) {
            streetEditText.setText(user.getAddress().getStreet());
            referenceEditText.setText(user.getAddress().getReference());
            zipcodeEditText.setText(user.getAddress().getZipCode());
            cityEditText.setText(user.getAddress().getCity() + ", " + user.getAddress().getState());
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


        if(user.getAddress() == null)
            user.setAddress(new Address());

        user.getAddress().setStreet(streetEditText.getText().toString());
        user.getAddress().setReference(referenceEditText.getText().toString());
        user.getAddress().setZipCode(zipcodeEditText.getText().toString());

        String[] cityState = cityEditText.getText().toString().split(",");
        if(cityState.length == 2) {
            user.getAddress().setCity(cityState[0]);
            user.getAddress().setState(cityState[1]);
        } else {
            showToastShort("Cidade deve está no padrão: Cidade, Estado.");
            return false;
        }

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable("user",this.user);
        outState.putString("password",this.password);
        super.onSaveInstanceState(outState);
    }
}
