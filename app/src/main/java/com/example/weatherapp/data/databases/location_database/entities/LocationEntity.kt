package com.example.weatherapp.data.databases.location_database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "locations",
)
data class LocationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val lat: Float,
    val lon: Float,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    @ColumnInfo(name = "has_next_day_forecast") val hasNextDayForecast: Boolean,
)