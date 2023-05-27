package com.example.weatherapp.domain.models

/**
 * A class that stores information about location and it weather info
 * @see Location
 * @see CurrentWeather
 * @param location stores base information about location
 * @param currentWeather stores weather information for current moment
 * @param weatherForecasts stores list of weather information for few days
 */
data class LocationWeather(
    val location: Location,
    val currentWeather: CurrentWeather,
    val weatherForecasts: List<Forecast>,
)