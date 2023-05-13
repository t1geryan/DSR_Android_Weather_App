package com.example.weatherapp.data.repositories

import com.example.weatherapp.data.databases.location_database.dao.LocationsDao
import com.example.weatherapp.data.databases.weather_database.dao.CurrentWeatherDao
import com.example.weatherapp.data.databases.weather_database.dao.WeatherForecastsDao
import com.example.weatherapp.data.mappers.*
import com.example.weatherapp.data.remote.weather.api.WeatherApi
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
    private val locationMapper: LocationDomainEntityMapper,
    private val weatherApi: WeatherApi,
    private val currentWeatherDtoMapper: CurrentWeatherDtoToEntityMapper,
    private val forecastDtoMapper: ForecastDtoToEntityMapper,
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherForecastsDao: WeatherForecastsDao,
    private val currentWeatherDomainEntityMapper: CurrentWeatherDomainEntityMapper,
    private val weatherForecastDomainEntityMapper: WeatherForecastDomainEntityMapper,
) : LocationListRepository {
    override suspend fun getAllLocationsWeather(onlyFavorites: Boolean): Flow<List<LocationWeather>> =
        getLocationsFromDatabase(onlyFavorites).map { list ->
            list.map { entity ->
                LocationWeather(
                    locationMapper.reverseMap(entity),
                    getLocationCurrentWeather(entity.lat, entity.lon),
                    getLocationWeatherForecast(entity.lat, entity.lon),
                )
            }
        }

    override suspend fun getLocationWeatherById(locationId: Long): Flow<LocationWeather> =
        locationsDao.getLocationById(locationId).map { entity ->
            LocationWeather(
                locationMapper.reverseMap(entity),
                getLocationCurrentWeather(entity.lat, entity.lon),
                getLocationWeatherForecast(entity.lat, entity.lon)
            )
        }

    override suspend fun addLocation(location: Location) {
        locationsDao.addLocation(
            locationMapper.map(location)
        )
    }

    override suspend fun deleteLocationById(locationId: Long) {
        locationsDao.deleteLocationById(locationId)
    }

    override suspend fun changeLocationFavoriteStatusById(locationId: Long) {
        locationsDao.changeLocationFavoriteStatusById(locationId)
    }

    // todo: replaced by mock
    private fun getLocationWeatherForecast(lat: Float, long: Float): List<Weather> = listOf(
        Weather(temperature = Random.nextFloat() * 10),
        Weather(temperature = Random.nextFloat() * 10),
    )

    // todo: replaced by mock
    private fun getLocationCurrentWeather(lat: Float, long: Float): Weather = Weather(
        temperature = Random.nextFloat() * 10
    )

    private fun getLocationsFromDatabase(onlyFavorites: Boolean) =
        if (onlyFavorites) {
            locationsDao.getFavoriteLocations()
        } else {
            locationsDao.getAllLocations()
        }
}