package com.example.weatherapp.domain.models

/**
 * A class that stores basic information about the location (not detailed)
 *
 * @param id stores a location id to distinguish between them
 * @param name is location name
 * @param isFavorite indicates the presence of a location in the list of favorites
 * @param currentTemperature indicates current temperature for location
 * @param hasTomorrowTemperature indicated  the presence of next day temp
 * @param tomorrowTemperature stores next day temp if previous parameter is true else stores null
 */
data class Location(
    val id: Int = 0,
    val name: String = "",
    val isFavorite: Boolean = false,
    val currentTemperature: Int = 0,
    val hasTomorrowTemperature: Boolean = false,
    val tomorrowTemperature: Int? = null,
)