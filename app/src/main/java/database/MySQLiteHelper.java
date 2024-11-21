/**
 * @author ${Kapil Rathee}
 * @date ${13-Sep-2018}
 * @date ${Last Modified}
 * # Database helper class.
 */
package database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.securepreferences.SecurePreferences;
import utils.AppConstant;
import utils.SingletonHelperGlobal;

@SuppressLint("UseSparseArrays")
public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    public MySQLiteHelper(Context context) {
        super(context, AppConstant.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop other tables data
        dropAllTable(db);

        //create all table
        createAllTable(db);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createAllTable(db);
    }

    private void dropAllTable(SQLiteDatabase db){

        SecurePreferences.Editor prefsMain = SingletonHelperGlobal.getInstance().mySharedPreferenceHelper.edit();
        prefsMain.putString("token", "");
        prefsMain.putBoolean("agreed", false);
        prefsMain.commit();

        db.execSQL("DROP TABLE IF EXISTS LocalLocationTable");
    }

    private void createAllTable(SQLiteDatabase db){
        db.execSQL(AppConstant.CREATE_LOCAL_RECORDS_TABLE);
    }



    public void cleardata() {
        SQLiteDatabase ddb = this.getWritableDatabase();
        ddb.delete("LocalLocationTable", null, null);
        ddb.close();
    }


    public int insertData(ContentValues values, String tableName) {
        int response = 0;
        try {
            //Log.e("Insert NotificationData in :"+tableName, values.toString())
            SQLiteDatabase db = this.getWritableDatabase();
            db.insert(tableName, null, values);
           // db.close();
            response = 1;
        } catch (Exception e) {
            //This is Catch Block
        }
        return response;
    }

        public Cursor gettabledata(String table_name) {
            SQLiteDatabase ddb = this.getWritableDatabase();
            String countQuery = "SELECT * FROM " + table_name;

            Cursor cursor = ddb.rawQuery(countQuery, null);
            int count = cursor.getCount();
            //Log.e("Total gettabledata ",table_name+": "+count)
         //   ddb.close();
            return cursor;
        }

} // End