package com.example.weatherapp.domain.models

/**
 * A class that store briefly information about future weather
 */
data class Forecast(
    val weatherIconName: String = "",
    val temperature: Int = 0,
    val dateTimeUnixUtc: Long = 0,
    val shiftFromUtcSeconds: Long = 0,
)