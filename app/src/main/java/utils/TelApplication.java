package utils;

import android.app.Application;

public class TelApplication extends Application  {
    @Override
    public void onCreate() {
        super.onCreate();
        SingletonHelperGlobal.getInstance().init(this);
    }

}
