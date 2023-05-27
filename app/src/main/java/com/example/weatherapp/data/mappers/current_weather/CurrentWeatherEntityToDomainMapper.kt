package com.example.weatherapp.data.mappers.current_weather

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
            weatherConditionId = weatherConditionId,
            weatherName = weatherName,
            weatherDescription = weatherDescription,
            weatherIconName = weatherIconName,
            temperature = temperature.toInt(),
            pressure = pressure,
            humidity = humidity,
            windSpeed = windSpeed,
            windDirectionDegrees = windDegree,
            dateTimeUnixUtc = dateTimeUnixUtc,
            shiftFromUtcSeconds = shiftFromUtcSec
        )
    }

}