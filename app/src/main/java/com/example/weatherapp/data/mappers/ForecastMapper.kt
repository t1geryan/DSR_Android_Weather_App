package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.remote.weather.dto.CurrentWeatherResponseDto
import com.example.weatherapp.data.remote.weather.dto.WeatherForecastResponseDto
import com.example.weatherapp.domain.models.Weather
import com.example.weatherapp.utils.BidirectionalMapper
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class for mapping [Weather] <-> [CurrentWeatherResponseDto]
 */
@Singleton
class ForecastMapper @Inject constructor() :
    BidirectionalMapper<Weather, WeatherForecastResponseDto> {

    /**
     * Maps from [Weather] to [WeatherForecastResponseDto]
     */
    override fun map(valueToMap: Weather): WeatherForecastResponseDto {
        TODO("Not yet implemented")
    }

    /**
     * Maps from [WeatherForecastResponseDto] to [Weather]
     */
    override fun reverseMap(valueToMap: WeatherForecastResponseDto): Weather {
        TODO("Not yet implemented")
    }
}