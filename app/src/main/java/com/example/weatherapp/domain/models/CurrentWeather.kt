package com.example.weatherapp.domain.models

/**
 * A class that stores detailed information about current weather
 */
data class CurrentWeather(
    val weatherConditionId: Int,
    val weatherName: String,
    val weatherDescription: String,
    val weatherIconName: String,
    val temperature: Int,
    val pressure: Int,
    val humidity: Int,
    val windSpeed: Float,
    val windDirectionDegrees: Int,
    val dateTimeUnixUtc: Long,
    val shiftFromUtcSeconds: Long,
)