package com.example.weatherapp.presentation.ui.weather_details_screen.adapter

import com.example.weatherapp.domain.models.Forecast

data class ForecastItem(
    val weatherIconName: String = "",
    val dateTimeUnixUtc: Long = 0,
    val shiftFromUtcSeconds: Long = 0,
    val temperature: Int = 0,
) {

    companion object {
        fun fromForecast(forecast: Forecast): ForecastItem = with(forecast) {
            ForecastItem(
                weatherIconName = weatherIconName,
                temperature = temperature,
                dateTimeUnixUtc = dateTimeUnixUtc,
                shiftFromUtcSeconds = shiftFromUtcSeconds,
            )
        }
    }
}