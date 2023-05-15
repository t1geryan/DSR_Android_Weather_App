package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.databases.location_database.entities.WeatherForecastEntity
import com.example.weatherapp.data.remote.weather.dto.WeatherForecastResponseDto
import com.example.weatherapp.utils.Mapper
import com.example.weatherapp.utils.ParameterizedMapper
import javax.inject.Inject

/**
 * Interface for mapping [WeatherForecastResponseDto] -> [WeatherForecastEntity]
 * @see Mapper
 */
interface ForecastDtoToEntityMapper :
    ParameterizedMapper<WeatherForecastResponseDto, List<WeatherForecastEntity>, Long>

class ForecastDtoToEntityMapperImpl @Inject constructor() :
    ForecastDtoToEntityMapper {

    /**
     * Maps from [WeatherForecastResponseDto] to [List] of [WeatherForecastEntity]
     */
    override fun mapWithParameter(
        valueToMap: WeatherForecastResponseDto,
        parameter: Long
    ): List<WeatherForecastEntity> = with(valueToMap) {
        List(timestampsCount) {
            with(list[it]) {
                val currentWeather = weather.first()
                WeatherForecastEntity(
                    locationId = parameter,
                    weatherConditionId = currentWeather.id,
                    weatherName = currentWeather.main,
                    weatherDescription = currentWeather.description,
                    weatherIconName = currentWeather.icon,
                    temperature = main.temp.toFloat(),
                    pressure = main.pressure,
                    humidity = main.humidity,
                    windSpeed = wind.speed.toFloat(),
                    dateTimeUnixUTC = dt,
                    shiftFromUtcSec = city.timezoneUtc
                )
            }
        }
    }

}