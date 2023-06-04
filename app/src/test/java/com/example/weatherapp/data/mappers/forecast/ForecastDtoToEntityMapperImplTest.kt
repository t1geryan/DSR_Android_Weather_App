package com.example.weatherapp.data.mappers.forecast

import com.example.weatherapp.testutils.createForecastEntity
import com.example.weatherapp.testutils.createWeatherForecastDto
import org.junit.Assert
import org.junit.Test

class ForecastDtoToEntityMapperImplTest {

    @Test
    fun mapWeatherForecastDtoWithLocationIdReturnsListOfWeatherForecastEntityWithSameLocationId() {
        val mapperImpl = ForecastDtoToEntityMapperImpl()
        val expectedLocationId = 5L
        val timeStampsCount = 3
        val expectedWeatherForecastEntityList = List(timeStampsCount) {
            createForecastEntity(locationId = expectedLocationId)
        }
        val weatherForecastDto = createWeatherForecastDto(timestampsCount = timeStampsCount)

        val actualWeatherForecastEntityList =
            mapperImpl.mapWithParameter(weatherForecastDto, expectedLocationId)

        Assert.assertArrayEquals(
            expectedWeatherForecastEntityList.toTypedArray(),
            actualWeatherForecastEntityList.toTypedArray()
        )
    }
}