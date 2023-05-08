package com.example.weatherapp.data.remote.weather.dto

import com.google.gson.annotations.SerializedName

data class WeatherForecastResponseDto(
    @SerializedName("cod") val cod: String,
    @SerializedName("message") val message: Int,
    @SerializedName("cnt") val timestampsCount: Int,
    @SerializedName("list") val list: List<ForecastWeather>,
    @SerializedName("city") val city: City,
)

data class ForecastWeather(
    @SerializedName("dt") val dt: Long,
    @SerializedName("main") val main: ForecastMain,
    @SerializedName("weather") val weather: List<WeatherDescription>,
    @SerializedName("clouds") val clouds: Clouds,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("rain") val forecastRain: ForecastRain?,
    @SerializedName("snow") val forecastSnow: ForecastSnow?,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("pop") val pop: Double,
    @SerializedName("sys") val sys: ForecastSys,
    @SerializedName("dt_txt") val dataTimeText: String,
)

data class ForecastSnow(
    @SerializedName("3h") val volumeLast3Hours: Float?,
)

data class ForecastRain(
    @SerializedName("3h") val volumeLast3Hours: Float?,
)

data class ForecastMain(
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("sea_level") val seaLevel: Int,
    @SerializedName("grnd_level") val groundLevel: Int,
    @SerializedName("humidity") val humidity: Int,
    @SerializedName("temp_kf") val tempKf: Double,
)

data class WeatherDescription(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String,
)

data class ForecastSys(
    @SerializedName("pod") val partOfDay: String,
)

data class City(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("coord") val coord: Coord,
    @SerializedName("country") val country: String,
    @SerializedName("population") val population: Int,
    @SerializedName("timezone") val timezoneUtc: Long,
    @SerializedName("sunrise") val sunriseTimeUTC: Long,
    @SerializedName("sunset") val sunsetTimeUtc: Long,
)