package com.example.weatherapp.data.mappers.current_weather

import com.example.weatherapp.testutils.createCurrentWeather
import com.example.weatherapp.testutils.createCurrentWeatherEntity
import org.junit.Assert
import org.junit.Test

class CurrentWeatherEntityToDomainMapperImplTest {

    @Test
    fun mapFromCurrentWeatherEntityToDomainReturnCurrentWeatherDomainClass() {
        val mapperImpl = CurrentWeatherEntityToDomainMapperImpl()
        val expectedCurrentWeather = createCurrentWeather(temperature = 23)
        val currentWeatherEntity = createCurrentWeatherEntity(temperature = 23.4f)

        val actualCurrentWeather = mapperImpl.map(currentWeatherEntity)

        Assert.assertEquals(expectedCurrentWeather, actualCurrentWeather)
    }
}