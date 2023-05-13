package com.example.weatherapp.di

import com.example.weatherapp.data.repositories.LocationsWeatherRepositoryImpl
import com.example.weatherapp.domain.repositories.LocationsWeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    @Singleton
    abstract fun bindsLocationListRepository(
        locationsWeatherRepositoryImpl: LocationsWeatherRepositoryImpl
    ): LocationsWeatherRepository
}