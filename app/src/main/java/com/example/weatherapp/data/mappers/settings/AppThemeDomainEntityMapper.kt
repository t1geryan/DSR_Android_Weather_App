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

    /**
     * Maps from [AppTheme] to [AppThemeEntity]
     */
    override fun map(valueToMap: AppTheme): AppThemeEntity = when (valueToMap) {
        AppTheme.DAY_THEME -> AppThemeEntity.DAY_THEME
        AppTheme.NIGHT_THEME -> AppThemeEntity.NIGHT_THEME
        AppTheme.SYSTEM_THEME -> AppThemeEntity.SYSTEM_THEME
    }

    /**
     * Maps from [AppThemeEntity] to [AppTheme]
     */
    override fun reverseMap(valueToMap: AppThemeEntity): AppTheme = when (valueToMap) {
        AppThemeEntity.DAY_THEME -> AppTheme.DAY_THEME
        AppThemeEntity.NIGHT_THEME -> AppTheme.NIGHT_THEME
        AppThemeEntity.SYSTEM_THEME -> AppTheme.SYSTEM_THEME
    }
}