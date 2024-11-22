package utils;

import android.content.Context;

public class AppConstant {
    // App constant
    public static String APP_NAME = "Employee Tracker";
    public static String LOG_DIRECTORY_NAME = "Employee Tracker Log";
    public static String SHARED_PREFERENCE_FILE_NAME = "employee_tracker_pref";
    public static String SHARED_PREFERENCE_KEY = "3838383838";

    public static String CONTENT_TYPE = "application/json";

    // Database Constants
    public static final String DATABASE_NAME = "EmployeeTrackerDB";
    public static final String CREATE_LOCAL_RECORDS_TABLE = "CREATE TABLE IF NOT EXISTS LocalLocationTable" +
            " (" + "row_id INTEGER PRIMARY KEY AUTOINCREMENT," + "company_id INTEGER,"
            + "emp_id INTEGER," + "latitude TEXT," +"longitude TEXT," + "battery_status TEXT," +
            "gps_status TEXT," +  "internet_status TEXT," + "date_time DATETIME" + ")";



    //constants for shared preference
    public static final String USERID = "id";
    public static final String COMPANY_ID = "company_id";
    public static final String API_TOKEN = "api_token";
    public static final String EMPLOYEE_ID = "employee_id";
    public static final String EMPLOYEE_NAME = "employeeName";
    public static final String USER_IMAGE = "image";
    public static final String IS_USER_LOGGED_IN = "isUserLoggedIn";
    public static final String IS_PUNCHED_IN = "isPunchedIn";
    public static final String IS_MANUALY_SERVICE_STOP = "isManuallyServiceStop";

    public static final String EMPLOYEE_EMAIL_ID = "emailId";

}
