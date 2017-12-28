package com.example.mykotlindemo.domain

/**
 * Created by zhangxutong on 2017/12/28.
 */
data class ForecastList(val city: String, val country: String,
                        val dailyForecast: List<Forecast>)

data class Forecast(val date: String,
                    val description: String,
                    val high: Int,
                    val low: Int)
