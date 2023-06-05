package com.example.weatherapp.domain.repositories

import com.example.weatherapp.domain.models.AppTheme
import com.example.weatherapp.domain.models.AppUnitsSystem
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getCurrentAppTheme(): Flow<AppTheme>

    suspend fun setAppTheme(value: AppTheme)

    fun getCurrentUnitsSystem(): Flow<AppUnitsSystem>

    suspend fun setUnitsSystem(value: AppUnitsSystem)
}