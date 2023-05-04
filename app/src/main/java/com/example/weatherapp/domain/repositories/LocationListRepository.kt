package com.example.weatherapp.domain.repositories

import com.example.weatherapp.domain.models.LocationWeather
import kotlinx.coroutines.flow.Flow

interface LocationListRepository {

    suspend fun getAllLocationsWeather(onlyFavorites: Boolean = false): Flow<List<LocationWeather>>

    suspend fun getLocationWeatherById(locationId: Int): Flow<LocationWeather>

    suspend fun addLocationWeather(locationWeather: LocationWeather)

    suspend fun deleteLocationById(locationId: Int)

    suspend fun changeLocationFavoriteStatusById(locationId: Int)
}