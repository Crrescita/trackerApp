package utils.service;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.abpal.tel.BuildConfig;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.securepreferences.SecurePreferences;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import modelResponse.ModelError;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofitAPI.WebRequest;
import utils.AppConstant;
import utils.SingletonHelperGlobal;
import utils.recevier.RestartBackgroundService;

public class LocationService extends Service {
    private int counter = 0;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private static final String TAG = "LocationService";
    private SecurePreferences prefsMain = null;
    private SecurePreferences.Editor prefsEditor = null;
    private Timer timer = null;
    private TimerTask timerTask = null;
    private TimerTask APICallTimerTask;
    private Timer APICallTimer;



    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            createNotificationChannel();
        } else {
            startForeground(1, new Notification());
        }
        requestLocationUpdates();
        prefsMain = SingletonHelperGlobal.mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        String NOTIFICATION_CHANNEL_ID = "com.tel.backgroundservice";
        String channelName = "TEL Gps Service";
        NotificationChannel chan = new NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
        );
        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (manager != null) {
            manager.createNotificationChannel(chan);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setContentTitle("GPS is running: " + counter)
                    .setPriority(NotificationManager.IMPORTANCE_HIGH)
                    .setCategory(Notification.CATEGORY_LOCATION_SHARING)
                    .build();
            startForeground(498797897, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        startSecondTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "Service destroyed");
        stopTimerTask();
        stopForeground(true);
        if(!prefsMain.getBoolean(AppConstant.IS_MANUALY_SERVICE_STOP, false)){
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("restartservice");
            broadcastIntent.setClass(this, RestartBackgroundService.class);
            this.sendBroadcast(broadcastIntent);
        }

    }




    private void insertData(){
        // Check if GPS is enabled
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        ContentValues values = new ContentValues();
        if (isGPSEnabled) {
            Log.e(TAG, "GPS is enabled."+"latitude = "+latitude+" longitute = "+longitude);
            // Add key-value pairs for each column
            values.put("latitude", latitude+""); // Example latitude
            values.put("longitude", longitude+""); // Example longitude
        } else {
            latitude = 0.0;
            longitude = 0.0;
            Log.e(TAG, "GPS is disabled."+"latitude = "+latitude+" longitute = "+longitude);
            values.put("latitude", latitude+""); // Example latitude
            values.put("longitude", longitude+""); // Example longitude
        }

        values.put("company_id", prefsMain.getInt(AppConstant.COMPANY_ID, 1)); // Example company ID
        values.put("emp_id", prefsMain.getInt(AppConstant.USERID, 2));     // Example employee ID
        values.put("battery_status", getBatteryLevel()+"%"); // Example battery status
        values.put("gps_status", "1");  // Example GPS status
        values.put("internet_status", isInternetConnected()+""); // Example internet status date-time
        values.put("date_time", getDateTimeForFESubmission()); // Example record creation date-time

        // Insert data into the table
        int result = SingletonHelperGlobal.mDBDbHelper.insertData(values, "LocalLocationTable");

        // Check if the data was inserted successfully
        if (result == 1) {
            // Insert successful
            Log.e("DB_INSERT", "Data inserted successfully");
        } else {
            // Insert failed
            Log.e("DB_INSERT", "Data insert failed");
        }
    }






    @SuppressLint("Range")
    public void perpareDataForAPI() {
        if (prefsMain.getBoolean(AppConstant.IS_USER_LOGGED_IN, false)) {
            if (prefsMain.getBoolean(AppConstant.IS_PUNCHED_IN, false)) {
                if(isInternetConnected()==1){
                    Cursor cursor = SingletonHelperGlobal.mDBDbHelper.gettabledata("LocalLocationTable");
                    if (cursor != null && cursor.moveToFirst()) {
                        do {
                            try {
                                JSONObject obj = new JSONObject();

                                // Assuming you have columns named as below in your table
                                int rowId = cursor.getInt(cursor.getColumnIndex("row_id"));
                                int companyId = cursor.getInt(cursor.getColumnIndex("company_id"));
                                int empId = cursor.getInt(cursor.getColumnIndex("emp_id"));
                                String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                                String longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                                String batteryStatus = cursor.getString(cursor.getColumnIndex("battery_status"));
                                String gpsStatus = cursor.getString(cursor.getColumnIndex("gps_status"));
                                String internetStatus = cursor.getString(cursor.getColumnIndex("internet_status"));
                                String dateTimeMobile = cursor.getString(cursor.getColumnIndex("date_time"));

                                // Populate JSON object
                                obj.put("company_id", companyId);
                                obj.put("emp_id", empId);
                                obj.put("latitude", latitude);
                                obj.put("longitude", longitude);
                                obj.put("battery_status", batteryStatus);
                                obj.put("gps_status", gpsStatus);
                                obj.put("internet_status", internetStatus);
                                obj.put("motion", "NA");
                                obj.put("datetime_mobile", dateTimeMobile);
                                obj.put("row_id", rowId);

                                setCoordinateAPI(obj); // Send data to API
                                // Check user login and punch-in status before sending data to API

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } while (cursor.moveToNext());

                        cursor.close(); // Close cursor after processing
                    } else {
                        Log.e("API", "No data found in LocalLocationTable");
                    }
                }

            } else {
                Log.e("API", "User punched out");
            }
        }

    }


    public void deleteRowFromLocalLocationTable(int rowId) {
        SQLiteDatabase db = SingletonHelperGlobal.mDBDbHelper.getWritableDatabase();
        db.delete("LocalLocationTable", "row_id = ?", new String[]{String.valueOf(rowId)});
        db.close();
    }



    public void startSecondTimer() {
        APICallTimer = new Timer();
        APICallTimerTask = new TimerTask() {
            @Override
            public void run() {
                // Your second timer's logic here
                Log.e(TAG, "Second timer running");
                perpareDataForAPI();

            }
        };
        APICallTimer.schedule(APICallTimerTask, 0, 60000); // Example: run every 60 seconds
    }
    public void startTimer() {
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                if (prefsMain.getBoolean(AppConstant.IS_USER_LOGGED_IN, false)) {
                    if (prefsMain.getBoolean(AppConstant.IS_PUNCHED_IN, false)) {
                        insertData();
                    } else {
                        Log.e("API", "User punched out");
                    }
                }
            }
        };
        timer.schedule(timerTask, 0, 55000); // Example: run every 10 seconds
    }

    @SuppressLint("MissingPermission")
    private void requestLocationUpdates() {
        LocationRequest request = LocationRequest.create()
                .setInterval(50000)
                .setFastestInterval(50000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);

        int permission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        Log.e("API","HI running location update");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();

                        prefsEditor.putString("latituteFused", Double.toString(latitude));
                        prefsEditor.putString("longitudeFused", Double.toString(longitude));
                        prefsEditor.commit();

                        Log.e("Location Service", "Location update: " + location);
                    }else{
                        Log.e("API","HI running location update else part");
                    }
                }
            }, null);
        }
    }

    public void stopTimerTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }

        if(APICallTimer!=null){
            APICallTimer.cancel();
            APICallTimer = null;
        }
    }


    public String getDateTimeForFESubmission() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-m-d H:i:s");
        Date date = new Date();
        return dateFormat.format(date);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }




    private int isInternetConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isInternetConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        if(isInternetConnected){
            return 1;
        }else {
            return 0;
        }
    }


    public int getBatteryLevel() {
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = registerReceiver(null, intentFilter);

        // Battery level (0 to 100)
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        // Calculate the battery percentage
        int batteryPct = (int) ((level / (float) scale) * 100);
        return batteryPct;
    }

    public void setCoordinateAPI(JSONObject jsonObject) {
        String token = prefsMain.getString(AppConstant.API_TOKEN, "");
        WebRequest mWebRequest = new WebRequest(this);
        Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.setCoordinate(
                AppConstant.CONTENT_TYPE,
                "Bearer " + token,
                jsonObject.toString()
        );
        user1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.errorBody() != null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    try {
                        ModelError error = (ModelError) retrofit.responseBodyConverter(ModelError.class, new java.lang.annotation.Annotation[0]).convert(response.errorBody());
                        // Utility.getInstance().showDialog(error.getMsg(), LocationService.this);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        String responseBodyString = response.body().string();
                        JSONObject responseJSON = new JSONObject(responseBodyString);
                        if (responseJSON.getBoolean("status")) {
                            String timers = responseJSON.getString("timer");
                            String row_id = responseJSON.getString("row_id");
                            prefsEditor.putString("timer_delay", timers);
                            prefsEditor.commit();
                            deleteRowFromLocalLocationTable(Integer.parseInt(row_id));
                            Log.e("API", "Success: " + timers);
                        } else {
                            Log.e("API", "Failure");
                        }
                    } catch (Exception e) {
                        Log.e("API exception", e.getLocalizedMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("API failure", t.getLocalizedMessage());
            }
        });
    }
}
