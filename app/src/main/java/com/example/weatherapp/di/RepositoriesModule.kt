package com.example.weatherapp.di

import com.example.weatherapp.data.repositories.LocationsWeatherRepositoryImpl
import com.example.weatherapp.data.repositories.SettingsRepositoryImpl
import com.example.weatherapp.domain.repositories.LocationsWeatherRepository
import com.example.weatherapp.domain.repositories.SettingsRepository
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

    @Binds
    @Singleton
    abstract fun bindsSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository
}