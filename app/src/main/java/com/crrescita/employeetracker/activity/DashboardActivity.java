package com.crrescita.employeetracker.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.crrescita.tel.BuildConfig;
import com.crrescita.tel.R;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.securepreferences.SecurePreferences;

import de.hdodenhof.circleimageview.CircleImageView;
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
import utils.SingletonHelperGlobal;
import utils.Utility;

import com.crrescita.employeetracker.activity.fragments.*;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;

public class DashboardActivity extends AppCompatActivity {
     private TextView textViewDashboard;
     private TextView userName;
    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;

    private CircleImageView user_image;
    private String TAG = "DashboardActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        userName = findViewById(R.id.user_name);
        user_image = findViewById(R.id.user_image);

        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();
        getTokenFirebase();
        subscribeFCMByTopic();


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

    private void getTokenFirebase(){
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }

                    // Get new FCM registration token
                    String token = task.getResult();

                    // Log and toast
                    String msg = token;
                    Log.d(TAG, msg);

                    try {
                        JSONObject obj = new JSONObject();
                        obj.put("fcm_token", token);
                        setFCMToken(obj);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                    //call API from here
                   // new AsyncDownloadMasterDataAndCheckField(db,DashboardActivity.this,msg,prefsMain);
                    //Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();
                });
    }


    private void subscribeFCMByTopic(){
        FirebaseMessaging.getInstance().subscribeToTopic("Global_Info")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscribe failed";
                        }
                        Log.d(TAG, msg);
                        //Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }


    public void setFCMToken(JSONObject jsonObject) {
        String token = prefsMain.getString(AppConstant.API_TOKEN, "");
        WebRequest mWebRequest = new WebRequest(this);
        Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.setFcmToken(AppConstant.CONTENT_TYPE,
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
                        Utility.getInstance().handleApiError(error.getMsg(), DashboardActivity.this,prefsEditor);
                    } catch (IOException e) {
                        //This is Catch Block

                    }
                } else {
                    try {
                        String responseBodyString = response.body().string();
                        JSONObject responseJSON = new JSONObject(responseBodyString);
                        if (responseJSON.getBoolean("status")) {
                          //  Toast.makeText(DashboardActivity.this, responseJSON.getString("message"), Toast.LENGTH_SHORT).show();
                        } else {
                            Utility.getInstance().handleApiError(responseJSON.getString("message"),DashboardActivity.this,prefsEditor);
                        }
                    } catch (Exception e) {
                        Log.e("dd", "sdds");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }



    @Override
    protected void onResume() {
        super.onResume();
        userName.setText("Welcome "+prefsMain.getString(AppConstant.EMPLOYEE_NAME,""));
        Glide.with(this)
                .load(prefsMain.getString(AppConstant.USER_IMAGE,""))
                .into(user_image);


        try {
            JSONObject obj = new JSONObject();
            obj.put("version", BuildConfig.VERSION_NAME);
            obj.put("device", "android");
            checkAppVersion(obj);
        }catch (Exception e){
            e.printStackTrace();
        }

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
//                        progressBar.dismiss();
                        Utility.getInstance().handleApiError(error.getMsg(),DashboardActivity.this,prefsEditor);
                    } catch (IOException e) {
                        //This is Catch Block
//                        progressBar.dismiss();
                    }
                } else {
//                    progressBar.dismiss();
                    try {
                        String responseBodyString = response.body().string();
                        JSONObject responseJSON = new JSONObject(responseBodyString);
                        if (responseJSON.getBoolean("status")) {
                            if(responseJSON.getBoolean("is_update")){
                                //Update the Application
                                Utility.getInstance().showAppUpdateDailog(DashboardActivity.this,prefsEditor,responseJSON.getString("msg"),DashboardActivity.this);
                            }else{
                                //Toast.makeText(DashboardActivity.this, responseJSON.getString("msg"), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Utility.getInstance().handleApiError(responseJSON.getString("msg"),DashboardActivity.this,prefsEditor);
                        }
                    } catch (Exception e) {
                        Log.e("dd", "sdds");
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                progressBar.dismiss();
            }
        });
    }

}