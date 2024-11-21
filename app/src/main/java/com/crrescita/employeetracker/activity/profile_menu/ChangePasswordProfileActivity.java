package com.crrescita.employeetracker.activity.profile_menu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.crrescita.tel.BuildConfig;
import com.crrescita.tel.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.securepreferences.SecurePreferences;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;

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

public class ChangePasswordProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton proceedButton;

    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;
    private ProgressDialog progressBar;

    private ImageView imageViewBackButton;

    private TextInputEditText currentPasswordEditText;
    private TextInputEditText newPasswordEditText;
    private TextInputEditText passwordNewConfirmEditText;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_profile);
        progressBar = new ProgressDialog(this);
        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();


        TextInputLayout currentPasswordInputLayout = findViewById(R.id.currentPasswordInputLayout);
        currentPasswordEditText = currentPasswordInputLayout.findViewById(R.id.currentPasswordEditText);

        TextInputLayout newPasswordInputLayout = findViewById(R.id.newPasswordInputLayout);
        newPasswordEditText = newPasswordInputLayout.findViewById(R.id.newPasswordEditText);


        TextInputLayout passwordNewConfirmInputLayout = findViewById(R.id.passwordNewConfirmInputLayout);
        passwordNewConfirmEditText = passwordNewConfirmInputLayout.findViewById(R.id.passwordNewConfirmEditText);



        imageViewBackButton = findViewById(R.id.imageViewBackButton);
        imageViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        proceedButton = findViewById(R.id.proceedButton);
        proceedButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
     switch (v.getId()){
         case R.id.proceedButton:
             proceed();
             break;
         default:
             break;
     }
    }

    private void proceed(){
        String emailAddress = prefsMain.getString(AppConstant.EMPLOYEE_EMAIL_ID, "-----NA----");
        String oldPassword = currentPasswordEditText.getText().toString();
        String password = newPasswordEditText.getText().toString();
        String confirmPassword = passwordNewConfirmEditText.getText().toString();
        if (oldPassword.length() == 0) {
            Utility.getInstance().showToastCenter(" Enter the current password", ChangePasswordProfileActivity.this);
        } else if(password.length()==0){
            Utility.getInstance().showToastCenter(" Enter the new password",ChangePasswordProfileActivity.this);
        } else if(confirmPassword.length()==0){
            Utility.getInstance().showToastCenter(" Enter the confirm New password",ChangePasswordProfileActivity.this);
        }else {
            try {
                Utility.getInstance().hideSoftKeyboard(ChangePasswordProfileActivity.this);
                progressBar.setMessage("Please wait...");
                progressBar.setCancelable(false);
                progressBar.setCanceledOnTouchOutside(false);
                progressBar.show();
                JSONObject obj = new JSONObject();
                obj.put("email", emailAddress);
                obj.put("oldPassword", oldPassword);
                obj.put("newPassword", password);
                obj.put("confirmPassword", confirmPassword);
                sendRequest(obj);

            } catch (Exception e) {
                //e.printStackTrace()
            }
        }
    }


    public void sendRequest(JSONObject jsonObject) {
        String token = prefsMain.getString(AppConstant.API_TOKEN, "");
        WebRequest mWebRequest = new WebRequest(this);
        Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.changePassword(AppConstant.CONTENT_TYPE,
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
                        Utility.getInstance().handleApiError(error.getMsg(),ChangePasswordProfileActivity.this,prefsEditor);
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
                            Toast.makeText(ChangePasswordProfileActivity.this, responseJSON.getString("message"), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Utility.getInstance().handleApiError(responseJSON.getString("message"),ChangePasswordProfileActivity.this,prefsEditor);
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
        Utility.getInstance().deleteCache(ChangePasswordProfileActivity.this);
    }
}
