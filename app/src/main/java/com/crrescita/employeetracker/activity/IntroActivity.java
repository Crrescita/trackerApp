package com.crrescita.employeetracker.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.crrescita.tel.R;
import com.google.android.material.button.MaterialButton;
import com.securepreferences.SecurePreferences;

import utils.SingletonHelperGlobal;
import utils.Utility;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton getStartbutton;

    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


        getStartbutton = findViewById(R.id.getStarteButton);
        getStartbutton.setOnClickListener(this);

        progressBar = new ProgressDialog(this);
        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();
    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.getStarteButton:
             Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
             startActivity(intent);
             finish();
             break;
         default:
             break;
     }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utility.getInstance().deleteCache(IntroActivity.this);
    }
}
