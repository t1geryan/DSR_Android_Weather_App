package com.example.weatherapp.data.remote.weather.dto

import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponseDto(
    @SerializedName("coord") val coordinates: Coordinates,
    @SerializedName("weather") val weather: List<CurrentWeather>,
    @SerializedName("base") val base: String,
    @SerializedName("main") val main: CurrentMain,
    @SerializedName("visibility") val visibility: Int,
    @SerializedName("wind") val wind: Wind,
    @SerializedName("clouds") val clouds: Clouds,
    @SerializedName("rain") val rain: CurrentRain?,
    @SerializedName("snow") val snow: CurrentSnow?,
    @SerializedName("dt") val dataTimeUtc: Long,
    @SerializedName("sys") val sys: CurrentSys,
    @SerializedName("timezone") val timezone: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("cod") val cod: Int,
)

data class CurrentSnow(
    @SerializedName("1h") val volumeLastHour: Float?,
    @SerializedName("3h") val volumeLast3Hours: Float?,
)

data class CurrentRain(
    @SerializedName("1h") val volumeLastHour: Float?,
    @SerializedName("3h") val volumeLast3Hours: Float?,
)

data class Coordinates(
    @SerializedName("lon") val lon: Float,
    @SerializedName("lat") val lat: Float,
)

data class CurrentWeather(
    @SerializedName("id") val id: Int,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("icon") val icon: String,
)

data class CurrentMain(
    @SerializedName("temp") val temp: Double,
    @SerializedName("feels_like") val feelsLike: Double,
    @SerializedName("temp_min") val tempMin: Double,
    @SerializedName("temp_max") val tempMax: Double,
    @SerializedName("pressure") val pressure: Int,
    @SerializedName("humidity") val humidity: Int,
)

data class Wind(
    @SerializedName("speed") val speed: Double,
    @SerializedName("deg") val deg: Int,
    @SerializedName("gust") val gust: Double,
)

data class Clouds(
    @SerializedName("all") val all: Int,
)

data class CurrentSys(
    @SerializedName("type") val type: Int,
    @SerializedName("id") val id: Int,
    @SerializedName("message") val message: String?,
    @SerializedName("country") val country: String,
    @SerializedName("sunrise") val sunrise: Long,
    @SerializedName("sunset") val sunset: Long,
)