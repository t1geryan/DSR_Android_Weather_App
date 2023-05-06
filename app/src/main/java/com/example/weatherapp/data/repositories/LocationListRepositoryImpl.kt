package com.example.weatherapp.data.repositories

import com.example.weatherapp.data.databases.location_database.dao.LocationsDao
import com.example.weatherapp.data.mappers.LocationMapper
import com.example.weatherapp.domain.models.Location
import com.example.weatherapp.domain.models.LocationWeather
import com.example.weatherapp.domain.models.Weather
import com.example.weatherapp.domain.repositories.LocationListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.random.Random

class LocationListRepositoryImpl @Inject constructor(
    private val locationsDao: LocationsDao,
    private val locationMapper: LocationMapper,
) : LocationListRepository {
    override suspend fun getAllLocationsWeather(onlyFavorites: Boolean): Flow<List<LocationWeather>> =
        getLocationsFromDatabase(onlyFavorites).map { list ->
            list.map { entity ->
                LocationWeather(
                    locationMapper.reverseMap(entity),
                    getWeatherForLocation(entity.lat, entity.lon)
                )
            }
        }

    override suspend fun getLocationWeatherById(locationId: Long): Flow<LocationWeather> {
        TODO("Not yet implemented")
    }

    override suspend fun addLocation(location: Location) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocationById(locationId: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun changeLocationFavoriteStatusById(locationId: Long) {

    }

    // todo: replaced by mock
    private fun getWeatherForLocation(lat: Float, long: Float): List<Weather> = listOf(
        Weather(temperature = Random.nextFloat() * 10),
        Weather(temperature = Random.nextFloat() * 10),
    )

    private fun getLocationsFromDatabase(onlyFavorites: Boolean) =
        if (onlyFavorites) {
            locationsDao.getFavoriteLocations()
        } else {
            locationsDao.getAllLocations()
        }
}