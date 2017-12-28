package com.example.mykotlindemo.json.data

/**
 * Created by zhangxutong on 2017/12/28.
 */
data class ForecastResult(var cod: String,
                          var message: Int,
                          var city: CityBean,
                          var cnt: Int,
                          var list: List<ForecastBean>)

data class CityBean(var geoname_id: Int,
                    var name: String,
                    var lat: Double,
                    var lon: Double,
                    var country: String,
                    var iso2: String,
                    var type: String,
                    var population: Int)

data class ForecastBean(var dt: Long,
                        var temp: TempBean,
                        var pressure: Double,
                        var humidity: Int,
                        var speed: Double,
                        var deg: Int,
                        var clouds: Int,
                        var snow: Double,
                        var weather: List<WeatherBean>)

data class TempBean(var day: Double,
                    var min: Double,
                    var max: Double,
                    var night: Double,
                    var eve: Double,
                    var morn: Double)

data class WeatherBean(var id: Int,
                       var main: String,
                       var description: String,
                       var icon: String)
