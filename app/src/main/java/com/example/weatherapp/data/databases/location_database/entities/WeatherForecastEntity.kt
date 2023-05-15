package com.example.weatherapp.data.databases.location_database.entities

import androidx.room.*

@Entity(
    tableName = "weather_forecasts",
    primaryKeys = [
        "location_id",
        "date_time_unix_utc"
    ],
    indices = [
        Index("location_id"),
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
data class WeatherForecastEntity(
    @ColumnInfo("location_id") val locationId: Long,
    @ColumnInfo("weather_icon_name") val weatherIconName: String,
    val temperature: Float,
    @ColumnInfo("date_time_unix_utc") val dateTimeUnixUTC: Long,
    @ColumnInfo("shift_from_utc_sec") val shiftFromUtcSec: Long,
)