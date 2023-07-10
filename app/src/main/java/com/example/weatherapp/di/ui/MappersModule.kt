package com.example.weatherapp.di.ui

import com.example.weatherapp.presentation.ui.weather_details_screen.adapter.forecastitem.ForecastToForecastItemMapper
import com.example.weatherapp.presentation.ui.weather_details_screen.adapter.forecastitem.ForecastToForecastItemMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MappersModule {

    @Binds
    @Singleton
    abstract fun bindsForecastToForecastItemMapper(
        forecastToForecastItemMapperImpl: ForecastToForecastItemMapperImpl
    ): ForecastToForecastItemMapper
}