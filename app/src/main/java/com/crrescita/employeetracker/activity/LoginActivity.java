package com.crrescita.employeetracker.activity;

import android.Manifest;
import android.app.Activity;
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
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.crrescita.tel.BuildConfig;
import com.crrescita.tel.R;
import com.crrescita.employeetracker.activity.forgot_password.SendOTPForgotPasswordActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.securepreferences.SecurePreferences;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;

import utils.SingletonHelperGlobal;
import database.MySQLiteHelper;
import modelResponse.ModelError;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofitAPI.WebRequest;
import utils.AppConstant;
import utils.Utility;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;

    private TextView forgotPassword;
    private MaterialButton loginbutton;

    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;
    private ProgressDialog progressBar;
    public static final int NOTIFICATION_PERMISSION_REQUEST_CODE = 100;
    public static final int ACCESS_FINE_LOCATION_REQUEST_CODE = 120;
    public static final int ACCESS_BACKGROUND_LOCATION_REQUEST_CODE = 2345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextInputLayout emailInputLayout = findViewById(R.id.emailInputLayout);
        TextInputLayout passwordInputLayout = findViewById(R.id.passwordInputLayout);

        emailEditText = emailInputLayout.findViewById(R.id.emailEditText);
        passwordEditText = passwordInputLayout.findViewById(R.id.passwordEditText);
        loginbutton = findViewById(R.id.loginButton);
        forgotPassword = findViewById(R.id.forgotPassword);

        forgotPassword.setOnClickListener(this);
        loginbutton.setOnClickListener(this);

        progressBar = new ProgressDialog(this);
        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            // Request POST_NOTIFICATIONS permission if on Android 13+
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) !=
                    PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        NOTIFICATION_PERMISSION_REQUEST_CODE);
                return;
            }
        }
        // Request ACCESS_FINE_LOCATION permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    ACCESS_FINE_LOCATION_REQUEST_CODE);
        } else if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) !=
                PackageManager.PERMISSION_GRANTED  && Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // Request ACCESS_BACKGROUND_LOCATION if ACCESS_FINE_LOCATION is granted
            Intent intent = new Intent(this, BackgroundLocationPermissionActivity.class);
            activityResultLauncher.launch(intent);
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
//                        ACCESS_BACKGROUND_LOCATION_REQUEST_CODE);
        } else {
            // All permissions granted, proceed with login
            proceedLogin();
        }
    }


    private final ActivityResultLauncher<Intent> activityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent data = result.getData();
                            if (data != null) {
                                String incomingdata = data.getStringExtra("resultKey");
                               // Toast.makeText(this, "Result: " + incomingdata, Toast.LENGTH_SHORT).show();
                                if(incomingdata.equals("1")){
                                    proceedLogin();
                                }

                            }
                        }
                    });

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
            case ACCESS_FINE_LOCATION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Fine location granted, request background location if needed
                    requestPermissions();
                } else {
                    handlePermissionDenial(Manifest.permission.ACCESS_FINE_LOCATION);
                }
                break;



            case NOTIFICATION_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Notification permission granted, proceed with login or other logic
                    requestPermissions();
                } else {
                    handlePermissionDenial(Manifest.permission.POST_NOTIFICATIONS);
                }
                break;

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
        String usernameStr = emailEditText.getText().toString();
        String passwordRaw = passwordEditText.getText().toString();
        // String passwordStr = Utility.getInstance().md5(passwordRaw);
        if (usernameStr.length() == 0 && passwordRaw.length() == 0) {
            Utility.getInstance().showToastCenter(" Enter a username & password to Login", LoginActivity.this);
        } else if (usernameStr.length() < 4) {
            if (usernameStr.length() == 0) {
                Utility.getInstance().showToastCenter("Enter Username", LoginActivity.this);
            } else {
                Utility.getInstance().showToastCenter("Incorrect Username", LoginActivity.this);
            }
        } else if (passwordEditText.getText().toString().length() < 4) {
            if (passwordRaw.length() == 0) {
                Utility.getInstance().showToastCenter("Enter Password", LoginActivity.this);
            } else {
                Utility.getInstance().showToastCenter("Incorrect Password", LoginActivity.this);
            }
        } else {
            try {
                MySQLiteHelper db;
                db = SingletonHelperGlobal.getInstance().mDBDbHelper;
                db.cleardata();

                Utility.getInstance().hideSoftKeyboard(LoginActivity.this);
                progressBar.setMessage("Please wait...");
                progressBar.setCancelable(false);
                progressBar.setCanceledOnTouchOutside(false);
                progressBar.show();
                JSONObject obj = new JSONObject();
                obj.put("email", usernameStr);
                obj.put("password", passwordRaw);
                obj.put("ip_address", Utility.getInstance().getIPAddress());
                obj.put("device", "ANDROID PHONE");
                obj.put("device_model_number", Build.MODEL);
                obj.put("device_name", Build.MANUFACTURER);
                obj.put("address", "NA");


//              obj.put("device_model_number", "RMX2193");
                if (BuildConfig.DEBUG) {
                    obj.put("device_model_number", "CPH2381");
                    // obj.put("device_model_number", "Redmi 5A");
                }
                loginRequest(obj);

            } catch (Exception e) {
                //e.printStackTrace()
            }
        }
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
        Intent intent = new Intent(LoginActivity.this, SendOTPForgotPasswordActivity.class);
        startActivity(intent);
    }


    public void loginRequest(JSONObject jsonObject) {
        WebRequest mWebRequest = new WebRequest(this);
        Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.getLogin(AppConstant.CONTENT_TYPE,
                BuildConfig.VERSION_NAME, jsonObject.toString());
        user1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.errorBody() != null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Converter<ResponseBody, ModelError> converter = retrofit.responseBodyConverter(ModelError.class, new Annotation[0]);

                    try {
                        ModelError error = converter.convert(response.errorBody());
                        progressBar.dismiss();
                        Utility.getInstance().handleApiError(error.getMsg(), LoginActivity.this,prefsEditor);
                    } catch (IOException e) {
                        //This is Catch Block
                        progressBar.dismiss();
                    }
                } else {
                    progressBar.dismiss();
                    //Log.e("GET FE Details Response",response.body().getResponse())
                    // TODO key need to define
                    //String responsedecrypted = Utility.getInstance().makeAPIResponse(response.body().getResponse(),prefsMain);
                    try {
                        String responseBodyString = response.body().string();
                        JSONObject responseJSON = new JSONObject(responseBodyString);
                        if (responseJSON.getBoolean("status")) {
                            JSONObject resposneData = responseJSON.getJSONObject("data");
                            prefsEditor.putInt(AppConstant.USERID, resposneData.getInt("id"));
                            prefsEditor.putInt(AppConstant.COMPANY_ID, resposneData.getInt("company_id"));
                            prefsEditor.putString(AppConstant.API_TOKEN, resposneData.getString("api_token"));
                            prefsEditor.putString(AppConstant.EMPLOYEE_ID, resposneData.getString("employee_id"));
                            prefsEditor.putString(AppConstant.EMPLOYEE_NAME, resposneData.getString("name"));
                            prefsEditor.putString(AppConstant.USER_IMAGE, resposneData.getString("image"));
                            prefsEditor.putString(AppConstant.EMPLOYEE_EMAIL_ID, resposneData.getString("email"));
                            //prefsEditor.putBoolean(AppConstant.IS_USER_LOGGED_IN, true);
                            prefsEditor.commit();


                            try {
                                JSONObject obj = new JSONObject();
                                obj.put("version", BuildConfig.VERSION_NAME);
                                obj.put("device", "android");
                                checkAppVersion(obj);
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        } else {
                            Utility.getInstance().handleApiError(responseJSON.getString("message"), LoginActivity.this,prefsEditor);
                        }
                    } catch (Exception e) {
                        Log.e("dd", "sdds");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.dismiss();
            }
        });
    }


    public void checkAppVersion(JSONObject jsonObject) {
        String token = prefsMain.getString(AppConstant.API_TOKEN, "");
        WebRequest mWebRequest = new WebRequest(this);
        Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.checkAppVersion(AppConstant.CONTENT_TYPE,
                "Bearer " + token, jsonObject.toString());
        user1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.errorBody() != null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    Converter<ResponseBody, ModelError> converter = retrofit.responseBodyConverter(ModelError.class, new Annotation[0]);

                    try {
                        ModelError error = converter.convert(response.errorBody());
                        progressBar.dismiss();
                        Utility.getInstance().handleApiError(error.getMsg(),LoginActivity.this,prefsEditor);
                    } catch (IOException e) {
                        //This is Catch Block
                        progressBar.dismiss();
                    }
                } else {
                   progressBar.dismiss();
                    try {
                        String responseBodyString = response.body().string();
                        JSONObject responseJSON = new JSONObject(responseBodyString);
                        if (responseJSON.getBoolean("status")) {
                            if(responseJSON.getBoolean("is_update")){
                                //Update the Application
                                Utility.getInstance().showAppUpdateDailog(LoginActivity.this,prefsEditor,responseJSON.getString("msg"),LoginActivity.this);
                            }else{
                                Toast.makeText(LoginActivity.this, "Logged In Successfully", Toast.LENGTH_SHORT).show();
                                prefsEditor.putBoolean(AppConstant.IS_USER_LOGGED_IN, true);
                                prefsEditor.commit();
                                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                                finish();
                            }

                        } else {
                            Utility.getInstance().handleApiError(responseJSON.getString("msg"),LoginActivity.this,prefsEditor);
                        }
                    } catch (Exception e) {
                        Log.e("dd", "sdds");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressBar.dismiss();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utility.getInstance().deleteCache(LoginActivity.this);
    }
}
