package com.example.weatherapp.data.databases.location_database.entities

import androidx.room.*

@Entity(
    tableName = "current_weather",
    indices = [
        Index("location_id", unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = LocationEntity::class,
            parentColumns = ["id"],
            childColumns = ["location_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class CurrentWeatherEntity(
    @PrimaryKey @ColumnInfo("location_id") val locationId: Long,
    @ColumnInfo("weather_condition_id") val weatherConditionId: Int,
    @ColumnInfo("weather_name") val weatherName: String,
    @ColumnInfo("weather_description") val weatherDescription: String,
    @ColumnInfo("weather_icon_name") val weatherIconName: String,
    val temperature: Float,
    val pressure: Int,
    val humidity: Int,
    @ColumnInfo("wind_speed") val windSpeed: Float,
    @ColumnInfo("date_time_unix_utc") val dateTimeUnixUtc: Long,
    @ColumnInfo("shift_from_utc_sec") val shiftFromUtcSec: Long,
)