package com.example.weatherapp.di.domain

import com.example.weatherapp.data.repositories.*
import com.example.weatherapp.domain.repositories.*
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

    @Binds
    @Singleton
    abstract fun bindsLocationTrackerRepository(
        locationTrackerRepositoryImpl: LocationTrackerRepositoryImpl
    ): LocationTrackerRepository

    @Binds
    @Singleton
    abstract fun bindsAutocompleteDataProviderRepository(
        locationsAutocompleteDataProviderRepositoryImpl: LocationsAutocompleteDataProviderRepositoryImpl
    ): LocationsAutocompleteDataProviderRepository

    @Binds
    @Singleton
    abstract fun bindsGeocoderRepository(
        geocoderRepositoryImpl: GeocoderRepositoryImpl
    ): GeocoderRepository
}