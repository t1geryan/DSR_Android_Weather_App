package com.example.weatherapp.data.pref_datastore.settings_datastore.dao

import com.example.weatherapp.data.pref_datastore.settings_datastore.entities.AppThemeEntity
import com.example.weatherapp.data.pref_datastore.settings_datastore.entities.UnitsSystemEntity
import kotlinx.coroutines.flow.Flow

interface SettingsDao {

    suspend fun getUnitsSystem(): Flow<UnitsSystemEntity>

    suspend fun setUnitsSystem(value: UnitsSystemEntity)

    suspend fun getAppTheme(): Flow<AppThemeEntity>

    suspend fun setAppTheme(value: AppThemeEntity)
}