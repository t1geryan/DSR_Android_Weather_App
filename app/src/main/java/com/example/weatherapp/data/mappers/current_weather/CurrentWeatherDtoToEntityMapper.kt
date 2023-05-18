package com.example.weatherapp.data.mappers.current_weather

import com.example.weatherapp.data.databases.location_database.entities.CurrentWeatherEntity
import com.example.weatherapp.data.remote.weather.dto.CurrentWeatherResponseDto
import com.example.weatherapp.utils.Mapper
import com.example.weatherapp.utils.ParameterizedMapper
import javax.inject.Inject

/**
 * Interface for mapping [CurrentWeatherResponseDto] -> [CurrentWeatherEntity]
 * @see Mapper
 */
interface CurrentWeatherDtoToEntityMapper :
    ParameterizedMapper<CurrentWeatherResponseDto, CurrentWeatherEntity, Long>

class CurrentWeatherDtoToEntityMapperImpl @Inject constructor() :
    CurrentWeatherDtoToEntityMapper {

    /**
     * Maps from [CurrentWeatherResponseDto] to [CurrentWeatherEntity]
     */
    override fun mapWithParameter(
        valueToMap: CurrentWeatherResponseDto,
        parameter: Long,
    ): CurrentWeatherEntity = with(valueToMap) {
        val currentWeather = weather.first()
        CurrentWeatherEntity(
            locationId = parameter,
            weatherConditionId = currentWeather.id,
            weatherName = currentWeather.main,
            weatherDescription = currentWeather.description,
            weatherIconName = currentWeather.icon,
            temperature = main.temp.toFloat(),
            pressure = main.pressure,
            humidity = main.humidity,
            windSpeed = wind.speed.toFloat(),
            windDegree = wind.deg,
            dateTimeUnixUtc = dataTimeUtc,
            shiftFromUtcSec = timezone.toLong()
        )
    }
}