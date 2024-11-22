package com.crrescita.employeetracker.activity.fragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.crrescita.employeetracker.activity.profile_menu.SupportUsActivity;
import com.crrescita.tel.BuildConfig;
import com.crrescita.tel.R;
import com.crrescita.employeetracker.activity.LoginActivity;
import com.crrescita.employeetracker.activity.profile_menu.ChangePasswordProfileActivity;
import com.crrescita.employeetracker.activity.profile_menu.ContactUsActivity;
import com.crrescita.employeetracker.activity.profile_menu.UpdateProfileActivity;
import com.crrescita.employeetracker.activity.profile_menu.attendence_report.AttendenceReportActivity;
import com.bumptech.glide.Glide;
import com.securepreferences.SecurePreferences;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;

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

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView userName;
    private TextView userEmailId;
    private CircleImageView user_image;
    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;

    private CardView cardViewEditProfile;
    private CardView cardViewChangePassword;
    private CardView cardViewAttendenceReport;
    private CardView cardViewContactUs;
    private CardView cardViewLogout;
    private CardView cardViewSupportUs;
    private CardView cardViewDeleteAccount;
    private ProgressDialog progressBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();

        userName = view.findViewById(R.id.profile_name);
        userEmailId = view.findViewById(R.id.profile_email);
        user_image = view.findViewById(R.id.user_image);



        cardViewEditProfile = view.findViewById(R.id.cardViewEditProfile);
        cardViewEditProfile.setOnClickListener(this);

        cardViewChangePassword = view.findViewById(R.id.cardViewChangePassword);
        cardViewChangePassword.setOnClickListener(this);

        cardViewAttendenceReport = view.findViewById(R.id.cardViewAttendenceReport);
        cardViewAttendenceReport.setOnClickListener(this);

        cardViewContactUs = view.findViewById(R.id.cardViewContactUs);
        cardViewContactUs.setOnClickListener(this);

        cardViewLogout = view.findViewById(R.id.cardViewLogout);
        cardViewLogout.setOnClickListener(this);

        cardViewSupportUs = view.findViewById(R.id.cardViewSupportUs);
        cardViewSupportUs.setOnClickListener(this);

        cardViewDeleteAccount = view.findViewById(R.id.cardViewDeleteAccount);
        cardViewDeleteAccount.setOnClickListener(this);

        progressBar = new ProgressDialog(getActivity());



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this)
                .load(prefsMain.getString(AppConstant.USER_IMAGE,""))
                .into(user_image);

        userName.setText(prefsMain.getString(AppConstant.EMPLOYEE_NAME, "----------NA-------"));
        userEmailId.setText(prefsMain.getString(AppConstant.EMPLOYEE_EMAIL_ID, "-----NA----"));
    }


    private void showDeleteAccountConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account? You can reactivate it within 90 days; otherwise, it will be permanently deleted.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteAccountAPI();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void showLogoutConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        logoutAPI();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void logout() {
        prefsEditor.clear().apply();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        getActivity().finish();
    }


    private void logoutAPI() {
        try {
            progressBar.setMessage("Please wait...");
            progressBar.setCancelable(false);
            progressBar.setCanceledOnTouchOutside(false);
            progressBar.show();
            JSONObject obj = new JSONObject();
            obj.put("device", "ANDROID PHONE");
            obj.put("ip_address", Utility.getInstance().getIPAddress());
            obj.put("address", "NA");
            obj.put("model_no", Build.MODEL);


            WebRequest mWebRequest = new WebRequest(getActivity());
            String token = prefsMain.getString(AppConstant.API_TOKEN, "");
            Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.logoutAPI(AppConstant.CONTENT_TYPE,
                    "Bearer " + token, obj.toString());
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
                            if (getActivity() != null && !getActivity().isFinishing()) {
                                Utility.getInstance().handleApiError(error.getMsg(), getActivity(), prefsEditor);
                            }

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
                                Toast.makeText(getActivity(), responseJSON.getString("message"), Toast.LENGTH_SHORT).show();
                                logout();
                            } else {
                                if (getActivity() != null && !getActivity().isFinishing()) {
                                    Utility.getInstance().handleApiError(responseJSON.getString("message"),
                                            getActivity(), prefsEditor);
                                }
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

        } catch (Exception e) {
            e.printStackTrace();
            progressBar.dismiss();
        }

    }

    private void deleteAccountAPI() {
        try {
            progressBar.setMessage("Please wait...");
            progressBar.setCancelable(false);
            progressBar.setCanceledOnTouchOutside(false);
            progressBar.show();
            JSONObject obj = new JSONObject();
            obj.put("status", "inactive");


            WebRequest mWebRequest = new WebRequest(getActivity());
            String token = prefsMain.getString(AppConstant.API_TOKEN, "");
            Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.deleteAccount(AppConstant.CONTENT_TYPE,
                    "Bearer " + token,obj.toString());
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
                            if (getActivity() != null && !getActivity().isFinishing()) {
                                Utility.getInstance().handleApiError(error.getMsg(), getActivity(), prefsEditor);
                            }

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
                                Toast.makeText(getActivity(), responseJSON.getString("message"), Toast.LENGTH_SHORT).show();
                                logout();
                            } else {
                                if (getActivity() != null && !getActivity().isFinishing()) {
                                    Utility.getInstance().handleApiError(responseJSON.getString("message"),
                                            getActivity(), prefsEditor);
                                }
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

        } catch (Exception e) {
            e.printStackTrace();
            progressBar.dismiss();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.cardViewEditProfile:
                Intent intent3 = new Intent(getActivity(), UpdateProfileActivity.class);
                startActivity(intent3);
                break;

            case R.id.cardViewChangePassword:
                Intent intent = new Intent(getActivity(), ChangePasswordProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.cardViewAttendenceReport:
                Intent intent4 = new Intent(getActivity(), AttendenceReportActivity.class);
                startActivity(intent4);
                break;

            case R.id.cardViewContactUs:
                Intent intent2 = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent2);
                break;


            case R.id.cardViewSupportUs:
                Intent intent6 = new Intent(getActivity(), SupportUsActivity.class);
                startActivity(intent6);
                break;

            case R.id.cardViewLogout:
                showLogoutConfirmationDialog();
                break;

            case R.id.cardViewDeleteAccount:
                 showDeleteAccountConfirmationDialog();
                 break;
            default:
                break;
        }
    }
}
