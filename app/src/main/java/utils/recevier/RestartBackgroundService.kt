package utils.recevier

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import com.securepreferences.SecurePreferences
import utils.AppConstant
import utils.SingletonHelperGlobal
import utils.service.LocationService

class RestartBackgroundService : BroadcastReceiver() {
    private var prefsMain: SecurePreferences? = null
    private var prefsEditor: SecurePreferences.Editor? = null
    override fun onReceive(context: Context?, intent: Intent?) {
        prefsMain = SingletonHelperGlobal.mySharedPreferenceHelper
        prefsEditor = prefsMain!!.edit()
        //Log.i("Broadcast Listened", "Service tried to stop")
        Toast.makeText(context, "Service restarted", Toast.LENGTH_SHORT).show()

        prefsEditor!!.putBoolean(AppConstant.IS_MANUALY_SERVICE_STOP, false)
        prefsEditor!!.commit()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context!!.startForegroundService(Intent(context, LocationService::class.java))
        } else {
            context!!.startService(Intent(context, LocationService::class.java))
        }
    }
}