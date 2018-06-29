package com.delivarius.app.android.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.delivarius.api.dto.User;

import com.delivarius.app.R;

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
            currentOrder.setUser(currentUser);
        }

    }

    public void editUser(View view){
        Intent intent = new Intent("com.delivarius.app.EDIT");
        startActivityForResult(intent, EDIT_REQUEST_CODE);
    }


    public void searchProducts(View view){
        Intent intent = new Intent("com.delivarius.app.SHOPPING");
        startActivityForResult(intent, SHOPPING_REQUEST_CODE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case EDIT_REQUEST_CODE:
                if(resultCode == RESULT_USER_DELETED){
                    setResult(RESULT_SUCCESS, data);
                    finish();
                }

        }

    }
}
