package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.databases.weather_database.entities.WeatherForecastEntity
import com.example.weatherapp.domain.models.Weather
import com.example.weatherapp.utils.BidirectionalMapper
import javax.inject.Inject

/**
 * Interface for mapping [Weather] <-> [WeatherForecastEntity]
 * @see BidirectionalMapper
 */
interface WeatherForecastDomainEntityMapper : BidirectionalMapper<Weather, WeatherForecastEntity>

class WeatherForecastDomainEntityMapperImpl @Inject constructor() :
    WeatherForecastDomainEntityMapper {

    /**
     * Maps from [Weather] to [WeatherForecastEntity]
     */
    override fun map(valueToMap: Weather): WeatherForecastEntity {
        TODO("Not yet implemented")
    }

    /**
     * Maps from [WeatherForecastEntity] to [Weather]
     */
    override fun reverseMap(valueToMap: WeatherForecastEntity): Weather {
        TODO("Not yet implemented")
    }
}