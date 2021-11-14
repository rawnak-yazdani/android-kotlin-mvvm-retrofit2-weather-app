package com.rawnak.weatherappmvvm.features.weather_info_show.model

import com.rawnak.weatherappmvvm.common.RequestCompleteListener
import com.rawnak.weatherappmvvm.features.weather_info_show.model.data_class.City
import com.rawnak.weatherappmvvm.features.weather_info_show.model.data_class.WeatherInfoResponse

interface WeatherInfoShowModel {
    fun getCityList(callback: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInfo(cityId: Int, callback: RequestCompleteListener<WeatherInfoResponse>)
}