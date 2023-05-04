package com.example.weatherapp.domain.repositories

import com.example.weatherapp.domain.models.Location
import com.example.weatherapp.domain.models.LocationWeather
import kotlinx.coroutines.flow.Flow

interface LocationListRepository {

    suspend fun getAllLocations(onlyFavorites: Boolean = false): Flow<List<Location>>

    suspend fun getLocationWeatherById(locationId: Int): Flow<LocationWeather>

    suspend fun addLocationWeather(locationWeather: LocationWeather)

    suspend fun deleteLocationById(locationId: Int)

    suspend fun changeLocationFavoriteStatusById(locationId: Int)
}