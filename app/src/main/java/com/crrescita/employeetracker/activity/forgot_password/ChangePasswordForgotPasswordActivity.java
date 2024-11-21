package com.crrescita.employeetracker.activity.forgot_password;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class ChangePasswordForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton proceedButton;

    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;
    private ProgressDialog progressBar;
    private String emailAddress="";
    private String OTP = "";

    private TextInputEditText passwordEditText;
    private TextInputEditText passwordConfirmEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);


        TextInputLayout passwordInputLayout = findViewById(R.id.passwordInputLayout);
        passwordEditText = passwordInputLayout.findViewById(R.id.passwordEditText);

        TextInputLayout passwordConfirmInputLayout = findViewById(R.id.passwordConfirmInputLayout);
        passwordConfirmEditText = passwordConfirmInputLayout.findViewById(R.id.passwordConfirmEditText);

        Intent intent = getIntent();

        if(null!=intent){
            emailAddress = intent.getStringExtra("email");
            OTP = intent.getStringExtra("otp");
        }

        progressBar = new ProgressDialog(this);
        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();

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
        String password = passwordEditText.getText().toString();
        String confirmPassword = passwordConfirmEditText.getText().toString();
        if (password.length() == 0) {
            Utility.getInstance().showToastCenter(" Enter the password",ChangePasswordForgotPasswordActivity.this);
        } else if(confirmPassword.length()==0){
            Utility.getInstance().showToastCenter(" Enter the confirm New password",ChangePasswordForgotPasswordActivity.this);
        }else {
            try {
                Utility.getInstance().hideSoftKeyboard(ChangePasswordForgotPasswordActivity.this);
                progressBar.setMessage("Please wait...");
                progressBar.setCancelable(false);
                progressBar.setCanceledOnTouchOutside(false);
                progressBar.show();
                JSONObject obj = new JSONObject();
                obj.put("email", emailAddress);
                obj.put("code", OTP);
                obj.put("newPassword", password);
                obj.put("confirmPassword", confirmPassword);
                sendRequest(obj);

            } catch (Exception e) {
                //e.printStackTrace()
            }
        }
    }


    public void sendRequest(JSONObject jsonObject) {
        WebRequest mWebRequest = new WebRequest(this);
        Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.resetPassword(AppConstant.CONTENT_TYPE,
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
                        Utility.getInstance().handleApiError(error.getMsg(),ChangePasswordForgotPasswordActivity.this,prefsEditor);
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
                            Toast.makeText(ChangePasswordForgotPasswordActivity.this, responseJSON.getString("message"), Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Utility.getInstance().handleApiError(responseJSON.getString("message"),ChangePasswordForgotPasswordActivity.this,prefsEditor);
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
        Utility.getInstance().deleteCache(ChangePasswordForgotPasswordActivity.this);
    }
}
