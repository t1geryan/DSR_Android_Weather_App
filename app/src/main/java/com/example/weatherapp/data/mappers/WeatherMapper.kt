package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.remote.weather.dto.CurrentWeatherResponseDto
import com.example.weatherapp.domain.models.Weather
import com.example.weatherapp.utils.BidirectionalMapper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class for mapping [Weather] <-> [CurrentWeatherResponseDto]
 */
@Singleton
class WeatherMapper @Inject constructor() :
    BidirectionalMapper<Weather, CurrentWeatherResponseDto> {

    /**
     * Maps from [Weather] to [CurrentWeatherResponseDto]
     */
    override fun map(valueToMap: Weather): CurrentWeatherResponseDto {
        TODO("Not yet implemented")
    }

    /**
     * Maps from [CurrentWeatherResponseDto] to [Weather]
     */
    override fun reverseMap(valueToMap: CurrentWeatherResponseDto): Weather {
        TODO("Not yet implemented")
    }
}