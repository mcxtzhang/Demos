package com.example.mykotlindemo.test

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

/**
 * Created by zhangxutong on 2017/12/29.
 */
class GcReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context,"gc",Toast.LENGTH_SHORT).show()
        Runtime.getRuntime().gc()
    }
}