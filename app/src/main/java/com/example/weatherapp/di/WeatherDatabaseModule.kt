package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.databases.weather_database.dao.CurrentWeatherDao
import com.example.weatherapp.data.databases.weather_database.dao.WeatherForecastsDao
import com.example.weatherapp.data.databases.weather_database.database.WeatherDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WeatherDatabaseModule {

    private const val databaseName = "weather.db"

    @Provides
    @Singleton
    fun providesWeatherDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(context, WeatherDatabase::class.java, databaseName).build()

    @Provides
    @Singleton
    fun providesCurrentWeatherDao(weatherDatabase: WeatherDatabase): CurrentWeatherDao =
        weatherDatabase.getCurrentWeatherDao()

    @Provides
    @Singleton
    fun providesWeatherForecastDao(weatherDatabase: WeatherDatabase): WeatherForecastsDao =
        weatherDatabase.getWeatherForecastDao()
}