package utils.service;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import com.abpal.employeetracker.activity.DashboardActivity;
import com.abpal.tel.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.securepreferences.SecurePreferences;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;

import utils.SingletonHelperGlobal;
import database.MySQLiteHelper;
import modelResponse.ChecksDownloaded;
import modelResponse.ModelError;
import modelResponse.feassigned_check_response.CheckField;
import modelResponse.feassigned_check_response.Datum;
import modelResponse.feassigned_check_response.DisabledChecks;
import modelResponse.feassigned_check_response.FEAssignedChecksResponse;
import modelResponse.feassigned_check_response.StatedCheckField;
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
import utils.MasterDataCallbackListner;
import utils.OpusConnectSingleTon;
import utils.Utility;

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
            try {
                notificationType = Integer.parseInt(data.get("notificationType"));
                notificationId = Integer.parseInt(data.get("notificationId"));
            } catch (Exception e) {
                e.printStackTrace();
            }


            switch (notificationType) {
                case 1:
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

}