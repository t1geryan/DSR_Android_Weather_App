package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.databases.weather_database.entities.CurrentWeatherEntity
import com.example.weatherapp.domain.models.Weather
import com.example.weatherapp.utils.BidirectionalMapper
import javax.inject.Inject

/**
 * Interface for mapping [Weather] <-> [CurrentWeatherEntity]
 * @see BidirectionalMapper
 */
interface CurrentWeatherDomainEntityMapper : BidirectionalMapper<Weather, CurrentWeatherEntity>

class CurrentWeatherDomainEntityMapperImpl @Inject constructor() :
    CurrentWeatherDomainEntityMapper {

    /**
     * Maps from [Weather] to [CurrentWeatherEntity]
     */
    override fun map(valueToMap: Weather): CurrentWeatherEntity {
        TODO("Not yet implemented")
    }

    /**
     * Maps from [CurrentWeatherEntity] to [Weather]
     */
    override fun reverseMap(valueToMap: CurrentWeatherEntity): Weather {
        TODO("Not yet implemented")
    }
}