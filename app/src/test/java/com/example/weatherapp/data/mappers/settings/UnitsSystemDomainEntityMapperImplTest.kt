package com.example.weatherapp.data.mappers.settings

import com.example.weatherapp.data.pref_datastore.settings_datastore.entities.UnitsSystemEntity
import com.example.weatherapp.domain.models.AppUnitsSystem
import org.junit.Assert
import org.junit.Test

class UnitsSystemDomainEntityMapperImplTest {

    @Test
    fun mapUnitsSystemDomainToEntityReturnsUnitsSystemEntity() {
        val mapperImpl = getMapperImpl()
        val expectedUnitsSystemEntity1 = UnitsSystemEntity.METRIC_SYSTEM
        val unitsSystem1 = AppUnitsSystem.METRIC_SYSTEM
        val expectedUnitsSystemEntity2 = UnitsSystemEntity.IMPERIAL_SYSTEM
        val unitsSystem2 = AppUnitsSystem.IMPERIAL_SYSTEM

        val actualUnitsSystemEntity1 = mapperImpl.map(unitsSystem1)
        val actualUnitsSystemEntity2 = mapperImpl.map(unitsSystem2)

        Assert.assertEquals(expectedUnitsSystemEntity1, actualUnitsSystemEntity1)
        Assert.assertEquals(expectedUnitsSystemEntity2, actualUnitsSystemEntity2)
    }

    @Test
    fun mapUnitsSystemEntityToDomainReturnsUnitsSystemDomainClass() {
        val mapperImpl = getMapperImpl()
        val expectedUnitsSystem1 = AppUnitsSystem.METRIC_SYSTEM
        val unitsSystemEntity1 = UnitsSystemEntity.METRIC_SYSTEM
        val expectedUnitsSystem2 = AppUnitsSystem.IMPERIAL_SYSTEM
        val unitsSystemEntity2 = UnitsSystemEntity.IMPERIAL_SYSTEM

        val actualUnitsSystem1 = mapperImpl.reverseMap(unitsSystemEntity1)
        val actualUnitsSystem2 = mapperImpl.reverseMap(unitsSystemEntity2)

        Assert.assertEquals(expectedUnitsSystem1, actualUnitsSystem1)
        Assert.assertEquals(expectedUnitsSystem2, actualUnitsSystem2)
    }

    private fun getMapperImpl() = UnitsSystemDomainEntityMapperImpl()
}