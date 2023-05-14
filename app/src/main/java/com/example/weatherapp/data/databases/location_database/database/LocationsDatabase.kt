package com.example.weatherapp.data.databases.location_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.databases.location_database.dao.CurrentWeatherDao
import com.example.weatherapp.data.databases.location_database.dao.LocationsDao
import com.example.weatherapp.data.databases.location_database.dao.WeatherForecastsDao
import com.example.weatherapp.data.databases.location_database.entities.CurrentWeatherEntity
import com.example.weatherapp.data.databases.location_database.entities.LocationEntity
import com.example.weatherapp.data.databases.location_database.entities.WeatherForecastEntity

@Database(
    version = 1,
    entities = [
        LocationEntity::class,
        CurrentWeatherEntity::class,
        WeatherForecastEntity::class
    ]
)
abstract class LocationsDatabase : RoomDatabase() {

    abstract fun getLocationDao(): LocationsDao

    abstract fun getCurrentWeatherDao(): CurrentWeatherDao

    abstract fun getWeatherForecastDao(): WeatherForecastsDao
}