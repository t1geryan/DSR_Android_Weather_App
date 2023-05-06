package com.example.weatherapp.data.databases.location_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.databases.location_database.entities.LocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationsDao {

    @Query("SELECT * FROM locations ORDER BY lower(name)")
    fun getAllLocations(): Flow<List<LocationEntity>>

    @Query("SELECT * FROM locations WHERE is_favorite = 1 ORDER BY lower(name)")
    fun getFavoriteLocations(): Flow<List<LocationEntity>>

    @Query("DELETE FROM locations WHERE id = :id")
    suspend fun deleteLocationById(id: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocation(locationEntity: LocationEntity)

    @Query("UPDATE locations SET is_favorite = not(is_favorite) WHERE id = :id")
    suspend fun changeLocationFavoriteStatusById(id: Long)
}