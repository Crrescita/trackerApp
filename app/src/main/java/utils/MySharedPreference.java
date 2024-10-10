package utils;
import android.content.Context;
import com.securepreferences.SecurePreferences;

public class MySharedPreference extends SecurePreferences {
    public MySharedPreference(Context context, String password, String sharedPrefFilename) {
        super(context, password, sharedPrefFilename);
    }
}
