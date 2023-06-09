package com.example.weatherapp.domain.models

/**
 * A class that stores basic information about the location (not detailed)
 * @param id stores a location id to distinguish between them
 * @param name location name
 * @param isFavorite indicates the presence of a location in the list of favorites
 * @param hasNextDayForecast indicates the need to show tomorrow's temperature in list of Locations
 * @param latLng locations coordinates
 */
data class Location(
    val id: Long,
    val name: String,
    val isFavorite: Boolean,
    val hasNextDayForecast: Boolean,
    val latLng: LatLng,
)