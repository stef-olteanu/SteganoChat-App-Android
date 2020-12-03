package com.stefanolteanu.steganochatapp.Utils

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build

class GlobalApplication: Application() {
    companion object {
        private lateinit var instance: GlobalApplication
        var context: Context? = null

        fun getApplicationContext(): Context {
            return context!!
        }

    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        instance = this
        System.setProperty("http.keepAliveDuration", (30 * 60 * 1000).toString())
    }
}