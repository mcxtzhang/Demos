package com.example.mykotlindemo.test

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class ToBeKillService : Service() {

    companion object {
        val TAG: String = "ToBeKillService"
    }

    override fun onBind(intent: Intent): IBinder? {
        // TODO: Return the communication channel to the service.
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        Log.w(TAG, "onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.w(TAG, "onDestroy")
    }
}
