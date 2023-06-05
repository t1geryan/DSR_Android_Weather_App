package com.example.weatherapp.di.utils

import com.example.weatherapp.utils.location.CurrentLocationGettingAbilityChecker
import com.example.weatherapp.utils.location.CurrentLocationGettingAbilityCheckerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrentLocationGettingAbilityCheckerModule {

    @Binds
    @Singleton
    abstract fun bindsCurrentLocationGettingAbilityChecker(
        currentLocationGettingAbilityCheckerImpl: CurrentLocationGettingAbilityCheckerImpl
    ): CurrentLocationGettingAbilityChecker
}