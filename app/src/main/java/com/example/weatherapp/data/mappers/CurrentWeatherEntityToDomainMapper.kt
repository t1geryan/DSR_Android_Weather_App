package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.databases.location_database.entities.CurrentWeatherEntity
import com.example.weatherapp.domain.models.Weather
import com.example.weatherapp.utils.Mapper
import javax.inject.Inject

/**
 * Interface for mapping [CurrentWeatherEntity] -> [Weather]
 * @see Mapper
 */
interface CurrentWeatherEntityToDomainMapper : Mapper<CurrentWeatherEntity, Weather>

class CurrentWeatherEntityToDomainMapperImpl @Inject constructor() :
    CurrentWeatherEntityToDomainMapper {

    /**
     * Maps from [CurrentWeatherEntity] to [Weather]
     */
    override fun map(valueToMap: CurrentWeatherEntity): Weather = with(valueToMap) {
        Weather(
            weatherConditionId,
            weatherName,
            weatherDescription,
            weatherIconName,
            temperature.toInt(),
            pressure,
            humidity,
            windSpeed,
            dateTimeUnixUtc,
            shiftFromUtcSec
        )
    }

}