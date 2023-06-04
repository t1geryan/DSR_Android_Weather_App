package com.example.weatherapp.data.mappers.settings

import com.example.weatherapp.data.pref_datastore.settings_datastore.entities.AppThemeEntity
import com.example.weatherapp.domain.models.AppTheme
import org.junit.Assert
import org.junit.Test

class AppThemeDomainEntityMapperImplTest {

    @Test
    fun mapAppThemeDomainToEntityReturnsAppThemeEntity() {
        val mapperImpl = getMapperImpl()
        val expectedAppThemeEntity1 = AppThemeEntity.DAY_THEME
        val appTheme1 = AppTheme.DAY_THEME
        val expectedAppThemeEntity2 = AppThemeEntity.NIGHT_THEME
        val appTheme2 = AppTheme.NIGHT_THEME
        val expectedAppThemeEntity3 = AppThemeEntity.SYSTEM_THEME
        val appTheme3 = AppTheme.SYSTEM_THEME

        val actualAppThemeEntity1 = mapperImpl.map(appTheme1)
        val actualAppThemeEntity2 = mapperImpl.map(appTheme2)
        val actualAppThemeEntity3 = mapperImpl.map(appTheme3)

        Assert.assertEquals(expectedAppThemeEntity1, actualAppThemeEntity1)
        Assert.assertEquals(expectedAppThemeEntity2, actualAppThemeEntity2)
        Assert.assertEquals(expectedAppThemeEntity3, actualAppThemeEntity3)
    }

    @Test
    fun mapAppThemeEntityToDomainReturnsAppThemeDomainClass() {
        val mapperImpl = getMapperImpl()
        val expectedAppTheme1 = AppTheme.DAY_THEME
        val appThemeEntity1 = AppThemeEntity.DAY_THEME
        val expectedAppTheme2 = AppTheme.NIGHT_THEME
        val appThemeEntity2 = AppThemeEntity.NIGHT_THEME
        val expectedAppTheme3 = AppTheme.SYSTEM_THEME
        val appThemeEntity3 = AppThemeEntity.SYSTEM_THEME

        val actualAppTheme1 = mapperImpl.reverseMap(appThemeEntity1)
        val actualAppTheme2 = mapperImpl.reverseMap(appThemeEntity2)
        val actualAppTheme3 = mapperImpl.reverseMap(appThemeEntity3)

        Assert.assertEquals(expectedAppTheme1, actualAppTheme1)
        Assert.assertEquals(expectedAppTheme2, actualAppTheme2)
        Assert.assertEquals(expectedAppTheme3, actualAppTheme3)
    }

    private fun getMapperImpl() = AppThemeDomainEntityMapperImpl()
}