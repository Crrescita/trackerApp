package utils.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.abpal.employeetracker.activity.DashboardActivity;
import com.abpal.tel.BuildConfig;
import com.abpal.tel.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.securepreferences.SecurePreferences;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

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
import database.MySQLiteHelper;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private MySQLiteHelper db;
    private String currentLatLngStr = "";
    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();

        db = SingletonHelperGlobal.getInstance().mDBDbHelper;

        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            sendNotification(remoteMessage.getData());
        }
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }


    @Override
    public void onNewToken(@NonNull String token) {
        //
    }


    private void sendNotification(Map<String, String> data) {
        try {
            String title = data.get("title");
            String body = data.get("body");


            int notificationType = 0;
            int notificationId = 3838; //default
            String requestFCMToken = "";
            String emp_id = "";
            try {
                notificationType = Integer.parseInt(data.get("notificationType"));
                notificationId = Integer.parseInt(data.get("notificationId"));
                requestFCMToken = data.get("requestFcmToken");
                emp_id = data.get("emp_id");
            } catch (Exception e) {
                e.printStackTrace();
            }


            switch (notificationType) {
                case 1:
                    String latitute = "";
                    String longitute = "";
                    String batteryStatus = "";
                    String internetStatus = "";
                    String gps_status = "";
                    try {
                      latitute =prefsMain.getString("latituteFused","");
                      longitute =prefsMain.getString("longitudeFused","");
                      batteryStatus = getBatteryLevel()+"%";
                      internetStatus = isInternetConnected()+"";


                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                        if (isGPSEnabled) {
                            gps_status = "1";
                        }else{
                            gps_status ="0";
                        }


                        JSONObject obj = new JSONObject();
                        obj.put("emp_id", emp_id);
                        obj.put("fcm_token", requestFCMToken);
                        obj.put("latitude", latitute);
                        obj.put("longitude", longitute);
                        obj.put("battery_status", batteryStatus);
                        obj.put("gps_status", gps_status);
                        obj.put("internet_status", internetStatus);
                        obj.put("motion", "NA");

                        setCoordinateAPI(obj);



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
            }

            Intent intent = new Intent(this, DashboardActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            Bundle bundle = new Bundle();
            bundle.putInt("redirect_flag", notificationType);
            intent.putExtras(bundle);
            intent.putExtra("redirect_flag", notificationType);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, notificationType /* Request code */, intent,
                    PendingIntent.FLAG_MUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            String channelId = "Tel All";
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, channelId)
                            .setContentTitle(title)
                            .setSmallIcon(R.drawable.tel_logo)
                            .setContentText(body)
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId,
                        "TEL",
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(notificationId, notificationBuilder.build());


        } catch (Exception e) {
            e.printStackTrace();
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


    public void setCoordinateAPI(JSONObject jsonObject) {
        String token = prefsMain.getString(AppConstant.API_TOKEN, "");
        WebRequest mWebRequest = new WebRequest(this);
        Call<ResponseBody> user1 = mWebRequest.m_ApiInterface.sendLiveLocation(
                AppConstant.CONTENT_TYPE,
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

                            Log.e("API FCM", "Success: ");
                        } else {
                            Log.e("API FCM", "Failure");
                        }
                    } catch (Exception e) {
                        Log.e("API FCM exception", e.getLocalizedMessage());
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