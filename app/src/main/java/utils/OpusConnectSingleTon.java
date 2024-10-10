package utils;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.database.Cursor;
import com.securepreferences.SecurePreferences;
import java.util.ArrayList;
import database.MySQLiteHelper;
import modelResponse.GroupHelperModelClass;
import modelResponse.way_point_sequence.own_array.MyRequestedSequenceData;
import utils.service.UpdateFirebaseNotification;

public class OpusConnectSingleTon {

    private static final OpusConnectSingleTon ourInstance = new OpusConnectSingleTon();

    public static OpusConnectSingleTon getInstance() {
        return ourInstance;
    }

    private OpusConnectSingleTon() {
    }

    public void setFirstTimeAskingNotificationPermission(SharedPreferences.Editor prefsEditor,boolean isFirstTime) {
        prefsEditor.putBoolean("isPermissionAskingFirstTime", isFirstTime);
        prefsEditor.commit();
    }

    public boolean isFirstTimeAskingNotificationPermission(SharedPreferences prefs1) {
        return prefs1.getBoolean("isPermissionAskingFirstTime", true);
    }


    public void setFirstTimeAskingCameraPermission(SharedPreferences.Editor prefsEditor,boolean isFirstTime) {
        prefsEditor.putBoolean("camera", isFirstTime);
        prefsEditor.commit();
    }

    public boolean isFirstTimeAskingCameraPermission(SharedPreferences prefs1) {
        return prefs1.getBoolean("camera", true);
    }


    public void setFirstTimeAskingFinePermission(SharedPreferences.Editor prefsEditor,boolean isFirstTime) {
        prefsEditor.putBoolean("fine", isFirstTime);
        prefsEditor.commit();
    }

    public boolean isFirstTimeAskingFinePermission(SharedPreferences prefs1) {
        return prefs1.getBoolean("fine", true);
    }


    public void setFirstTimeAskingCoarsePermission(SharedPreferences.Editor prefsEditor,boolean isFirstTime) {
        prefsEditor.putBoolean("coarse", isFirstTime);
        prefsEditor.commit();
    }

    public boolean isFirstTimeAskingCoarsePermission(SharedPreferences prefs1) {
        return prefs1.getBoolean("coarse", true);
    }


    public void setFirstTimeAskingExternalPermission(SharedPreferences.Editor prefsEditor,boolean isFirstTime) {
        prefsEditor.putBoolean("external", isFirstTime);
        prefsEditor.commit();
    }

    public boolean isFirstTimeExternalCoarsePermission(SharedPreferences prefs1) {
        return prefs1.getBoolean("external", true);
    }

}
