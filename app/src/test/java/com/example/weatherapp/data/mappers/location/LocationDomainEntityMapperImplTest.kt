package com.example.weatherapp.data.mappers.location

import com.example.weatherapp.testutils.createLocationDomain
import com.example.weatherapp.testutils.createLocationEntity
import org.junit.Assert
import org.junit.Test

class LocationDomainEntityMapperImplTest {

    @Test
    fun mapLocationDomainClassToEntityReturnsLocationEntity() {
        val mapperImpl = getMapperImpl()
        val expectedLocationEntity = createLocationEntity(id = 43, name = "Voronezh")
        val locationDomain = createLocationDomain(id = 43, name = "Voronezh")

        val actualLocationEntity = mapperImpl.map(locationDomain)

        Assert.assertEquals(expectedLocationEntity, actualLocationEntity)
    }

    @Test
    fun mapLocationEntityToDomainReturnsLocationDomainClass() {
        val mapperImpl = getMapperImpl()
        val expectedLocationDomain = createLocationDomain()
        val locationEntity = createLocationEntity()

        val actualLocationDomain = mapperImpl.reverseMap(locationEntity)

        Assert.assertEquals(expectedLocationDomain, actualLocationDomain)
    }

    private fun getMapperImpl() = LocationDomainEntityMapperImpl()
}