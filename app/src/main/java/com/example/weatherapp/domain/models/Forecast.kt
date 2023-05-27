package com.example.weatherapp.domain.models

/**
 * A class that store briefly information about future weather
 */
data class Forecast(
    val weatherIconName: String,
    val temperature: Int,
    val dateTimeUnixUtc: Long,
    val shiftFromUtcSeconds: Long,
)