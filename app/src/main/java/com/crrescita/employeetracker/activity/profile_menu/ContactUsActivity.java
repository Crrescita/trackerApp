package com.crrescita.employeetracker.activity.profile_menu;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.crrescita.tel.BuildConfig;
import com.crrescita.tel.R;
import com.bumptech.glide.Glide;
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

public class ContactUsActivity extends AppCompatActivity  {

    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;
    private ProgressDialog progressBar;

    private ImageView imageViewBackButton;
    private ImageView imageViewHeader;
    private TextView textViewLocation;
    private TextView textViewPhoneNumber;
    private TextView textViewEmailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


        progressBar = new ProgressDialog(this);
        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();

        imageViewBackButton = findViewById(R.id.imageViewBackButton);
        imageViewHeader = findViewById(R.id.imageViewHeader);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewPhoneNumber = findViewById(R.id.textViewPhoneNumber);
        textViewEmailAddress = findViewById(R.id.textViewEmailAddress);


        imageViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        getEmployeeCompany();
    }

    public void getEmployeeCompany() {
        progressBar.setMessage("Please wait...");
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();

        String token = prefsMain.getString(AppConstant.API_TOKEN, "");
        WebRequest mWebRequest = new WebRequest(this);
        Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.getEmployeeCompanay(AppConstant.CONTENT_TYPE,
                "Bearer " + token);
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
                        Utility.getInstance().handleApiError(error.getMsg(), ContactUsActivity.this,prefsEditor);
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

                            JSONObject data = responseJSON.getJSONObject("data");
                            JSONObject company = data.getJSONObject("company");
                            String name = company.getString("name");
                            String logo = company.getString("logo");
                            String email = company.getString("email");
                            String mobile = company.getString("mobile");
                            String address = company.getString("address");
                            String city = company.getString("city");
                            String zip_code = company.getString("zip_code");
                            String state = company.getString("state");

                            textViewPhoneNumber.setText(mobile);
                            textViewEmailAddress.setText(email);
                            textViewLocation.setText(address+", "+city+", "+zip_code+", "+state);

                            Glide.with(ContactUsActivity.this)
                                    .load(logo)
                                    .into(imageViewHeader);

                        } else {
                            Utility.getInstance().handleApiError(responseJSON.getString("message"),ContactUsActivity.this,prefsEditor);
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
        Utility.getInstance().deleteCache(ContactUsActivity.this);
    }
}
