package com.example.weatherapp.domain.models

/**
 * A class that stores information about weather
 */
data class Weather(
    val weatherId: Int = 0,
    val weatherName: String = "",
    val weatherDescription: String = "",
    val weatherIconName: String = "",
    val temperature: Float = 0.0f,
    val pressure: Int = 0,
    val humidity: Int = 0,
    val windSpeed: Float = 0.0f,
)