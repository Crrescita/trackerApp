package com.crrescita.employeetracker.activity.profile_menu.attendence_report;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crrescita.tel.BuildConfig;
import com.crrescita.tel.R;
import com.crrescita.employeetracker.activity.profile_menu.attendence_report.adapter.AttendenceModal;
import com.crrescita.employeetracker.activity.profile_menu.attendence_report.adapter.AttendenceRecylerViewAdapter;
import com.crrescita.employeetracker.activity.profile_menu.attendence_report.utils.MonthYearPickerDialog;
import com.securepreferences.SecurePreferences;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

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


public class AttendenceReportActivity extends AppCompatActivity implements MonthYearPickerDialog.OnDateSetListener  {

    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;
    private ProgressDialog progressBar;

    private AttendenceRecylerViewAdapter adapter;
     private RecyclerView recyclerView;

    private ImageView imageViewBackButton;
    private RelativeLayout backwordArrowImageView;
    private RelativeLayout forwardArrowImageView;
    private TextView textViewMonthAndYear;
    private int currentDay;
    private int currentMonth;
    private int currentYear;

    private MonthYearPickerDialog.OnDateSetListener listener = this;
    private boolean isMonthSelected = true; // by default month is selected

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendence_report);
        progressBar = new ProgressDialog(this);
        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();
        textViewMonthAndYear = findViewById(R.id.textViewMonthAndYear);
        backwordArrowImageView = findViewById(R.id.backwordArrowImageView);

        backwordArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             if(isMonthSelected){
                 if(currentMonth==0){
                     currentMonth=11;
                     currentYear = currentYear-1;
                 }else{
                     currentMonth = currentMonth-1;
                 }
                 setDateToTextView(0,currentYear,currentMonth);
                 callAttendenceAPIMonth(currentMonth,currentYear);
             }else{
                 if (currentDay == 1) {
                     currentDay = 31;
                     if(currentMonth==0){
                         currentMonth=11;
                         currentYear = currentYear-1;
                     }else{
                         currentMonth = currentMonth-1;
                     }
                 }else{
                     currentDay = currentDay -1;
                 }
                 setDateToTextView(currentDay,currentYear,currentMonth);
                 callAttendenceAPIDay(currentDay,currentMonth,currentYear);
             }
            }
        });


        forwardArrowImageView = findViewById(R.id.forwardArrowImageView);
        forwardArrowImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMonthSelected){
                    if(currentMonth==11){
                        currentMonth=0;
                        currentYear = currentYear+1;
                    }else{
                        currentMonth = currentMonth+1;
                    }
                    setDateToTextView(0,currentYear,currentMonth);
                    callAttendenceAPIMonth(currentMonth,currentYear);
                }else{
                    if (currentDay == 31) {
                        currentDay = 1;
                        if(currentMonth==11){
                            currentMonth=0;
                            currentYear = currentYear+1;
                        }else{
                            currentMonth = currentMonth+1;
                        }
                    }else{
                        currentDay = currentDay +1;
                    }
                    setDateToTextView(currentDay,currentYear,currentMonth);
                    callAttendenceAPIDay(currentDay,currentMonth,currentYear);
                }
//                  if(currentMonth==11){
//                      currentMonth=0;
//                      currentYear = currentYear+1;
//                  }else{
//                      currentMonth = currentMonth+1;
//                  }
//                  setDateToTextView(0,currentYear,currentMonth);
//                callAttendenceAPIMonth(currentMonth,currentYear);
            }
        });


        imageViewBackButton = findViewById(R.id.imageViewBackButton);
        recyclerView = findViewById(R.id.recylerView);



        imageViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // Get the current instance of Calendar
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        currentMonth = month;
        currentYear = year;
        currentDay =  day;
        setDateToTextView(0,currentYear,currentMonth);
        callAttendenceAPIMonth(currentMonth,currentYear);


        textViewMonthAndYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MonthYearPickerDialog dialog = new MonthYearPickerDialog(
                        AttendenceReportActivity.this,
                        listener,
                        currentMonth,
                        currentYear,
                        currentDay,
                        isMonthSelected
                );
                dialog.show();
            }
        });
    }

    public void onToggleClick(View view) {
        TextView dayView = findViewById(R.id.dayView);
        TextView monthView = findViewById(R.id.monthView);

        // Toggle state based on the clicked view
        if (view.getId() == R.id.dayView) {
            isMonthSelected = false;
            // Select day, deselect month
            dayView.setBackgroundResource(R.drawable.bg_selected);
            dayView.setTextColor(getResources().getColor(R.color.white));

            monthView.setBackgroundResource(R.drawable.bg_deselected);
            monthView.setTextColor(getResources().getColor(R.color.unselected));

            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            currentMonth = month;
            currentYear = year;
           currentDay = day;

            callAttendenceAPIDay(currentDay,currentMonth,currentYear);
            setDateToTextView(currentDay,currentYear,currentMonth);

        } else if (view.getId() == R.id.monthView) {
            isMonthSelected = true;
            // Select month, deselect day
            monthView.setBackgroundResource(R.drawable.bg_selected);
            monthView.setTextColor(getResources().getColor(R.color.white));

            dayView.setBackgroundResource(R.drawable.bg_deselected);
            dayView.setTextColor(getResources().getColor(R.color.unselected));

            Calendar calendar = Calendar.getInstance();
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            currentMonth = month;
            currentYear = year;
            currentDay =  day;
            setDateToTextView(0,currentYear,currentMonth);
            callAttendenceAPIMonth(currentMonth,currentYear);
        }
    }


    @Override
    public void onDateSet(int year, int month,int day,boolean isMonthSelected) {
        if(isMonthSelected){
            String monthString = new DateFormatSymbols().getMonths()[month];
            // Create the date string with the month name
            String selectedDate = monthString + " " + year;
            // Display the selected date
            Toast.makeText(AttendenceReportActivity.this,
                    "Selected date: " + selectedDate, Toast.LENGTH_SHORT).show();
            setDateToTextView(0,year, month);
            callAttendenceAPIMonth(month,year);
        }else{
            String monthString = new DateFormatSymbols().getMonths()[month];
            // Create the date string with the month name
            String selectedDate = monthString + " " + year+" "+day;
            // Display the selected date
            Toast.makeText(AttendenceReportActivity.this,
                    "Selected date: " + selectedDate, Toast.LENGTH_SHORT).show();
            setDateToTextView(day,year, month);
            callAttendenceAPIDay(day,month,year);
        }

    }

    private void setDateToTextView(int day,int year, int month) {
        if(isMonthSelected){
            String monthString = new DateFormatSymbols().getMonths()[month];
            // Create the date string with the month name
            String dateAndYear = monthString + " " + year;
            currentMonth = month;
            currentYear = year;
            textViewMonthAndYear.setText(dateAndYear);
        }else {
            String monthString = new DateFormatSymbols().getMonths()[month];
            // Create the date string with the month name
            String dateAndYear = day+" "+monthString + " " + year;
            currentMonth = month;
            currentYear = year;
            currentDay = day;
            textViewMonthAndYear.setText(dateAndYear);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Utility.getInstance().deleteCache(AttendenceReportActivity.this);
    }

    private void callAttendenceAPIMonth(int month,int year){
        // Format the month to be two digits
        String formattedMonth = String.format("%02d", month+1);

        String date = year+"-"+formattedMonth;

        progressBar.setMessage("Please wait...");
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();

        String token = prefsMain.getString(AppConstant.API_TOKEN, "");
        WebRequest mWebRequest = new WebRequest(this);
        Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.getEmployeeAttendance(AppConstant.CONTENT_TYPE,
                "Bearer " + token,date);
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
                        Utility.getInstance().handleApiError(error.getMsg(), AttendenceReportActivity.this,prefsEditor);
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
                            JSONArray checkDataInArray = data.getJSONArray("checkInsByDate");
                            ArrayList<AttendenceModal>attendenceModalArrayList = new ArrayList<>();
                            for(int i=0; i<checkDataInArray.length();i++){
                                JSONObject checkDataObj = checkDataInArray.getJSONObject(i);
                                String date = checkDataObj.getString("date");
                                String earliestCheckInTime = checkDataObj.getString("earliestCheckInTime");
                                String latestCheckOutTime = checkDataObj.getString("latestCheckOutTime");
                                String attendance_status = checkDataObj.getString("attendance_status");
                                String totalDuration = checkDataObj.getString("totalDuration");
                                attendenceModalArrayList.add(new AttendenceModal(
                                        date,
                                        attendance_status,
                                        earliestCheckInTime,
                                        latestCheckOutTime,
                                        totalDuration
                                ));
                            }

                            adapter = new AttendenceRecylerViewAdapter(AttendenceReportActivity.this,attendenceModalArrayList);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AttendenceReportActivity.this,
                                    LinearLayoutManager.VERTICAL,false);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);

                        } else {
                            Utility.getInstance().handleApiError(responseJSON.getString("message"),AttendenceReportActivity.this,prefsEditor);
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

    private void callAttendenceAPIDay(int day ,int month,int year){
        // Format the month to be two digits
        String formattedMonth = String.format("%02d", month+1);
        String formattedDay = String.format("%02d", day);

        String date = year+"-"+formattedMonth+"-"+formattedDay;

        progressBar.setMessage("Please wait...");
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();

        String token = prefsMain.getString(AppConstant.API_TOKEN, "");
        WebRequest mWebRequest = new WebRequest(this);
        Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.getEmployeeAttendance(AppConstant.CONTENT_TYPE,
                "Bearer " + token,date);
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
                        Utility.getInstance().handleApiError(error.getMsg(), AttendenceReportActivity.this,prefsEditor);
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
                            JSONArray checkDataInArray = data.getJSONArray("checkInsByDate");
                            ArrayList<AttendenceModal>attendenceModalArrayList = new ArrayList<>();
                            for(int i=0; i<checkDataInArray.length();i++){
                                JSONObject checkDataObj = checkDataInArray.getJSONObject(i);
                                String date = checkDataObj.getString("date");
                                String earliestCheckInTime = checkDataObj.getString("earliestCheckInTime");
                                String latestCheckOutTime = checkDataObj.getString("latestCheckOutTime");
                                String attendance_status = checkDataObj.getString("attendance_status");
                                String totalDuration = checkDataObj.getString("totalDuration");
                                attendenceModalArrayList.add(new AttendenceModal(
                                        date,
                                        attendance_status,
                                        earliestCheckInTime,
                                        latestCheckOutTime,
                                        totalDuration
                                ));
                            }

                            adapter = new AttendenceRecylerViewAdapter(AttendenceReportActivity.this,attendenceModalArrayList);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(AttendenceReportActivity.this,
                                    LinearLayoutManager.VERTICAL,false);
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(adapter);

                        } else {
                            Utility.getInstance().handleApiError(responseJSON.getString("message"),AttendenceReportActivity.this,prefsEditor);
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


//    private ArrayList<AttendenceModal> getAttendenceDummylist(){
//        ArrayList<AttendenceModal>attendenceModalArrayList = new ArrayList<>();
//        attendenceModalArrayList.add(new AttendenceModal("Tuesday,20 August 2024","1","09:00:AM","06:00:PM","09:00 H"));
//        attendenceModalArrayList.add(new AttendenceModal("Tuesday,20 August 2024","2","09:00:AM","06:00:PM","09:00 H"));
//        attendenceModalArrayList.add(new AttendenceModal("Tuesday,20 August 2024","1","09:00:AM","06:00:PM","09:00 H"));
//        attendenceModalArrayList.add(new AttendenceModal("Tuesday,20 August 2024","1","09:00:AM","06:00:PM","09:00 H"));
//        attendenceModalArrayList.add(new AttendenceModal("Tuesday,20 August 2024","3","09:00:AM","06:00:PM","09:00 H"));
//        attendenceModalArrayList.add(new AttendenceModal("Tuesday,20 August 2024","2","09:00:AM","06:00:PM","09:00 H"));
//        attendenceModalArrayList.add(new AttendenceModal("Tuesday,20 August 2024","1","09:00:AM","06:00:PM","09:00 H"));
//        attendenceModalArrayList.add(new AttendenceModal("Tuesday,20 August 2024","3","09:00:AM","06:00:PM","09:00 H"));
//        attendenceModalArrayList.add(new AttendenceModal("Tuesday,20 August 2024","2","09:00:AM","06:00:PM","09:00 H"));
//        return  attendenceModalArrayList;
//    }



}
