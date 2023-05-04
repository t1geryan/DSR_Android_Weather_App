package com.example.weatherapp.data.repositories

import com.example.weatherapp.domain.models.Location
import com.example.weatherapp.domain.models.LocationWeather
import com.example.weatherapp.domain.repositories.LocationListRepository
import kotlinx.coroutines.flow.Flow

class LocationListRepositoryImpl : LocationListRepository {
    override suspend fun getAllLocations(onlyFavorites: Boolean): Flow<List<Location>> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocationWeatherById(locationId: Int): Flow<LocationWeather> {
        TODO("Not yet implemented")
    }

    override suspend fun addLocationWeather(locationWeather: LocationWeather) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocationById(locationId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun changeLocationFavoriteStatusById(locationId: Int) {
        TODO("Not yet implemented")
    }
}