package com.example.weatherapp.data.databases.weather_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.databases.weather_database.dao.CurrentWeatherDao
import com.example.weatherapp.data.databases.weather_database.dao.WeatherForecastsDao
import com.example.weatherapp.data.databases.weather_database.entities.CurrentWeatherEntity
import com.example.weatherapp.data.databases.weather_database.entities.WeatherForecastEntity

@Database(
    version = 1,
    entities = [
        CurrentWeatherEntity::class,
        WeatherForecastEntity::class
    ]
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getCurrentWeatherDao(): CurrentWeatherDao

    abstract fun getWeatherForecastDao(): WeatherForecastsDao
}