package com.example.weatherapp.domain.repositories

import com.example.weatherapp.domain.models.Location
import com.example.weatherapp.domain.models.LocationWeather
import kotlinx.coroutines.flow.Flow

interface LocationListRepository {

    suspend fun getAllLocationsWeather(onlyFavorites: Boolean = false): Flow<List<LocationWeather>>

    suspend fun getLocationWeatherById(locationId: Long): Flow<LocationWeather>

    suspend fun addLocation(location: Location)

    suspend fun deleteLocationById(locationId: Long)

    suspend fun changeLocationFavoriteStatusById(locationId: Long)
}