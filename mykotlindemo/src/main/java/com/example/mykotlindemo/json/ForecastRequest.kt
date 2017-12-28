package com.example.mykotlindemo.json

import com.example.mykotlindemo.json.data.ForecastResult
import com.google.gson.Gson
import java.net.URL

/**
 * Created by zhangxutong on 2017/12/28.
 */
class ForecastRequest(val id: String) {

    companion object {
        private val APP_ID = "b6907d289e10d714a6e88b30761fae22"
        private val BASE_URL = "http://samples.openweathermap.org/data/2.5/"
        private val COMPLETE_URL = "${BASE_URL}forecast/daily?id="
        private val PARAMS_SUFFIX = "&lang=zh_cn&appid=${APP_ID}"
    }

    //524901
    fun execute(): ForecastResult {
        val url = COMPLETE_URL + id + PARAMS_SUFFIX
        println(url)
        val json = URL(url).readText()
        println("json ：$json")
        return Gson().fromJson(json, ForecastResult::class.java)

    }

}