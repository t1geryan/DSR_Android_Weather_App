package com.example.weatherapp.di

import com.example.weatherapp.utils.locale.CurrentLocaleProvider
import com.example.weatherapp.utils.locale.CurrentLocaleProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UtilsModule {

    @Binds
    @Singleton
    abstract fun bindsCurrentLocaleProvider(
        currentLocaleProviderImpl: CurrentLocaleProviderImpl
    ): CurrentLocaleProvider
}