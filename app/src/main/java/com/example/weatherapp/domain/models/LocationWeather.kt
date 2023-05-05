package com.example.weatherapp.domain.models

typealias LocationItem = LocationWeather

/**
 * A class that stores information about location and it weather info
 * @see Location
 * @see Weather
 * @param location stores base information about location
 * @param weatherForecast stores list of base weather information for few days that needs to be shown
 */
data class LocationWeather(
    val location: Location,
    val weatherForecast: List<Weather>
)