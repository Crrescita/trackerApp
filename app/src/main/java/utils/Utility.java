package utils;

import static android.content.Context.ACTIVITY_SERVICE;
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Environment;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.StatFs;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.abpal.employeetracker.activity.LoginActivity;
import com.abpal.tel.BuildConfig;
import com.abpal.tel.R;
import com.securepreferences.SecurePreferences;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import database.MySQLiteHelper;
import encryption.AESEncreption;
import modelResponse.DropDownValueForPhotoModel;
import modelResponse.DynamicPhotoModel;
import modelResponse.GSTAndDropDownPhotoModel;
import modelResponse.MultiCallModel;
import modelResponse.UnableToVerifyModel;
import modelResponse.notification.NotificationList;

public class Utility {
    private static final Utility utility = new Utility();

    public static Utility getInstance() {
        return utility;
    }

    private Utility() {

    }


    public void handleApiError(String errorMessage,Context context,SecurePreferences.Editor prefsEditor) {
        if (errorMessage.contains("Forbidden: Invalid or expired token")|| errorMessage.contains("Invalid or expired token")) {
            showSessionExpiredDialog(context,prefsEditor);
        } else {
            showDialog(context, errorMessage);
        }
    }

    private void showSessionExpiredDialog(Context context,SecurePreferences.Editor prefsEditor) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Session Expired");
        builder.setMessage("Your session has expired. Please log in again.");
        builder.setCancelable(false);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logoutUser(context,prefsEditor);
            }
        });
        builder.show();
    }

    private void showDialog(Context context, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage(message);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private void logoutUser(Context context, SecurePreferences.Editor prefsEditor) {
        // Clear shared preferences or any stored user data
        prefsEditor.clear().apply();

        // Redirect to the login screen
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    // Alert Dialog with Ok Button
    public void showDialogss(String msg,Context context) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setTitle("Message");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        alert.getWindow().getAttributes();
        TextView textView = (TextView) alert.findViewById(android.R.id.message);
        textView.setTextSize(13);
        textView.setTextColor(context.getResources().getColor(R.color.text_checks));
    }


    // Alert Dialog with Ok Button
    public void showDialogErrorWithAlertActionButton(String msg,DialogInterface.OnClickListener ok,Context context) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setTitle("Message");
        builder.setMessage(msg);
        builder.setPositiveButton("OK", ok);

        AlertDialog alert = builder.create();
        alert.show();
        alert.getWindow().getAttributes();
        TextView textView = (TextView) alert.findViewById(android.R.id.message);
        textView.setTextSize(13);
        textView.setTextColor(context.getResources().getColor(R.color.text_checks));
    }

    public void showErrorDialog(String msg,Context context) {
        AlertDialog.Builder builder =
                new AlertDialog.Builder(context);
        builder.setTitle("Error");
        builder.setMessage(msg);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
        alert.getWindow().getAttributes();
        TextView textView = (TextView) alert.findViewById(android.R.id.message);
        textView.setTextSize(13);
        textView.setTextColor(context.getResources().getColor(R.color.red));
    }

    // Yes No Alert Dialog
    public void YesNoAlert(Context context, String Title, String msgString, DialogInterface.OnClickListener yesListener,
                           DialogInterface.OnClickListener noListener) {
        AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(context);
        alertDialog2.setTitle(Title);
        alertDialog2.setMessage(Html.fromHtml(msgString));
        alertDialog2.setPositiveButton("Yes", yesListener);
        alertDialog2.setCancelable(false);
        alertDialog2.setNegativeButton("No", noListener);

        AlertDialog alert = alertDialog2.create();
        alert.show();
        alert.getWindow().getAttributes();
        TextView textView = (TextView) alert.findViewById(android.R.id.message);
        textView.setTextSize(13);
    }


    public void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            //This is Catch Block
        }
    }


    public String getDateTimeForFESubmission() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-m-d H:i:s");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getDate() {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }


    public String getTodayDateOnly() {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS")
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getDatePhoto() {
        //SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSSS")
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yy HH:mm");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public boolean isGPSEnabled(Context mContext) {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    public boolean permission(Activity activity, Context context) {
        boolean result = true;
        int requestCode = 100;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            String[] permissions = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.CAMERA,
                    Manifest.permission.POST_NOTIFICATIONS
            };

            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    result = false;
                    // Request permissions using ActivityCompat.requestPermissions
                    ActivityCompat.requestPermissions(activity, permissions, requestCode);
                    break; // Request one at a time
                }
            }
        }  else {
            String[] permissions = {
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };

            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    result = false;
                    // Request permissions using ActivityCompat.requestPermissions
                    ActivityCompat.requestPermissions(activity, permissions, requestCode);
                    break; // Request one at a time
                }
            }
        }

        return result;
    }



    public boolean checkLocationPermission(Activity activity,Context context) {
        boolean result = true;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            result = false;
            ActivityCompat.requestPermissions(activity,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    100);
        }
        return result;
    }

    public InputFilter EMOJI_FILTER = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            //Log.e("Char Sequence","Smiley Filters")
            Pattern pattern = Pattern.compile("[^a-zA-Z0-9:,'&?@*!/.%_ -]");
            for (int index = start; index < end; index++) {
                int type = Character.getType(source.charAt(index));
                Matcher matcher = pattern.matcher("" + source);
                if (type == Character.SURROGATE || matcher.find()) {
                    return "";
                }
            }
            return null;
        }
    };

    public InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int i = start; i < end; ++i) {
                if (!Pattern.compile("[1234567890]*").matcher(String.valueOf(source.charAt(i))).matches()) {
                    return "";
                }
            }

            return null;
        }
    };

    public String getLocation(Activity activity,Context context) {
        double myLat = 0;
        double myLong = 0;
        if (permission(activity,context)) {
            if (isGPSEnabled(activity)) {
                LocationDetector myloc = new LocationDetector(activity);
                if (myloc.canGetLocation) {
                    myLat = myloc.getLatitude();
                    myLong = myloc.getLongitude();
                    //Log.v("get location values", Double.toString(myLat) + "     " + Double.toString(myLong))
                }
            }
        }

        return myLat + "," + myLong;
    }

    public String getLocationByFirebaseContext(Context activity) {
        double myLat = 0;
        double myLong = 0;
            if (isGPSEnabled(activity)) {
                LocationDetector myloc = new LocationDetector(activity);
                if (myloc.canGetLocation) {
                    myLat = myloc.getLatitude();
                    myLong = myloc.getLongitude();
                    //Log.v("get location values", Double.toString(myLat) + "     " + Double.toString(myLong))
                }
            }

        return myLat + "," + myLong;
    }



    public String getKey(SharedPreferences prefs1) {
        return prefs1.getString("password", "");
    }
















    public void showToastCenter(String toast_msg,Context context) {
        Toast toast = Toast.makeText(context, toast_msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public String getIPAddress() {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr)
                        boolean isIPv4 = sAddr.indexOf(':') < 0;
                        if (isIPv4)
                            return sAddr;
                        /*if (useIPv4) {
                            if (isIPv4)
                                return sAddr
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase()
                            }
                        }*/
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

}
