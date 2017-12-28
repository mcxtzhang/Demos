package com.example.mykotlindemo.domain

import com.example.mykotlindemo.json.data.ForecastBean
import com.example.mykotlindemo.json.data.ForecastResult
import java.text.DateFormat
import java.util.*

/**
 * Created by zhangxutong on 2017/12/28.
 */
class ForecastDataMapper {
    fun convertFromDataModel(forecast: ForecastResult): ForecastList {
        return ForecastList(forecast.city.name, forecast.city.country,
                convertForecastList2Domain(forecast.list))
    }

    private fun convertForecastList2Domain(list: List<ForecastBean>): List<Forecast> {
        return list.map {
            convertForecastItem2Domain(it)
        }
    }

    private fun convertForecastItem2Domain(it: ForecastBean): Forecast {
        return Forecast(convert2Date(it.dt),
                it.weather[0].description,
                it.temp.max.toInt(),
                it.temp.min.toInt())
    }

    private fun convert2Date(date: Long): String {
        val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINESE)
        return df.format(date * 1000)
    }
}