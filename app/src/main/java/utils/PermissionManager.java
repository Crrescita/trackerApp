package utils;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;

import com.securepreferences.SecurePreferences;


public class PermissionManager {

    private Context context;
    private SecurePreferences prefsMain;
    private SecurePreferences.Editor prefsEditor;

    public static final int NOTIFICATION_PERMISSION_REQUEST_CODE=100;
    public static final int CAMERA_REQUEST_REQUEST_CODE=110;
    public static final int ACCESS_FINE_LOCATION_REQUEST_CODE=120;
    public static final int ACCESS_COARSE_LOCATION_REQUEST_CODE=130;
    public static final int ACCESS_READ_EXTERNAL_STORAGE_CODE=140;

    public PermissionManager(Context context){
        this.context = context;
        prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper;
        prefsEditor = prefsMain.edit();
    }

    public  boolean shouldAskPermission() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
    }

    private  boolean shouldAskPermission(Context context, String permission){
        if (shouldAskPermission()) {
            int permissionResult = ActivityCompat.checkSelfPermission(context, permission);
            if (permissionResult != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }


    public void checkPermission(Context context, String permission, PermissionAskListener listener,int requestcode){
        if (shouldAskPermission(context, permission)){
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,permission)) {
                listener.onPermissionPreviouslyDenied();
            } else {
                switch (requestcode){
                    case NOTIFICATION_PERMISSION_REQUEST_CODE:
                        if (OpusConnectSingleTon.getInstance().isFirstTimeAskingNotificationPermission(prefsMain)) {
                            OpusConnectSingleTon.getInstance().setFirstTimeAskingNotificationPermission(prefsEditor,false);
                            //sessionManager.firstTimeAskingPermission(permission, false);
                            listener.onNeedPermission();
                        } else {
                            listener.onPermissionPreviouslyDeniedWithNeverAskAgain();
                        }
                        break;

                    case CAMERA_REQUEST_REQUEST_CODE:
                        if (OpusConnectSingleTon.getInstance().isFirstTimeAskingCameraPermission(prefsMain)) {
                            OpusConnectSingleTon.getInstance().setFirstTimeAskingCameraPermission(prefsEditor,false);
                            //sessionManager.firstTimeAskingPermission(permission, false);
                            listener.onNeedPermission();
                        } else {
                            listener.onPermissionPreviouslyDeniedWithNeverAskAgain();
                        }
                        break;

                    case ACCESS_FINE_LOCATION_REQUEST_CODE:

                        if (OpusConnectSingleTon.getInstance().isFirstTimeAskingFinePermission(prefsMain)) {
                            OpusConnectSingleTon.getInstance().setFirstTimeAskingFinePermission(prefsEditor,false);
                            //sessionManager.firstTimeAskingPermission(permission, false);
                            listener.onNeedPermission();
                        } else {
                            listener.onPermissionPreviouslyDeniedWithNeverAskAgain();
                        }

                        break;

                    case ACCESS_COARSE_LOCATION_REQUEST_CODE:
                        if (OpusConnectSingleTon.getInstance().isFirstTimeAskingCoarsePermission(prefsMain)) {
                            OpusConnectSingleTon.getInstance().setFirstTimeAskingCoarsePermission(prefsEditor,false);
                            //sessionManager.firstTimeAskingPermission(permission, false);
                            listener.onNeedPermission();
                        } else {
                            listener.onPermissionPreviouslyDeniedWithNeverAskAgain();
                        }
                        break;

                    case ACCESS_READ_EXTERNAL_STORAGE_CODE:
                        if (OpusConnectSingleTon.getInstance().isFirstTimeExternalCoarsePermission(prefsMain)) {
                            OpusConnectSingleTon.getInstance().setFirstTimeAskingExternalPermission(prefsEditor,false);
                            //sessionManager.firstTimeAskingPermission(permission, false);
                            listener.onNeedPermission();
                        } else {
                            listener.onPermissionPreviouslyDeniedWithNeverAskAgain();
                        }
                        break;


                }

            }
        } else {
            listener.onPermissionGranted();
        }
    }


    public interface PermissionAskListener {

        void onNeedPermission();

        void onPermissionPreviouslyDenied();

        void onPermissionPreviouslyDeniedWithNeverAskAgain();

        void onPermissionGranted();
    }

}