package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.databases.weather_database.entities.WeatherForecastEntity
import com.example.weatherapp.data.remote.weather.dto.WeatherForecastResponseDto
import com.example.weatherapp.utils.Mapper
import javax.inject.Inject

/**
 * Interface for mapping [WeatherForecastResponseDto] -> [WeatherForecastEntity]
 * @see Mapper
 */
interface ForecastDtoToEntityMapper : Mapper<WeatherForecastResponseDto, WeatherForecastEntity>

class ForecastDtoToEntityMapperImpl @Inject constructor() :
    ForecastDtoToEntityMapper {

    /**
     * Maps from [WeatherForecastResponseDto] to [WeatherForecastEntity]
     */
    override fun map(valueToMap: WeatherForecastResponseDto): WeatherForecastEntity {
        TODO("Not yet implemented")
    }
}