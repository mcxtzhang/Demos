package com.example.mykotlindemo.domain

/**
 * Created by zhangxutong on 2017/12/28.
 */
data class ForecastList(val city: String, val country: String,
                        private val dailyForecast: List<Forecast>) {
    //省略返回值
    operator fun get(position: Int) = dailyForecast[position]

    fun size() = dailyForecast.size
}

data class Forecast(val date: String,
                    val description: String,
                    val high: Int,
                    val low: Int,
                    val icon: String)
