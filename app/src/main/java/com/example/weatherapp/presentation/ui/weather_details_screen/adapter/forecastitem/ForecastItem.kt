package com.example.weatherapp.presentation.ui.weather_details_screen.adapter.forecastitem

data class ForecastItem(
    val weatherIconName: String = "",
    val dateTimeUnixUtc: Long = 0,
    val shiftFromUtcSeconds: Long = 0,
    val temperature: Int = 0,
)