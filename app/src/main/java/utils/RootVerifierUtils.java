package utils;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class RootVerifierUtils {

    private Activity activity;
    private String suAppInformation="";

    public RootVerifierUtils(Activity activity) {
        this.activity = activity;
    }

    public String isDeviceRooted() {
        if (checkRoot()) {
            return "Your device is rooted you can't use this application";
        } else {
            if(su_app()){
                return suAppInformation+" app already installed in your device, Please uninstall the app to use this application";
            }else {
                return "";
            }

        }
    }
    //check root
    private boolean checkRoot() {

        if (suAvailable()) { // Checks if su binary is available
            try {
                boolean check_file_created = false;
                Process process = Runtime.getRuntime().exec("su");
                PrintWriter pw = new PrintWriter(process.getOutputStream(),
                        true);

                // CREATING A DUMMY FILE in / called abc.txt
                pw.println("mount -o remount,rw /");
                pw.println("cd /");
                pw.println("echo \"ABC\" > abc.txt");
                pw.println("exit");
                pw.close();
                process.waitFor();


                if (checkFile()) { // Checks if the file has been successfully
                    check_file_created = true;
                }
                // DELETES THE DUMMY FILE IF PRESENT
                process = Runtime.getRuntime().exec("su");
                pw = new PrintWriter(process.getOutputStream());
                pw.println("cd /");
                pw.println("rm abc.txt");
                pw.println("mount -o ro,remount /");
                pw.println("exit");
                pw.close();
                process.waitFor();
                process.destroy();

                return check_file_created;

            } catch (Exception e) {
                return false;
            }

        } else {
            return false;
        }

    }

    private static boolean suAvailable() {
        boolean flag;
        try {
            Process p = Runtime.getRuntime().exec("su");
            p.destroy();
            flag = true;
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    private static boolean checkFile() throws IOException {
        boolean flag = false;
        try {
            File x = new File("/abc.txt");
            flag = x.exists();

        } catch (SecurityException e) {
            Process p = Runtime.getRuntime().exec("ls /");
            Scanner sc = new Scanner(p.getInputStream());
            String line = null;

            while (sc.hasNextLine()) {
                line = sc.nextLine();
                if (line.contains("abc.txt")) {
                    flag = true;
                    break;
                }
            }
            sc.close();

        }
        return flag;
    }
    // check super user

    private boolean su_app() {
        final String[] packages = {
                "com.topjohnwu.magisk",
                "com.ramdroid.appquarantine",
                "com.zachspong.temprootremovejb",
                "com.thirdparty.superuser",
                "eu.chainfire.supersu",
                "eu.chainfire.supersu.pro",
                "com.koushikdutta.superuser",
                "com.noshufou.android.su",
                "com.dianxinos.superuser",
                "com.kingouser.com",
                "com.mueskor.superuser.su",
                "org.masteraxe.superuser",
                "com.yellowes.su",
                "com.kingroot.kinguser",

                "de.robv.android.xposed.installer",
                "com.kingoapp.apk",
                "com.alephzain.framaroot",
                "com.qihoo.permmgr,",
                "com.shuame.rootgenius",
                "com.mgyun.shua.su",
                "com.baidu.easyroot",
                "com.wzeeroot_4279131",
                "com.smedialink.oneclickroot",
                "com.vanshashu.rootmaster",
                "com.geohot.towelroot",
                "com.knoos.rooev.etyr"
        };
        PackageManager pm = activity.getPackageManager();
        int i, l = packages.length;
        String superuser = null;

        for (i = 0; i < l; i++) {
            try {
                ApplicationInfo info = pm.getApplicationInfo(packages[i], 0);
                PackageInfo info2 = pm.getPackageInfo(packages[i], 0);
                superuser = pm.getApplicationLabel(info).toString() + " "
                        + info2.versionName;
                break;
            } catch (PackageManager.NameNotFoundException e) {
                continue;
            }
        }

        if (superuser != null) {
            suAppInformation= superuser;
            return true;
        } else {
            return su_alternative();
        }

    }

    private boolean su_alternative() {
        String line;
        try {
            Process p = Runtime.getRuntime().exec("su -v");// Superuser version
            InputStreamReader t = new InputStreamReader(p.getInputStream());
            BufferedReader in = new BufferedReader(t);
            line = in.readLine();

            char[] chars = line.toCharArray();
            boolean flag = false;// Check if su -v returns the package name
            // instead of just the version number
            for (char c : chars) {
                if (Character.isLetter(c)) {
                    flag = true;
                }
            }
            if (!flag) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

}
