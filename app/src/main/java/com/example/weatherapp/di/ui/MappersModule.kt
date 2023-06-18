package com.example.weatherapp.di.ui

import com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_conditions_screen.adapter.conditionitem.ConditionDomainPresentationMapper
import com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_conditions_screen.adapter.conditionitem.ConditionDomainPresentationMapperImpl
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
    abstract fun bindsConditionMapper(
        conditionDomainPresentationMapperImpl: ConditionDomainPresentationMapperImpl
    ): ConditionDomainPresentationMapper

    @Binds
    @Singleton
    abstract fun bindsForecastToForecastItemMapper(
        forecastToForecastItemMapperImpl: ForecastToForecastItemMapperImpl
    ): ForecastToForecastItemMapper
}