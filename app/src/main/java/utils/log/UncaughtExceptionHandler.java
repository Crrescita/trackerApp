package utils.log;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by parikshit tomar on 20-02-2019.
 */

public class UncaughtExceptionHandler {

    private static Thread.UncaughtExceptionHandler androidDefaultUEH;
    static private Context mContext;
    static private String model;
    static private int versionCode;

    private UncaughtExceptionHandler() {
    }

    public static void init(Context context) {
        mContext = context;
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            model = Build.MODEL;
            versionCode = info.versionCode;
            if (!model.startsWith(Build.MANUFACTURER))
                model = Build.MANUFACTURER + " " + model;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startLogging() throws NullPointerException {
        if (mContext == null) {
            throw new NullPointerException("Context can not  be null. Please call init and provide not null context of application.");
        }
        androidDefaultUEH = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(handler);
    }

    private static Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
        public void uncaughtException(Thread thread, Throwable ex) {

            extractLogToFile(ex);
//            androidDefaultUEH.uncaughtException(thread, ex);
        }
    };


    private static void extractLogToFile(Throwable ex) {
        final long timeStamp = System.currentTimeMillis();
        try {


            // For Android 4.0 and earlier, you will get all app's log output, so filter it to
            // mostly limit it to your app's output.  In later versions, the filtering isn't needed.
//            String cmd = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) ?
//                    "logcat -d -v time in.ecomexpress.sathi:v dalvikvm:v System.err:v *:s" :
//                    "logcat -d -v time";
            String cmd = "logcat -d -v time";
            // get input stream
            Process process = Runtime.getRuntime().exec(cmd);
            InputStreamReader reader = new InputStreamReader(process.getInputStream());


            // write output stream
            StringBuilder builder = new StringBuilder();
            builder.append("Android version: " + Build.VERSION.SDK_INT + "\n");
            builder.append("Device: " + model + "\n");
            builder.append("App version: " + versionCode + "\n");
            BufferedReader r = new BufferedReader(reader);
            StringBuilder total = new StringBuilder();
            for (String line; (line = r.readLine()) != null; ) {
                total.append(line).append('\n');
            }
            total.append(builder.toString());
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
                    writeCrashes(timeStamp, new Exception(ex));
//                }
//            }).start();
        } catch (Exception e) {
            new Thread(() -> writeCrashes(timeStamp, e)).start();
        }
        System.exit(1);
    }

    private static void writeCrashes(long timeStamp, Exception e) {
        AppLogJsonProcessor.appendErrorJSONObject(AppLogJsonProcessor.LogType.ERROR,
                getExceptionAsString(e), timeStamp,"","","");
    }

    private static void writeCrashes(long timeStamp, String e) {
        AppLogJsonProcessor.appendErrorJSONObject(AppLogJsonProcessor.LogType.ERROR,
                e, timeStamp,"","","");
    }

    private static double getCurrentLatitude() {
        return 0;
    }

    private static double getCurrentLongitude() {
        return 0;
    }

    // Below method is used for user specific
    private static String getCode() {
        return "User id";
    }


    private static String getExceptionAsString(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}