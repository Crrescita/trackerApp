package com.auth.icici.util

import android.os.Environment
import android.util.Log
import com.crrescita.tel.BuildConfig
import utils.AppConstant

import java.io.File

class Logger {

    companion object {

        val logger = BuildConfig.BUILD_TYPE.contains("debug")
        fun e(TAG: String, message: String) {
            if (logger)
                Log.e(TAG, message)
        }

        fun i(TAG: String, message: String) {
            if (logger)
                Log.i(TAG, message)
        }

        fun getHiddenDir(filename: String?): String? {
            try {
                val fileDir = File(
                    Environment.getExternalStorageDirectory(),"/" + AppConstant.LOG_DIRECTORY_NAME
                )
                if (!fileDir.exists()) fileDir.mkdirs()
                val file = File(fileDir, filename)
                if (!file.exists()) file.createNewFile()
                return file.path
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        @JvmStatic
        fun d(tag: String, message: String) {
            if (logger)
                Log.e(tag, message)
        }
    }

}