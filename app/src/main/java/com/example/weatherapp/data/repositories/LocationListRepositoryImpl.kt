package com.example.weatherapp.data.repositories

import com.example.weatherapp.domain.models.Location
import com.example.weatherapp.domain.models.LocationWeather
import com.example.weatherapp.domain.models.Weather
import com.example.weatherapp.domain.repositories.LocationListRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocationListRepositoryImpl @Inject constructor() : LocationListRepository {
    override suspend fun getAllLocationsWeather(onlyFavorites: Boolean): Flow<List<LocationWeather>> =
        flow {
            // todo (not implemented yet - replaced by mock)
            delay(2000)
            emit(
                listOf(
                    LocationWeather(Location(2, "Voronezh"), listOf(Weather(temperature = 12.4f))),
                    LocationWeather(
                        Location(3, "Moscow"),
                        listOf(Weather(temperature = 12.4f), Weather(temperature = 13.5f))
                    ),
                )
            )
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