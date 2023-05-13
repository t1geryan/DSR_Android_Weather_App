package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.databases.weather_database.entities.CurrentWeatherEntity
import com.example.weatherapp.data.remote.weather.dto.CurrentWeatherResponseDto
import com.example.weatherapp.utils.Mapper
import javax.inject.Inject

/**
 * Interface for mapping [CurrentWeatherResponseDto] -> [CurrentWeatherEntity]
 * @see Mapper
 */
interface CurrentWeatherDtoToEntityMapper : Mapper<CurrentWeatherResponseDto, CurrentWeatherEntity>

class CurrentWeatherDtoToEntityMapperImpl @Inject constructor() :
    CurrentWeatherDtoToEntityMapper {

    /**
     * Maps from [CurrentWeatherResponseDto] to [CurrentWeatherEntity]
     */
    override fun map(valueToMap: CurrentWeatherResponseDto): CurrentWeatherEntity {
        TODO("Not yet implemented")
    }
}