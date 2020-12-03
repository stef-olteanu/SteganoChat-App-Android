package com.stefanolteanu.steganochatapp.Utils

import android.content.Context
import android.provider.Settings

class Util {
    companion object {
        fun getDeviceId() : String {
            val context = GlobalApplication.getApplicationContext()
            val androidId: String = Settings.Secure.getString(
                context.contentResolver,
                Settings.Secure.ANDROID_ID
            )
            return androidId
        }
    }
}