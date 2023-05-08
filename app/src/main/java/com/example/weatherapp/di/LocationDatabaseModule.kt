package com.example.weatherapp.di

import android.content.Context
import androidx.room.Room
import com.example.weatherapp.data.databases.location_database.dao.LocationsDao
import com.example.weatherapp.data.databases.location_database.database.LocationsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocationDatabaseModule {

    private val databaseName = "locations.db"

    @Provides
    @Singleton
    fun provideLocationDatabase(@ApplicationContext context: Context): LocationsDatabase =
        Room.databaseBuilder(context, LocationsDatabase::class.java, databaseName).build()

    @Provides
    @Singleton
    fun provideLocationDao(locationsDatabase: LocationsDatabase): LocationsDao =
        locationsDatabase.getLocationDao()
}