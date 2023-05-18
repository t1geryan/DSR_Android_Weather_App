package com.example.weatherapp.di

import com.example.weatherapp.data.mappers.*
import com.example.weatherapp.data.mappers.current_weather.CurrentWeatherDtoToEntityMapper
import com.example.weatherapp.data.mappers.current_weather.CurrentWeatherDtoToEntityMapperImpl
import com.example.weatherapp.data.mappers.current_weather.CurrentWeatherEntityToDomainMapper
import com.example.weatherapp.data.mappers.current_weather.CurrentWeatherEntityToDomainMapperImpl
import com.example.weatherapp.data.mappers.forecast.ForecastDtoToEntityMapper
import com.example.weatherapp.data.mappers.forecast.ForecastDtoToEntityMapperImpl
import com.example.weatherapp.data.mappers.forecast.WeatherForecastEntityToDomainMapper
import com.example.weatherapp.data.mappers.forecast.WeatherForecastEntityToDomainMapperImpl
import com.example.weatherapp.data.mappers.location.LocationDomainEntityMapper
import com.example.weatherapp.data.mappers.location.LocationDomainEntityMapperImpl
import com.example.weatherapp.data.mappers.settings.AppThemeDomainEntityMapper
import com.example.weatherapp.data.mappers.settings.AppThemeDomainEntityMapperImpl
import com.example.weatherapp.data.mappers.settings.UnitsSystemDomainEntityMapper
import com.example.weatherapp.data.mappers.settings.UnitsSystemDomainEntityMapperImpl
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

    @Binds
    @Singleton
    abstract fun bindsAppThemeDomainEntityMapper(
        mapper: AppThemeDomainEntityMapperImpl
    ): AppThemeDomainEntityMapper

    @Binds
    @Singleton
    abstract fun bindsUnitsSystemDomainEntityMapper(
        mapper: UnitsSystemDomainEntityMapperImpl
    ): UnitsSystemDomainEntityMapper
}