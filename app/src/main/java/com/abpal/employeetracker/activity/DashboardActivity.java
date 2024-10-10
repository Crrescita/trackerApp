package com.abpal.employeetracker.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.abpal.tel.R;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.securepreferences.SecurePreferences;

import de.hdodenhof.circleimageview.CircleImageView;
import utils.AppConstant;
import utils.SingletonHelperGlobal;
import utils.UtilGps;
import utils.service.LocationService;

import com.abpal.employeetracker.activity.fragments.*;

public class DashboardActivity extends AppCompatActivity {
     private TextView textViewDashboard;
     private TextView userName;
    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;

    private CircleImageView user_image;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        userName = findViewById(R.id.user_name);
        user_image = findViewById(R.id.user_image);

        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();



        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        selectedFragment = new HomeFragment();
                        break;
                    case R.id.navigation_myrecord:
                        selectedFragment = new MyRecordFragment();
                        break;
                    case R.id.navigation_profile:
                        selectedFragment = new ProfileFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            }
        });

        // Set default fragment
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        userName.setText("Welcome "+prefsMain.getString(AppConstant.EMPLOYEE_NAME,""));
        Glide.with(this)
                .load(prefsMain.getString(AppConstant.USER_IMAGE,""))
                .into(user_image);
    }
}