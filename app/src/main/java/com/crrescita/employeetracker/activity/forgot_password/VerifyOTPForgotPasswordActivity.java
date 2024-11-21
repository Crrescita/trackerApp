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

public class VerifyOTPForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private MaterialButton proceedButton;

    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;
    private ProgressDialog progressBar;
    private TextInputEditText otpEditText;

    private String emailAddress="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp_forgot_password);

        TextInputLayout otpInputLayout = findViewById(R.id.otpInputLayout);
        otpEditText = otpInputLayout.findViewById(R.id.otpEditText);

        Intent intent = getIntent();
        if(null!=intent){
            emailAddress = intent.getStringExtra("email");
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
        String otp = otpEditText.getText().toString();
        if (otp.length() == 0) {
            Utility.getInstance().showToastCenter(" Enter the 4 digit otp",VerifyOTPForgotPasswordActivity.this);
        } else {
            try {
                Utility.getInstance().hideSoftKeyboard(VerifyOTPForgotPasswordActivity.this);
                progressBar.setMessage("Please wait...");
                progressBar.setCancelable(false);
                progressBar.setCanceledOnTouchOutside(false);
                progressBar.show();
                JSONObject obj = new JSONObject();
                obj.put("email", emailAddress);
                obj.put("code", otp);
                sendRequest(obj);

            } catch (Exception e) {
                //e.printStackTrace()
            }
        }
    }


    public void sendRequest(JSONObject jsonObject) {
        WebRequest mWebRequest = new WebRequest(this);
        Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.validateResetCode(AppConstant.CONTENT_TYPE,
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
                        Utility.getInstance().handleApiError(error.getMsg(),VerifyOTPForgotPasswordActivity.this,prefsEditor);
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
                            Toast.makeText(VerifyOTPForgotPasswordActivity.this, responseJSON.getString("message"), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(VerifyOTPForgotPasswordActivity.this, ChangePasswordForgotPasswordActivity.class);
                            intent.putExtra("email",emailAddress);
                            intent.putExtra("otp",otpEditText.getText().toString());
                            startActivity(intent);
                            finish();
                        } else {
                            Utility.getInstance().handleApiError(responseJSON.getString("message"),VerifyOTPForgotPasswordActivity.this,prefsEditor);
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
        Utility.getInstance().deleteCache(VerifyOTPForgotPasswordActivity.this);
    }
}
