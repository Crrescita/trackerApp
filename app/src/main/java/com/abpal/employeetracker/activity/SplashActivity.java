package com.abpal.employeetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.abpal.tel.BuildConfig;
import com.abpal.tel.R;
import com.securepreferences.SecurePreferences;

import utils.AppConstant;
import utils.SingletonHelperGlobal;

public class SplashActivity extends AppCompatActivity {

    private final int PROGRESS_DURATION = 3000; // 5 seconds
    private final int UPDATE_INTERVAL = 10; // Update every 50 milliseconds

    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_splash);

        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();


        // Start countdown
        startCountdown();
    }

    private void startCountdown() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (prefsMain.getBoolean(AppConstant.IS_USER_LOGGED_IN, false)) {
                    Intent intent = new Intent(SplashActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish(); // Optionally finish the SplashActivity
                } else {
                    Intent intent = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(intent);
                    finish(); // Optionally finish the SplashActivity
                }
            }
        }, PROGRESS_DURATION); // Delay for the specified duration (e.g., 2000 milliseconds)
    }


}
