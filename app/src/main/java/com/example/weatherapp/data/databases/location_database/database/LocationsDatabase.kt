package com.example.weatherapp.data.databases.location_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weatherapp.data.databases.location_database.dao.LocationsDao
import com.example.weatherapp.data.databases.location_database.entities.LocationEntity

@Database(
    version = 1,
    entities = [
        LocationEntity::class
    ]
)
abstract class LocationsDatabase : RoomDatabase() {

    abstract fun getLocationDao(): LocationsDao
}