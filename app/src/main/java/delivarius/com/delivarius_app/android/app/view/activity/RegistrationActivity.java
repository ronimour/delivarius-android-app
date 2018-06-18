package delivarius.com.delivarius_app.android.app.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.delivarius.delivarius_api.dto.Phone;
import com.delivarius.delivarius_api.dto.User;

import delivarius.com.delivarius_app.R;


public class RegistrationActivity extends DelivariusActivity {

    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity_personal_info);
        user = new User();
    }

    public void enterAddressInfo(View view){

        if(setUserFromViewPersonalInfo())
            setContentView(R.layout.registration_activity_address_info);

    }

    public void enterPersonalInfo(View view){

        setContentView(R.layout.registration_activity_personal_info);

        setViewFromUserPersonalInfo();
    }

    private boolean setViewFromUserPersonalInfo() {

        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        EditText birthdateEditText = (EditText) findViewById(R.id.birthdateEditText);
        EditText phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        CheckBox isWhatsapp = (CheckBox) findViewById(R.id.isWhatsappCheckBox);
        CheckBox isCelphone = (CheckBox) findViewById(R.id.isCelphoneCheckBox);

        nameEditText.setText(user.getName());
        emailEditText.setText(user.getEmail());
        birthdateEditText.setText(user.getBirthDate());


        phoneEditText.setText(user.getPhone().getNumber());
        isWhatsapp.setChecked(user.getPhone().getWhatsapp());
        isCelphone.setChecked(user.getPhone().getCelphone());

        return true;
    }

    private boolean setUserFromViewPersonalInfo() {

        EditText nameEditText = (EditText) findViewById(R.id.nameEditText);
        EditText emailEditText = (EditText) findViewById(R.id.emailEditText);
        EditText birthdateEditText = (EditText) findViewById(R.id.birthdateEditText);
        EditText phoneEditText = (EditText) findViewById(R.id.phoneEditText);
        CheckBox isWhatsapp = (CheckBox) findViewById(R.id.isWhatsappCheckBox);
        CheckBox isCelphone = (CheckBox) findViewById(R.id.isCelphoneCheckBox);

        if(nameEditText.getText().toString().isEmpty()){
            showToastShort("Name can not be empty");
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



}
