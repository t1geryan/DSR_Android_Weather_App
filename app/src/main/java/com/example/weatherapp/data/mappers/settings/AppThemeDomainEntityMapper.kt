package com.example.weatherapp.data.mappers.settings

import com.example.weatherapp.data.pref_datastore.settings_datastore.entities.AppThemeEntity
import com.example.weatherapp.domain.models.AppTheme
import com.example.weatherapp.utils.BidirectionalMapper
import javax.inject.Inject

/**
 * Interface for mapping [AppTheme] <-> [AppThemeEntity]
 * @see BidirectionalMapper
 */
interface AppThemeDomainEntityMapper : BidirectionalMapper<AppTheme, AppThemeEntity>

class AppThemeDomainEntityMapperImpl @Inject constructor() : AppThemeDomainEntityMapper {

    private val themeEntityValues = AppThemeEntity.values()

    /**
     * Maps from [AppTheme] to [AppThemeEntity]
     * @throws IllegalArgumentException when [AppTheme.themeKey] does not match any enum constant
     */
    override fun map(valueToMap: AppTheme): AppThemeEntity = themeEntityValues[valueToMap.themeKey]

    /**
     * Maps from [AppThemeEntity] to [AppTheme]
     */
    override fun reverseMap(valueToMap: AppThemeEntity): AppTheme = AppTheme(valueToMap.ordinal)
}