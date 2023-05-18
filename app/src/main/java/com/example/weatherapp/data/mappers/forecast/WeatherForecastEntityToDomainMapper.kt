package com.example.weatherapp.data.mappers.forecast

import com.example.weatherapp.data.databases.location_database.entities.WeatherForecastEntity
import com.example.weatherapp.domain.models.CurrentWeather
import com.example.weatherapp.domain.models.Forecast
import com.example.weatherapp.utils.BidirectionalMapper
import com.example.weatherapp.utils.Mapper
import javax.inject.Inject

/**
 * Interface for mapping [CurrentWeather] <-> [Forecast]
 * @see BidirectionalMapper
 */
interface WeatherForecastEntityToDomainMapper : Mapper<WeatherForecastEntity, Forecast>

class WeatherForecastEntityToDomainMapperImpl @Inject constructor() :
    WeatherForecastEntityToDomainMapper {

    /**
     * Maps from [WeatherForecastEntity] to [Forecast]
     */
    override fun map(valueToMap: WeatherForecastEntity): Forecast = with(valueToMap) {
        Forecast(
            weatherIconName,
            temperature.toInt(),
            dateTimeUnixUTC,
            shiftFromUtcSec,
        )
    }
}