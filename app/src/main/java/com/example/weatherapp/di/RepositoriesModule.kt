package com.example.weatherapp.di

import com.example.weatherapp.data.repositories.LocationListRepositoryImpl
import com.example.weatherapp.domain.repositories.LocationListRepository
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
        locationListRepositoryImpl: LocationListRepositoryImpl
    ): LocationListRepository
}