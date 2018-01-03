package com.example.mykotlindemo.application5

import android.app.Application

/**
 * Created by zhangxutong on 2018/1/2.
 */
class App : Application() {
    companion object {
        private lateinit var ins: App
        fun getInstance(): App = ins
    }

    override fun onCreate() {
        super.onCreate()
        ins = this
    }
}