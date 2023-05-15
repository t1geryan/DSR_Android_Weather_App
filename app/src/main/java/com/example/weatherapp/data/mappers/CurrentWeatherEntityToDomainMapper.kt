package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.databases.location_database.entities.CurrentWeatherEntity
import com.example.weatherapp.domain.models.CurrentWeather
import com.example.weatherapp.utils.Mapper
import javax.inject.Inject

/**
 * Interface for mapping [CurrentWeatherEntity] -> [CurrentWeather]
 * @see Mapper
 */
interface CurrentWeatherEntityToDomainMapper : Mapper<CurrentWeatherEntity, CurrentWeather>

class CurrentWeatherEntityToDomainMapperImpl @Inject constructor() :
    CurrentWeatherEntityToDomainMapper {

    /**
     * Maps from [CurrentWeatherEntity] to [CurrentWeather]
     */
    override fun map(valueToMap: CurrentWeatherEntity): CurrentWeather = with(valueToMap) {
        CurrentWeather(
            weatherConditionId,
            weatherName,
            weatherDescription,
            weatherIconName,
            temperature.toInt(),
            pressure,
            humidity,
            windSpeed,
            windDegree,
            dateTimeUnixUtc,
            shiftFromUtcSec
        )
    }

}