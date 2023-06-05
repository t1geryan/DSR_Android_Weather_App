package com.example.weatherapp.data.mappers.forecast

import com.example.weatherapp.testutils.createForecastDomain
import com.example.weatherapp.testutils.createForecastEntity
import org.junit.Assert
import org.junit.Test

class WeatherForecastEntityToDomainMapperImplTest {

    @Test
    fun mapForecastEntityToDomainReturnForecastDomainClass() {
        val mapperImpl = WeatherForecastEntityToDomainMapperImpl()
        val expectedForecast = createForecastDomain(temperature = 34, dateTimeUnixUtc = 12345)
        val forecastEntity = createForecastEntity(temperature = 34.2f, dateTimeUnixUtc = 12345)

        val actualForecast = mapperImpl.map(forecastEntity)

        Assert.assertEquals(expectedForecast, actualForecast)
    }
}