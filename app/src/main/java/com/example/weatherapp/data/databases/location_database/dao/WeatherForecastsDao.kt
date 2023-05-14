package com.example.weatherapp.data.databases.location_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.databases.location_database.entities.WeatherForecastEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherForecastsDao {

    @Query("SELECT * FROM weather_forecasts WHERE location_id = :locationId")
    fun getWeatherForecastsForLocation(locationId: Long): Flow<List<WeatherForecastEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWeatherForecast(weatherForecastEntity: WeatherForecastEntity)

    @Query("DELETE FROM weather_forecasts WHERE date_time_unix_utc < strftime('%s','now') AND location_id = :locationId")
    suspend fun deleteOldForecastsForLocation(locationId: Long)
}