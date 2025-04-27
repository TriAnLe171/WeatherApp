package com.example.weatherapp.ui.theme.network

import com.example.weatherapp.ui.theme.data.WeatherRes
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String
    ): WeatherRes
}
