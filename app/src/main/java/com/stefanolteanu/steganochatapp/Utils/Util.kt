package com.stefanolteanu.steganochatapp.Utils

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import android.telephony.TelephonyManager

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

        fun getDevicePhoneNumber() : String {
            val sharedPreferences = GlobalApplication.getApplicationContext().getSharedPreferences("device_info",Context.MODE_PRIVATE)
            return sharedPreferences.getString("phoneNumber","")!!
        }

        fun saveDevicePhoneNumber(phoneNumber : String) {
            val sharedPreferences = GlobalApplication.getApplicationContext().getSharedPreferences("device_info",Context.MODE_PRIVATE)
            val sharedPreferencesEditor = sharedPreferences.edit()
            sharedPreferencesEditor.putString("phoneNumber",phoneNumber)
            sharedPreferencesEditor.apply()
        }
    }
}