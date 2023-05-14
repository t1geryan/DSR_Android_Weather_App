package com.example.weatherapp.di

import com.example.weatherapp.data.mappers.*
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
    abstract fun bindsLocationDomainEntityMapper(
        mapper: LocationDomainEntityMapperImpl
    ): LocationDomainEntityMapper

    @Binds
    @Singleton
    abstract fun bindsCurrentWeatherDomainEntityMapper(
        mapper: CurrentWeatherEntityToDomainMapperImpl
    ): CurrentWeatherEntityToDomainMapper

    @Binds
    @Singleton
    abstract fun bindsWeatherForecastDomainEntityMapper(
        mapper: WeatherForecastEntityToDomainMapperImpl
    ): WeatherForecastEntityToDomainMapper

    @Binds
    @Singleton
    abstract fun bindsCurrentWeatherDtoToEntityMapper(
        mapper: CurrentWeatherDtoToEntityMapperImpl
    ): CurrentWeatherDtoToEntityMapper

    @Binds
    @Singleton
    abstract fun bindsForecastDtoToEntityMapper(
        mapper: ForecastDtoToEntityMapperImpl
    ): ForecastDtoToEntityMapper
}