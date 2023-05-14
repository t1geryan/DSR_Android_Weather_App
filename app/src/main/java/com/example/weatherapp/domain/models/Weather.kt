package com.example.weatherapp.domain.models

/**
 * A class that stores information about weather
 */
data class Weather(
    val weatherConditionId: Int = 0,
    val weatherName: String = "",
    val weatherDescription: String = "",
    val weatherIconName: String = "",
    val temperature: Int = 0,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val windSpeed: Float = 0.0f,
    val dateTimeUnix: Long = 0,
)