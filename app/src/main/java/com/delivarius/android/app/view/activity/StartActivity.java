package com.delivarius.android.app.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.delivarius.android.R;
import com.delivarius.android.app.model.database.Database;

public class StartActivity extends DelivariusActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_activity);

    }

}
