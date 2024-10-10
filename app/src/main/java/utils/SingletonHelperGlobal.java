package utils;

import android.content.Context;

import database.MySQLiteHelper;

public class SingletonHelperGlobal {
    public static MySQLiteHelper mDBDbHelper = null;
    public static MySharedPreference mySharedPreferenceHelper = null;
    private static final SingletonHelperGlobal ourInstance = new SingletonHelperGlobal();

    public static SingletonHelperGlobal getInstance() {
        return ourInstance;
    }

    private SingletonHelperGlobal() {
    }

    public void init(Context context){
        mySharedPreferenceHelper = new MySharedPreference(context,AppConstant.SHARED_PREFERENCE_KEY,
                AppConstant.SHARED_PREFERENCE_FILE_NAME);
        mDBDbHelper = new MySQLiteHelper(context);
    }


}