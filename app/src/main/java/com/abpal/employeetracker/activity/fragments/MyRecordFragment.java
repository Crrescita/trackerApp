package com.abpal.employeetracker.activity.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abpal.employeetracker.activity.fragments.adapter.MyRecordGridRecylerViewAdapter;
import com.abpal.employeetracker.activity.fragments.adapter.MyRecordModal;
import com.abpal.employeetracker.activity.profile_menu.UpdateProfileActivity;
import com.abpal.employeetracker.activity.profile_menu.attendence_report.AttendenceReportActivity;
import com.abpal.employeetracker.activity.profile_menu.attendence_report.adapter.AttendenceModal;
import com.abpal.employeetracker.activity.profile_menu.attendence_report.adapter.AttendenceRecylerViewAdapter;
import com.abpal.tel.BuildConfig;
import com.abpal.tel.R;
import com.bumptech.glide.Glide;
import com.securepreferences.SecurePreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;

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

public class MyRecordFragment extends Fragment {

    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;

    private RecyclerView recyclerViewMyRecord;
    private ProgressDialog progressBar;
    private MyRecordGridRecylerViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_my_record, container, false);

        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();

        progressBar = new ProgressDialog(getActivity());

        recyclerViewMyRecord = view.findViewById(R.id.recylerViewMyRecord);
        getMyRecordData();
        return view;
    }

    public void getMyRecordData() {
        progressBar.setMessage("Please wait...");
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();

        String token = prefsMain.getString(AppConstant.API_TOKEN, "");
        WebRequest mWebRequest = new WebRequest(getActivity());
        Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.getMyRecord(AppConstant.CONTENT_TYPE,
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
                        Utility.getInstance().handleApiError(error.getMsg(), getActivity(),prefsEditor);
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

                            JSONArray data = responseJSON.getJSONArray("data");
                            ArrayList<MyRecordModal> myRecordModalArrayList = new ArrayList<>();
                            for(int i =0; i<data.length(); i++){
                                JSONObject dataObj = data.getJSONObject(i);
                                String title = dataObj.getString("title");
                                String icon = dataObj.getString("icon");
                                String detail = dataObj.getString("detail");

                                myRecordModalArrayList.add(new MyRecordModal(title,icon,detail));
                            }
                            adapter = new MyRecordGridRecylerViewAdapter(getActivity(),myRecordModalArrayList);
                            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
                            recyclerViewMyRecord.setLayoutManager(layoutManager);
                            recyclerViewMyRecord.setAdapter(adapter);

                        } else {
                            Utility.getInstance().handleApiError(responseJSON.getString("message"),getActivity(),prefsEditor);
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
}
