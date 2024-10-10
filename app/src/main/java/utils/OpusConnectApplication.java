package utils;

import android.app.Application;
import androidx.annotation.NonNull;

public class OpusConnectApplication extends Application  {
    @Override
    public void onCreate() {
        super.onCreate();
        SingletonHelperGlobal.getInstance().init(this);
    }

}
