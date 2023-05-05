package com.example.weatherapp.domain.models

/**
 * A class that stores basic information about the location (not detailed)
 * @param id stores a location id to distinguish between them
 * @param name is location name
 * @param isFavorite indicates the presence of a location in the list of favorites
 * @param lat is location latitude
 * @param long is location longitude
 */
data class Location(
    val id: Int = 0,
    val name: String = "",
    val isFavorite: Boolean = false,
    val hasNextDayForecast: Boolean = false,
    val lat: Float = 0.0f,
    val long: Float = 0.0f,
)