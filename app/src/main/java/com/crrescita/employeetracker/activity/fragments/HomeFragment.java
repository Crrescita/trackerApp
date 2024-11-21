package com.crrescita.employeetracker.activity.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.crrescita.tel.BuildConfig;
import com.crrescita.tel.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.securepreferences.SecurePreferences;

import java.lang.annotation.Annotation;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
import utils.UtilGps;
import utils.Utility;
import utils.service.LocationService;

public class HomeFragment extends Fragment {
    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private ProgressDialog progressBar;

    private String latituteGlobal = "";
    private String longituteGlobal = "";

    private CircleImageView attendenceImageButton;
    private TextView textViewSetAttendenceDay;
    private TextView textViewSetAttendenceDate;
    private TextView textViewDateTimeAndDay;
    private TextView textViewRunningTime;
    private TextInputEditText checkInEditText;
    private TextInputEditText checkoutEditText;
    private TextInputEditText totalTimeEditText;
    private boolean isPunchedIn = false;
    private Handler handler;
    private Runnable timeRunnable;

    private LocationService mLocationService;

    private Intent mServiceIntent;

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        String currentDay = new SimpleDateFormat("EEEE", Locale.getDefault()).format(new Date());
        String currentDate = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(new Date());

        prefsMain = SingletonHelperGlobal.mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();

        isPunchedIn = prefsMain.getBoolean(AppConstant.IS_PUNCHED_IN, false);

        attendenceImageButton = view.findViewById(R.id.imageViewAttendence);
        textViewSetAttendenceDay = view.findViewById(R.id.textViewSetAttendenceDay);
        textViewSetAttendenceDay.setText("Set attendence for " + currentDay + ",");
        textViewSetAttendenceDate = view.findViewById(R.id.textViewSetAttendenceDate);
        textViewSetAttendenceDate.setText(currentDate);
        textViewDateTimeAndDay = view.findViewById(R.id.textViewDateTimeAndDay);

        textViewDateTimeAndDay.setText(currentDay + ", " + currentDate);


        textViewRunningTime = view.findViewById(R.id.textViewRunningTime);


        TextInputLayout checkInInputLayout = view.findViewById(R.id.checkInInputLayout);
        TextInputLayout checkoutInputLayout = view.findViewById(R.id.checkoutInputLayout);
        TextInputLayout totalHourInputLayout = view.findViewById(R.id.totalHourInputLayout);

        checkInEditText = checkInInputLayout.findViewById(R.id.checkInEditText);
        checkoutEditText = checkoutInputLayout.findViewById(R.id.checkoutEditText);
        totalTimeEditText = totalHourInputLayout.findViewById(R.id.totalTimeEditText);


        progressBar = new ProgressDialog(getActivity());


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());


        // Initialize punch status and times
        // updatePunchStatus();
        getPunchStatusFromServer();

        updateTimeForTicker();

        attendenceImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vibrateDevice();
                isPunchedIn = prefsMain.getBoolean(AppConstant.IS_PUNCHED_IN, false);
                if (isGPSEnabled()) {
                    if (latituteGlobal.equals("") && longituteGlobal.equals("")) {
                        setupLocationUpdates();
                        if (getActivity() != null && !getActivity().isFinishing()) {
                            Toast.makeText(getActivity(), "Please wait your GPS intailize", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        if (isPunchedIn) {
                            punchOutCall();
                            // Punch out
                        } else {
                            // punch in
                            punchInCall();
                        }
                        //updatePunchStatus();
                    }

                } else {
                    if (getActivity() != null && !getActivity().isFinishing()) {
                        Toast.makeText(getActivity(), "Please enable GPS", Toast.LENGTH_LONG).show();
                    }
                    startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            }
        });


        return view;
    }



    private void vibrateDevice() {
        // Get the Vibrator service
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

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

    private void updateTimeForTicker() {
        handler = new Handler();
        timeRunnable = new Runnable() {
            @Override
            public void run() {
                // Get current time and set it to the TextView
                String currentTime = new SimpleDateFormat("hh:mm a", Locale.getDefault()).format(new Date());
                textViewRunningTime.setText(currentTime);

                // Repeat this every second
                handler.postDelayed(this, 1000);
            }
        };

        // Start the ticker
        handler.post(timeRunnable);
    }


    @Override
    public void onResume() {
        super.onResume();
        latituteGlobal = "";
        longituteGlobal = "";
        setupLocationUpdates();
    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) requireActivity().getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isGPSEnabled;
    }


    private void punchOutCall() {
        String emp_id = String.valueOf(prefsMain.getInt(AppConstant.USERID, 2));
        String company_id = String.valueOf(prefsMain.getInt(AppConstant.COMPANY_ID, 1));
        String lat_check_in = latituteGlobal;
        String long_check_in = longituteGlobal;
        String checkin_imgstr = "";
        String battery_status_at_checkIn = "100";
        // String passwordStr = Utility.getInstance().md5(passwordRaw);

        try {
            progressBar.setMessage("Please wait...");
            progressBar.setCancelable(false);
            progressBar.setCanceledOnTouchOutside(false);
            progressBar.show();
            JSONObject obj = new JSONObject();
            obj.put("emp_id", emp_id);
            obj.put("company_id", company_id);
            obj.put("lat_check_out", lat_check_in);
            obj.put("long_check_out", long_check_in);
            obj.put("checkout_img", checkin_imgstr);
            obj.put("battery_status_at_checkout", battery_status_at_checkIn);


            WebRequest mWebRequest = new WebRequest(getActivity());
            String token = prefsMain.getString(AppConstant.API_TOKEN, "");
            Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.checkOut(AppConstant.CONTENT_TYPE,
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
                                JSONObject obj = responseJSON.getJSONObject("data");
                                // JSONObject obj = data.getJSONObject(0);

                                String checkin_status = obj.getString("checkin_status");
                                String total_duration = obj.getString("total_duration");
                                String earliestCheckInTime = obj.getString("earliestCheckInTime");
                                String latestCheckOutTime = obj.getString("latestCheckOutTime");

                                updatePunchStatus(checkin_status, earliestCheckInTime, latestCheckOutTime, total_duration);
                                if (getActivity() != null && !getActivity().isFinishing()) {
                                    Toast.makeText(getActivity(), "Check out Successfully", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if (getActivity() != null && !getActivity().isFinishing()) {
                                    Utility.getInstance().handleApiError(responseJSON.getString("message"), getActivity(), prefsEditor);
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

    private void punchInCall() {
        String emp_id = String.valueOf(prefsMain.getInt(AppConstant.USERID, 2));
        String company_id = String.valueOf(prefsMain.getInt(AppConstant.COMPANY_ID, 1));
        String lat_check_in = latituteGlobal;
        String long_check_in = longituteGlobal;
        String checkin_imgstr = "";
        String battery_status_at_checkIn = "100";
        // String passwordStr = Utility.getInstance().md5(passwordRaw);

        try {
            progressBar.setMessage("Please wait...");
            progressBar.setCancelable(false);
            progressBar.setCanceledOnTouchOutside(false);
            progressBar.show();
            JSONObject obj = new JSONObject();
            obj.put("emp_id", emp_id);
            obj.put("company_id", company_id);
            obj.put("lat_check_in", lat_check_in);
            obj.put("long_check_in", long_check_in);
            obj.put("checkin_img", checkin_imgstr);
            obj.put("battery_status_at_checkIn", battery_status_at_checkIn);


            WebRequest mWebRequest = new WebRequest(getActivity());
            String token = prefsMain.getString(AppConstant.API_TOKEN, "");
            Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.checkIn(AppConstant.CONTENT_TYPE,
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
                                if (getActivity() != null && !getActivity().isFinishing()) {
                                    Toast.makeText(getActivity(), "Check in Successfully", Toast.LENGTH_SHORT).show();
                                }
                                JSONObject obj = responseJSON.getJSONObject("data");
                                // JSONObject obj = data.getJSONObject(0);

                                String checkin_status = obj.getString("checkin_status");
                                String total_duration = obj.getString("total_duration");
                                String earliestCheckInTime = obj.getString("earliestCheckInTime");
                                String latestCheckOutTime = obj.getString("latestCheckOutTime");

                                updatePunchStatus(checkin_status, earliestCheckInTime, latestCheckOutTime, total_duration);
                            } else {
                                if (getActivity() != null && !getActivity().isFinishing()) {
                                    Utility.getInstance().handleApiError(responseJSON.getString("message"), getActivity(), prefsEditor);
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


    private void setupLocationUpdates() {
        progressBar.setMessage("Please wait...");
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // High accuracy for precise results
        locationRequest.setInterval(0); // Immediate location
        locationRequest.setFastestInterval(0);

        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Use fusedLocationClient to request a single location update
            fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, null)
                    .addOnSuccessListener(location -> {
                        progressBar.dismiss();
                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            // Save the current location to shared preferences
                            prefsEditor.putString("latitudeFused", String.valueOf(latitude));
                            prefsEditor.putString("longitudeFused", String.valueOf(longitude));
                            prefsEditor.commit();

                            // Update global variables
                            latituteGlobal = String.valueOf(latitude);
                            longituteGlobal = String.valueOf(longitude);

                            // Log the current location
                            Log.d("LocationService", "Current location: " + latitude + ", " + longitude);
                        } else {
                            Log.e("LocationService", "Failed to get the current location.");
                        }
                    })
                    .addOnFailureListener(e -> Log.e("LocationService", "Error getting current location: " + e.getMessage()));
        } else {
            Log.e("LocationService", "Permission for location access is not granted");
        }
    }

//    private void setupLocationUpdates() {
//        LocationRequest locationRequest = LocationRequest.create();
//        locationRequest.setInterval(1000); // 1 minute
//        locationRequest.setFastestInterval(1000); // 30 seconds
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//
//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(LocationResult locationResult) {
//                if (locationResult == null) {
//                    return;
//                }
//                for (Location location : locationResult.getLocations()) {
//                    if (location != null) {
//                        double latitude = location.getLatitude();
//                        double longitude = location.getLongitude();
//                        prefsEditor.putString("latitudeFused", String.valueOf(latitude));
//                        prefsEditor.putString("longitudeFused", String.valueOf(longitude));
//                        prefsEditor.commit();
//                        latituteGlobal = latitude + "";
//                        longituteGlobal = longitude + "";
//                        //tvLocation.setText("Current Location: " + latitude + ", " + longitude);
//                        // Log.d("Location Service", "Location update: " + location);
//                    }
//                }
//            }
//        };
//
//        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
//            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
//        } else {
//            // Handle permission request or show a message to the user
//        }
//    }

    private void updatePunchStatus(String checkin_status, String punchInTime, String punchOutTime, String totalDuration) {
        if (checkin_status.equalsIgnoreCase("Check-in")) {
            attendenceImageButton.setImageResource(R.drawable.checkout);
            prefsEditor.putBoolean(AppConstant.IS_PUNCHED_IN, true);

            mLocationService = new LocationService();
            mServiceIntent = new Intent(getActivity(), LocationService.class);
            if (!UtilGps.INSTANCE.isMyServiceRunning(mLocationService.getClass(), getActivity())) {
                prefsEditor.putBoolean(AppConstant.IS_MANUALY_SERVICE_STOP, false);
                prefsEditor.commit();
                //getActivity().startService(mServiceIntent);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    getActivity().startForegroundService(mServiceIntent);
                } else {
                    getActivity().startService(mServiceIntent);
                }
            }


        } else {
            attendenceImageButton.setImageResource(R.drawable.check_in);
            prefsEditor.putBoolean(AppConstant.IS_PUNCHED_IN, false);

            mLocationService = new LocationService();
            mServiceIntent = new Intent(getActivity(), LocationService.class);
            if (UtilGps.INSTANCE.isMyServiceRunning(mLocationService.getClass(), getActivity())) {
                prefsEditor.putBoolean(AppConstant.IS_MANUALY_SERVICE_STOP, true);
                prefsEditor.commit();
                getActivity().stopService(mServiceIntent);
            }

        }

        prefsEditor.commit();

        checkInEditText.setText(punchInTime);
        checkoutEditText.setText(punchOutTime);
        totalTimeEditText.setText(totalDuration);
//        boolean isPunchedIn = prefsMain.getBoolean(AppConstant.IS_PUNCHED_IN, false);
//        if (isPunchedIn) {
//            attendenceImageButton.setImageResource(R.drawable.checkout);
//        } else {
//            attendenceImageButton.setImageResource(R.drawable.check_in);
//        }

    }


    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        return sdf.format(new Date());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }

    public void getPunchStatusFromServer() {
        progressBar.setMessage("Please wait...");
        progressBar.setCancelable(false);
        progressBar.setCanceledOnTouchOutside(false);
        progressBar.show();

        String token = prefsMain.getString(AppConstant.API_TOKEN, "");
        WebRequest mWebRequest = new WebRequest(getActivity());
        Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.getCheckInDetails(AppConstant.CONTENT_TYPE,
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
                            // Punch out
                            JSONObject obj = responseJSON.getJSONObject("data");
                            // JSONObject obj = data.getJSONObject(0);

                            String checkin_status = obj.getString("checkin_status");
                            String total_duration = obj.getString("total_duration");
                            String earliestCheckInTime = obj.getString("earliestCheckInTime");
                            String latestCheckOutTime = obj.getString("latestCheckOutTime");

                            updatePunchStatus(checkin_status, earliestCheckInTime, latestCheckOutTime, total_duration);

                        } else {
                            if (getActivity() != null && !getActivity().isFinishing()) {
                                Utility.getInstance().handleApiError(responseJSON.getString("message"), getActivity(), prefsEditor);
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
    }
}
