package com.example.weatherapp.data.repositories

import com.example.weatherapp.data.databases.location_database.dao.CurrentWeatherDao
import com.example.weatherapp.data.databases.location_database.dao.LocationsDao
import com.example.weatherapp.data.databases.location_database.dao.WeatherForecastsDao
import com.example.weatherapp.data.databases.location_database.entities.LocationEntity
import com.example.weatherapp.data.mappers.current_weather.CurrentWeatherDtoToEntityMapper
import com.example.weatherapp.data.mappers.current_weather.CurrentWeatherEntityToDomainMapper
import com.example.weatherapp.data.mappers.forecast.ForecastDtoToEntityMapper
import com.example.weatherapp.data.mappers.forecast.WeatherForecastEntityToDomainMapper
import com.example.weatherapp.data.mappers.location.LocationDomainEntityMapper
import com.example.weatherapp.data.pref_datastore.settings_datastore.dao.SettingsDao
import com.example.weatherapp.data.pref_datastore.settings_datastore.entities.UnitsSystemEntity
import com.example.weatherapp.data.remote.weather.api.WeatherApi
import com.example.weatherapp.domain.AppException
import com.example.weatherapp.domain.ConnectionException
import com.example.weatherapp.domain.models.CurrentWeather
import com.example.weatherapp.domain.models.Forecast
import com.example.weatherapp.domain.models.Location
import com.example.weatherapp.domain.models.LocationWeather
import com.example.weatherapp.domain.repositories.LocationsWeatherRepository
import com.example.weatherapp.utils.Constants
import com.example.weatherapp.utils.extensions.wrapRetrofitExceptions
import com.example.weatherapp.utils.locale.CurrentLocaleProvider
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
    private val settingsDao: SettingsDao,
    private val currentLocaleProvider: CurrentLocaleProvider,
) : LocationsWeatherRepository {

    override fun getAllLocations(onlyFavorites: Boolean): Flow<List<Location>> =
        getLocationsFromDatabase(onlyFavorites) { locationEntity ->
            locationMapper.reverseMap(locationEntity)
        }

    override fun getAllLocationsWeather(onlyFavorites: Boolean): Flow<List<LocationWeather>> =
        getLocationsFromDatabase(onlyFavorites) { locationEntity ->
            createLocationWeatherByLocationEntity(locationEntity)
        }

    override fun getLocationWeatherById(locationId: Long): Flow<LocationWeather> =
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

    private fun <T> getLocationsFromDatabase(
        onlyFavorites: Boolean,
        locationEntityMapper: suspend (LocationEntity) -> T
    ): Flow<List<T>> {
        try {
            val locationsEntitiesFlow = if (onlyFavorites) {
                locationsDao.getFavoriteLocations()
            } else {
                locationsDao.getAllLocations()
            }
            return locationsEntitiesFlow.map { list ->
                list.map { locationEntity ->
                    locationEntityMapper(locationEntity)
                }
            }
        } catch (e: Exception) {
            throw AppException(e)
        }
    }

    /**
     * Creates LocationWeather class for location
     * @return weather for location from [weatherApi] or non-updated data from db if there is no internet connection
     */
    private suspend fun createLocationWeatherByLocationEntity(locationEntity: LocationEntity): LocationWeather {
        try {
            wrapRetrofitExceptions {
                updateWeatherForecastForLocationInDb(locationEntity)
                updateCurrentWeatherForLocationInDb(locationEntity)
            }
        } catch (_: ConnectionException) {
            // return non-updated data if catch ConnectionException
        }
        return LocationWeather(
            locationMapper.reverseMap(locationEntity),
            getLocationCurrentWeather(locationEntity),
            getLocationWeatherForecast(locationEntity)
        )
    }

    private suspend fun getLocationWeatherForecast(locationEntity: LocationEntity): List<Forecast> =
        weatherForecastsDao.getWeatherForecastsForLocation(locationEntity.id).first()
            .map { forecastEntity ->
                weatherForecastDomainEntityMapper.map(forecastEntity)
            }

    private suspend fun getLocationCurrentWeather(locationEntity: LocationEntity): CurrentWeather =
        currentWeatherDomainEntityMapper.map(
            currentWeatherDao.getCurrentWeatherForLocation(locationEntity.id).first()
        )

    private suspend fun updateWeatherForecastForLocationInDb(locationEntity: LocationEntity) {
        weatherForecastsDao.deleteOldForecastsForLocation(locationEntity.id)
        val forecastResponse = weatherApi.getLocationEvery3HoursWeatherForecast(
            locationEntity.lat,
            locationEntity.lon,
            apiKey = Constants.OPEN_WEATHER_API_KEY,
            units = getCurrentUnitsSystem(),
            timestampsCount = Constants.Weather.FORECASTS_COUNT_FOR_3_DAYS,
            language = getApiRequestLocale(),
        )
        if (forecastResponse.isSuccessful) {
            forecastResponse.body()?.let { forecastDto ->
                val weatherForecastEntityList = forecastDtoMapper.mapWithParameter(
                    forecastDto,
                    locationEntity.id
                )
                for (forecastEntity in weatherForecastEntityList)
                    weatherForecastsDao.addWeatherForecast(forecastEntity)
            }
        }
    }

    private suspend fun updateCurrentWeatherForLocationInDb(locationEntity: LocationEntity) {
        val currentWeatherResponse = weatherApi.getLocationCurrentWeather(
            locationEntity.lat,
            locationEntity.lon,
            apiKey = Constants.OPEN_WEATHER_API_KEY,
            units = getCurrentUnitsSystem(),
            language = getApiRequestLocale(),
        )
        if (currentWeatherResponse.isSuccessful) {
            currentWeatherResponse.body()?.let { currentWeatherDto ->
                val currentWeatherEntity = currentWeatherDtoMapper.mapWithParameter(
                    currentWeatherDto,
                    locationEntity.id
                )
                // will replace the old weather
                currentWeatherDao.addCurrentWeather(currentWeatherEntity)
            }

        }
    }

    private suspend fun getCurrentUnitsSystem() =
        when (settingsDao.getUnitsSystem().first()) {
            UnitsSystemEntity.METRIC_SYSTEM -> Constants.WeatherApi.METRIC_UNITS_SYSTEM_API_VALUE
            else -> Constants.WeatherApi.IMPERIAl_UNITS_SYSTEM_API_VALUE
        }

    private fun getApiRequestLocale() = currentLocaleProvider.provideIso3166Code()
}