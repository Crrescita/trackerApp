package com.crrescita.employeetracker.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.crrescita.employeetracker.activity.forgot_password.SendOTPForgotPasswordActivity;
import com.crrescita.tel.R;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.securepreferences.SecurePreferences;

import utils.SingletonHelperGlobal;
import utils.Utility;

public class BackgroundLocationPermissionActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView hyperLink;
    private MaterialButton proceedButton;

    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;
    private ProgressDialog progressBar;

    public static final int ACCESS_BACKGROUND_LOCATION_REQUEST_CODE = 2345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_background_location_permission);

        proceedButton = findViewById(R.id.proceedButton);
        hyperLink = findViewById(R.id.hyperLink);

       // hyperLink.setOnClickListener(this);
        proceedButton.setOnClickListener(this);

        progressBar = new ProgressDialog(this);
        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();

        ImageView imageViewInstructions = findViewById(R.id.imageViewInstructions);
        Glide.with(this)
                .asGif()
                .load(R.raw.instruction) // Load the GIF from the raw folder
                .into(imageViewInstructions);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) ==
                PackageManager.PERMISSION_GRANTED  && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("resultKey", "1");
            setResult(RESULT_OK, resultIntent);
            finish();

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.proceedButton:
                vibrateDevice();
                requestPermissions();
                break;

            case R.id.forgotPassword:
                clicknewpassword();
                break;
            default:
                break;
        }
    }


    private void requestPermissions() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) !=
                PackageManager.PERMISSION_GRANTED  && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Request ACCESS_BACKGROUND_LOCATION if ACCESS_FINE_LOCATION is granted
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                        ACCESS_BACKGROUND_LOCATION_REQUEST_CODE);
        } else {
            // All permissions granted, proceed with login
            proceedLogin();
        }
    }

    private void vibrateDevice() {
        // Get the Vibrator service
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        if (vibrator != null) {
            // Check for the Android version
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                // For Android API 26 and above
                vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                // For Android API below 26
                vibrator.vibrate(100); // Vibrate for 100 milliseconds
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case ACCESS_BACKGROUND_LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Background location granted, proceed with login or other logic
                    proceedLogin();
                } else {
                    handlePermissionDenial(Manifest.permission.ACCESS_BACKGROUND_LOCATION);
                }
                break;

            default:
                Toast.makeText(this, "Permission request was cancelled or failed", Toast.LENGTH_SHORT).show();
                break;
        }
    }


    private void handlePermissionDenial(String permission) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showRationaleDialog(permission);
        } else {
            showSettingsDialog();
        }
    }

    private void showRationaleDialog(String permission) {
        new AlertDialog.Builder(this)
                .setTitle("Permission Required")
                .setMessage("Allow all time permission is needed for location," +
                        " select Allow all time from setting for all permission. Are you sure you want to deny this permission?")
                .setPositiveButton("Retry", (dialog, which) -> requestPermissions())
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void showSettingsDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Permission Required")
                .setMessage("You have denied the permission with 'Don't ask again'." +
                        " Please enable permissions from the app settings.")
                .setPositiveButton("Open Settings", (dialog, which) -> {
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    intent.setData(Uri.parse("package:" + getPackageName()));
                    startActivity(intent);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .show();
    }


    private void proceedLogin() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("resultKey", "1");
        setResult(RESULT_OK, resultIntent);
        finish();
    }


    private void dialogForSettings(String title, String msg) {
        new AlertDialog.Builder(this).setTitle(title).setMessage(msg)
                .setCancelable(false)
                .setNegativeButton("NOT NOW", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("SETTINGS", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        goToSettings();
                        dialog.dismiss();
                    }
                }).show();
    }

    private void goToSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.parse("package:" + getPackageName());
        intent.setData(uri);
        startActivity(intent);
    }

    public void clicknewpassword() {
        Intent intent = new Intent(BackgroundLocationPermissionActivity.this, SendOTPForgotPasswordActivity.class);
        startActivity(intent);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utility.getInstance().deleteCache(BackgroundLocationPermissionActivity.this);
    }
}
