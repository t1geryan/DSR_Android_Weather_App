package com.example.weatherapp.domain.repositories

import com.example.weatherapp.domain.AppException
import com.example.weatherapp.domain.BackendException
import com.example.weatherapp.domain.models.Location
import com.example.weatherapp.domain.models.LocationWeather
import kotlinx.coroutines.flow.Flow

interface LocationsWeatherRepository {

    /**
     * The function for getting list of [Location] for all/favorite locations
     * @throws AppException
     */
    fun getAllLocations(onlyFavorites: Boolean = false): Flow<List<Location>>

    /**
     * The function for getting list of [LocationWeather] for all/favorite locations.
     * Returns unupdated data if there is no network connection
     * @throws BackendException
     * @throws AppException
     */
    fun getAllLocationsWeather(onlyFavorites: Boolean = false): Flow<List<LocationWeather>>

    /**
     * The function for getting [LocationWeather] for location by id.
     * Returns unupdated data if there is no network connection
     * @throws BackendException
     * @throws AppException
     */
    fun getLocationWeatherById(locationId: Long): Flow<LocationWeather>

    suspend fun addLocation(location: Location)

    suspend fun deleteLocationById(locationId: Long)

    suspend fun changeLocationFavoriteStatusById(locationId: Long)
}