package com.example.weatherapp.data.repositories

import com.example.weatherapp.data.databases.location_database.dao.CurrentWeatherDao
import com.example.weatherapp.data.databases.location_database.dao.LocationsDao
import com.example.weatherapp.data.databases.location_database.dao.WeatherForecastsDao
import com.example.weatherapp.data.mappers.current_weather.CurrentWeatherDtoToEntityMapper
import com.example.weatherapp.data.mappers.current_weather.CurrentWeatherEntityToDomainMapper
import com.example.weatherapp.data.mappers.forecast.ForecastDtoToEntityMapper
import com.example.weatherapp.data.mappers.forecast.WeatherForecastEntityToDomainMapper
import com.example.weatherapp.data.mappers.location.LocationDomainEntityMapper
import com.example.weatherapp.data.pref_datastore.settings_datastore.dao.SettingsDao
import com.example.weatherapp.data.remote.weather.api.WeatherApi
import com.example.weatherapp.utils.locale.CurrentLocaleProvider
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import org.junit.Before
import org.junit.Rule

class LocationsWeatherRepositoryImplTest {

    // TODO: write tests

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var locationsDao: LocationsDao

    @MockK
    lateinit var locationMapper: LocationDomainEntityMapper

    @MockK
    lateinit var weatherApi: WeatherApi

    @MockK
    lateinit var currentWeatherDtoMapper: CurrentWeatherDtoToEntityMapper

    @MockK
    lateinit var forecastDtoMapper: ForecastDtoToEntityMapper

    @MockK
    lateinit var currentWeatherDao: CurrentWeatherDao

    @MockK
    lateinit var weatherForecastsDao: WeatherForecastsDao

    @MockK
    lateinit var currentWeatherDomainEntityMapper: CurrentWeatherEntityToDomainMapper

    @MockK
    lateinit var weatherForecastDomainEntityMapper: WeatherForecastEntityToDomainMapper

    @MockK
    lateinit var settingsDao: SettingsDao

    @MockK
    lateinit var currentLocaleProvider: CurrentLocaleProvider

    private lateinit var repositoryImpl: LocationsWeatherRepositoryImpl

    @Before
    fun setUp() {
        repositoryImpl = LocationsWeatherRepositoryImpl(
            locationsDao,
            locationMapper,
            weatherApi,
            currentWeatherDtoMapper,
            forecastDtoMapper,
            currentWeatherDao,
            weatherForecastsDao,
            currentWeatherDomainEntityMapper,
            weatherForecastDomainEntityMapper,
            settingsDao,
            currentLocaleProvider
        )
    }
}