package com.example.mykotlindemo.domain

import com.example.mykotlindemo.json.ForecastRequest

/**
 * Created by zhangxutong on 2017/12/28.
 */
class RequestForecastCommand(private val id: String) : Command<ForecastList> {
    override fun execute(): ForecastList {
        val forecastRequest = ForecastRequest(id)
        return ForecastDataMapper().convertFromDataModel(forecastRequest.execute())
    }

}