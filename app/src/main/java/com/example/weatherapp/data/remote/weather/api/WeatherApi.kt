package com.example.weatherapp.data.remote.weather.api

import com.example.weatherapp.data.remote.weather.dto.CurrentWeatherResponseDto
import com.example.weatherapp.data.remote.weather.dto.WeatherForecastResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("2.5/weather")
    suspend fun getLocationCurrentWeather(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") language: String = "en",
    ): CurrentWeatherResponseDto

    @GET("2.5/forecast")
    suspend fun getLocationEvery3HoursWeatherForecast(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
        @Query("appid") apiKey: String,
        @Query("cnt") timestampsCount: UInt = 16U,
        @Query("units") units: String = "metric",
        @Query("lang") language: String = "en",
    ): WeatherForecastResponseDto
}