package com.example.weatherapp.data.remote.weather.api

import com.example.weatherapp.data.remote.weather.dto.CurrentWeatherResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("3.0/onecall")
    suspend fun getLocationCurrentWeather(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
        @Query("appid") apiKey: String,
        @Query("exclude") exclude: String = "current, daily",
    ): CurrentWeatherResponseDto

    // todo: write forecast @GET query
}