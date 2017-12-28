package com.example.mykotlindemo.Request

import android.util.Log
import java.net.URL

/**
 * Created by zhangxutong on 2017/12/27.
 */
class Request(val url: String) {
    public fun run(): String {
        Log.d(javaClass.simpleName, "开始执行网络请求")
        val forecastJsonStr = URL(url).readText()
        Log.d(javaClass.simpleName, forecastJsonStr)
        return forecastJsonStr
    }
}