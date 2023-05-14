package com.example.weatherapp.presentation.ui.weather_details_screen.adapter

import com.example.weatherapp.domain.models.Weather

data class ForecastItem(
    val weatherIconName: String = "",
    val dateTimeUnixUtc: Long = 0,
    val shiftFromUtcSeconds: Long = 0,
    val temperature: Int = 0,
) {

    companion object {

        fun fromWeather(weather: Weather): ForecastItem = with(weather) {
            ForecastItem(
                weatherIconName = weatherIconName,
                temperature = temperature,
                dateTimeUnixUtc = dateTimeUnixUtc,
                shiftFromUtcSeconds = shiftFromUtcSeconds,
            )
        }
    }
}