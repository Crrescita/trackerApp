package utils;

import android.content.Context;

/**
 * Created by Kapil Rathee on 16/10/18.
 */

public class AppConstant {
    // App constant
    public static String APP_NAME = "Employee Tracker";
    public static String LOG_DIRECTORY_NAME = "Employee Tracker Log";
    public static String SHARED_PREFERENCE_FILE_NAME = "employee_tracker_pref";
    public static String SHARED_PREFERENCE_KEY = "3838383838";

    public static String CONTENT_TYPE = "application/json";

    // Database Constants
    public static final String DATABASE_NAME = "EmployeeTrackerDB";
    //public static final String PRIVACY_POLICY_URL = "https://authbridge.com/privacy-policy/";
    // public static final String PRIVACY_POLICY_URL = "https://opus-uat.authbridge.app/opus/privacy_policy_opus_connect.html";
    // public static final String PRIVACY_POLICY_URL = "https://opusconnectapi.authbridge.app/opus_connect_api_v1.1/privacy_policy_opus_connect.html";

    public static final String CREATE_LOCAL_RECORDS_TABLE = "CREATE TABLE IF NOT EXISTS LocalLocationTable" +
            " (" + "row_id INTEGER PRIMARY KEY AUTOINCREMENT," + "company_id INTEGER,"
            + "emp_id INTEGER," + "latitude TEXT," +"longitude TEXT," + "battery_status TEXT," +
            "gps_status TEXT," +  "internet_status TEXT," + "date_time DATETIME" + ")";


    public static final String SELECT_ASSIGNED_CHECKS_LIST_ORDER_INTIAL = "Select * from FEChecks where  fe_check_status IN(1,4) Order By list_order DESC";
    public static final String SELECT_ASSIGNED_CHECKS_ROUTE_ORDER = "Select * from FEChecks where  fe_check_status IN(1,4) Order By route_main ASC";


    public static final String SELECT_COMPLETED_CHECKS = "Select * from FEChecks where  fe_check_status = 2";
    public static final String SELECT_UNVERIFIED_CHECK = "Select * from FEChecks where  fe_check_status = 21";
    public static final String SELECT_UNVERIFIED_COMPLETED = "Select * from FEChecks where  fe_check_status IN(2,21,31,4,11)";
    //public static final String SELECT_SYNCED_CHECKS ="Select * from FEChecks where  fe_check_status = 3"
    public static final String SELECT_COMPLETED_WITHOUT_FOUR = "Select * from FEChecks where  fe_check_status IN(2,21,31,11)";
    public static final String SELECT_EXCEPT_ONE = "SELECT * FROM FEChecks WHERE fe_check_status <> 1;";

    public static final String GET_NOTIFICATION_NOT_VIEWD_LIST = "Select * from Notification WHERE isViewed = 0";


    //constants for shared preference

    public static final String USERNAMEPREF = "username";
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
