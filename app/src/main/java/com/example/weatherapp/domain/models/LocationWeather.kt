package com.example.weatherapp.domain.models

typealias LocationItem = LocationWeather

/**
 * A class that stores information about location and it weather info
 * @see Location
 * @see Weather
 * @param location stores base information about location
 * @param currentWeather stores weather information for current days
 * @param weatherForecast stores list of weather information for few days that needs to be shown
 */
data class LocationWeather(
    val location: Location = Location(),
    val currentWeather: Weather,
    val weatherForecast: List<Weather> = listOf(),
)