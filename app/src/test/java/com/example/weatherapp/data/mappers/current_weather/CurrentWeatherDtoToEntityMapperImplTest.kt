package com.example.weatherapp.data.mappers.current_weather

import com.example.weatherapp.data.remote.weather.dto.CurrentMain
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
            createCurrentWeatherEntity(locationId = expectedLocationId, temperature = 43.12f)
        val currentWeatherDto =
            createCurrentWeatherDto(main = CurrentMain(43.12, 0.0, 0.0, 0.0, 0, 0))

        val actualCurrentWeatherEntity =
            mapperImpl.mapWithParameter(currentWeatherDto, expectedLocationId)

        Assert.assertEquals(expectedCurrentWeatherEntity, actualCurrentWeatherEntity)
    }
}