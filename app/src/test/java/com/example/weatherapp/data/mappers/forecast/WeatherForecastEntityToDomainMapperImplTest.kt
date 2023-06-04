package com.example.weatherapp.data.mappers.forecast

import com.example.weatherapp.testutils.createForecastDomain
import com.example.weatherapp.testutils.createForecastEntity
import org.junit.Assert
import org.junit.Test

class WeatherForecastEntityToDomainMapperImplTest {

    @Test
    fun mapForecastEntityToDomainReturnForecastDomainClass() {
        val mapperImpl = WeatherForecastEntityToDomainMapperImpl()
        val expectedForecast = createForecastDomain()
        val forecastEntity = createForecastEntity()

        val actualForecast = mapperImpl.map(forecastEntity)

        Assert.assertEquals(expectedForecast, actualForecast)
    }
}