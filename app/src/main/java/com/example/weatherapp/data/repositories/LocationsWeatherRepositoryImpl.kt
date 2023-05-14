package com.example.weatherapp.data.repositories

import com.example.weatherapp.data.databases.location_database.dao.CurrentWeatherDao
import com.example.weatherapp.data.databases.location_database.dao.LocationsDao
import com.example.weatherapp.data.databases.location_database.dao.WeatherForecastsDao
import com.example.weatherapp.data.databases.location_database.entities.LocationEntity
import com.example.weatherapp.data.mappers.*
import com.example.weatherapp.data.remote.weather.api.WeatherApi
import com.example.weatherapp.domain.models.Location
import com.example.weatherapp.domain.models.LocationWeather
import com.example.weatherapp.domain.models.Weather
import com.example.weatherapp.domain.repositories.LocationsWeatherRepository
import com.example.weatherapp.utils.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocationsWeatherRepositoryImpl @Inject constructor(
    private val locationsDao: LocationsDao,
    private val locationMapper: LocationDomainEntityMapper,
    private val weatherApi: WeatherApi,
    private val currentWeatherDtoMapper: CurrentWeatherDtoToEntityMapper,
    private val forecastDtoMapper: ForecastDtoToEntityMapper,
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherForecastsDao: WeatherForecastsDao,
    private val currentWeatherDomainEntityMapper: CurrentWeatherEntityToDomainMapper,
    private val weatherForecastDomainEntityMapper: WeatherForecastEntityToDomainMapper,
) : LocationsWeatherRepository {

    private fun getLocationsFromDatabase(onlyFavorites: Boolean) =
        if (onlyFavorites) {
            locationsDao.getFavoriteLocations()
        } else {
            locationsDao.getAllLocations()
        }

    override suspend fun getAllLocationsWeather(onlyFavorites: Boolean): Flow<List<LocationWeather>> =
        getLocationsFromDatabase(onlyFavorites).map { list ->
            list.map { locationEntity ->
                createLocationWeatherByLocationEntity(locationEntity)
            }
        }

    override suspend fun getLocationWeatherById(locationId: Long): Flow<LocationWeather> =
        locationsDao.getLocationById(locationId).map { locationEntity ->
            createLocationWeatherByLocationEntity(locationEntity)
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

    private suspend fun createLocationWeatherByLocationEntity(locationEntity: LocationEntity): LocationWeather =
        LocationWeather(
            locationMapper.reverseMap(locationEntity),
            getLocationCurrentWeather(locationEntity),
            getLocationWeatherForecast(locationEntity)
        )


    private suspend fun getLocationWeatherForecast(locationEntity: LocationEntity): List<Weather> {
        updateWeatherForecastForLocationInDb(locationEntity)
        return weatherForecastsDao.getWeatherForecastsForLocation(locationEntity.id).first()
            .map { forecastEntity ->
                weatherForecastDomainEntityMapper.map(forecastEntity)
            }
    }

    private suspend fun getLocationCurrentWeather(locationEntity: LocationEntity): Weather {
        updateCurrentWeatherForLocationInDb(locationEntity)
        return currentWeatherDomainEntityMapper.map(
            currentWeatherDao.getCurrentWeatherForLocation(locationEntity.id).first()
        )
    }

    // todo: add caching logic and do not constantly access weatherApi
    private suspend fun updateWeatherForecastForLocationInDb(locationEntity: LocationEntity) {
        val weatherForecastEntityList = forecastDtoMapper.mapWithParameter(
            weatherApi.getLocationEvery3HoursWeatherForecast(
                locationEntity.lat,
                locationEntity.lon,
                Constants.OPEN_WEATHER_API_KEY,
                timestampsCount = 16U
            ),
            locationEntity.id
        )
        for (forecastEntity in weatherForecastEntityList)
            weatherForecastsDao.addWeatherForecast(forecastEntity)
        weatherForecastsDao.deleteOldForecastsForLocation(locationEntity.id)
    }

    private suspend fun updateCurrentWeatherForLocationInDb(locationEntity: LocationEntity) {
        val currentWeatherEntity = currentWeatherDtoMapper.mapWithParameter(
            weatherApi.getLocationCurrentWeather(
                locationEntity.lat,
                locationEntity.lon,
                Constants.OPEN_WEATHER_API_KEY
            ),
            locationEntity.id
        )
        // will replace the old weather
        currentWeatherDao.addCurrentWeather(currentWeatherEntity)
    }
}