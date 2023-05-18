package com.example.weatherapp.data.repositories

import com.example.weatherapp.data.mappers.settings.AppThemeDomainEntityMapper
import com.example.weatherapp.data.mappers.settings.UnitsSystemDomainEntityMapper
import com.example.weatherapp.data.pref_datastore.settings_datastore.dao.SettingsDao
import com.example.weatherapp.domain.models.AppTheme
import com.example.weatherapp.domain.models.AppUnitsSystem
import com.example.weatherapp.domain.repositories.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepositoryImpl @Inject constructor(
    private val settingsDao: SettingsDao,
    private val appThemeDomainEntityMapper: AppThemeDomainEntityMapper,
    private val unitsSystemDomainEntityMapper: UnitsSystemDomainEntityMapper
) : SettingsRepository {
    override suspend fun getCurrentAppTheme(): Flow<AppTheme> =
        settingsDao.getAppTheme().map {
            appThemeDomainEntityMapper.reverseMap(it)
        }


    override suspend fun setAppTheme(value: AppTheme) {
        settingsDao.setAppTheme(
            appThemeDomainEntityMapper.map(value)
        )
    }

    override suspend fun getCurrentUnitsSystem(): Flow<AppUnitsSystem> =
        settingsDao.getUnitsSystem().map {
            unitsSystemDomainEntityMapper.reverseMap(it)
        }

    override suspend fun setUnitsSystem(value: AppUnitsSystem) {
        settingsDao.setUnitsSystem(
            unitsSystemDomainEntityMapper.map(value)
        )
    }
}