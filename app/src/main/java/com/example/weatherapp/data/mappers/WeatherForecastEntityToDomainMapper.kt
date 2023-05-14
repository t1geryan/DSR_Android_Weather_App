package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.databases.location_database.entities.WeatherForecastEntity
import com.example.weatherapp.domain.models.Weather
import com.example.weatherapp.utils.BidirectionalMapper
import com.example.weatherapp.utils.Mapper
import javax.inject.Inject

/**
 * Interface for mapping [Weather] <-> [WeatherForecastEntity]
 * @see BidirectionalMapper
 */
interface WeatherForecastEntityToDomainMapper : Mapper<WeatherForecastEntity, Weather>

class WeatherForecastEntityToDomainMapperImpl @Inject constructor() :
    WeatherForecastEntityToDomainMapper {

    /**
     * Maps from [WeatherForecastEntity] to [Weather]
     */
    override fun map(valueToMap: WeatherForecastEntity): Weather = with(valueToMap) {
        Weather(
            weatherConditionId,
            weatherName,
            weatherDescription,
            weatherIconName,
            temperature.toInt(),
            pressure,
            humidity,
            windSpeed,
            dateTimeUnixUTC,
            shiftFromUtcSec,
        )
    }
}