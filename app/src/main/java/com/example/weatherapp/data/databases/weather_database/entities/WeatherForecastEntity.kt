package com.example.weatherapp.data.databases.weather_database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// todo: not yet implemented
@Entity(
    tableName = "weather_forecasts"
)
data class WeatherForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
)