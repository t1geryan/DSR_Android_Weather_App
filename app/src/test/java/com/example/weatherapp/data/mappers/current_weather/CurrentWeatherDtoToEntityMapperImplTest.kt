package com.example.weatherapp.data.mappers.current_weather

import com.example.weatherapp.testutils.createCurrentWeatherDto
import com.example.weatherapp.testutils.createCurrentWeatherEntity
import org.junit.Assert
import org.junit.Test

class CurrentWeatherDtoToEntityMapperImplTest {

    @Test
    fun mapCurrentWeatherDtoWithLocationIdReturnsCurrentWeatherEntityWithSameLocationId() {
        val mapperImpl = CurrentWeatherDtoToEntityMapperImpl()
        val expectedLocationId = 5L
        val expectedCurrentWeatherEntity =
            createCurrentWeatherEntity(locationId = expectedLocationId)
        val currentWeatherDto = createCurrentWeatherDto()

        val actualCurrentWeatherEntity =
            mapperImpl.mapWithParameter(currentWeatherDto, expectedLocationId)

        Assert.assertEquals(expectedCurrentWeatherEntity, actualCurrentWeatherEntity)
    }
}