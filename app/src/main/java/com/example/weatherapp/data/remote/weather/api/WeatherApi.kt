package com.example.weatherapp.data.remote.weather.api

import com.example.weatherapp.data.remote.weather.dto.CurrentWeatherResponseDto
import com.example.weatherapp.data.remote.weather.dto.WeatherForecastResponseDto
import com.example.weatherapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getLocationCurrentWeather(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
        @Query("appid") apiKey: String,
        @Query("units") units: String,
        @Query("lang") language: String,
    ): Response<CurrentWeatherResponseDto>

    @GET("data/2.5/forecast")
    suspend fun getLocationEvery3HoursWeatherForecast(
        @Query("lat") latitude: Float,
        @Query("lon") longitude: Float,
        @Query("appid") apiKey: String,
        @Query("cnt") timestampsCount: UInt = Constants.Weather.FORECASTS_COUNT_FOR_3_DAYS,
        @Query("units") units: String,
        @Query("lang") language: String,
    ): Response<WeatherForecastResponseDto>
}