package com.example.weatherapp.domain.models

/**
 * A class that stores detailed information about current weather
 */
data class CurrentWeather(
    val weatherConditionId: Int = 0,
    val weatherName: String = "",
    val weatherDescription: String = "",
    val weatherIconName: String = "",
    val temperature: Int = 0,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val windSpeed: Float = 0.0f,
    val windDegree: Int = 0,
    val dateTimeUnixUtc: Long = 0,
    val shiftFromUtcSeconds: Long = 0,
)