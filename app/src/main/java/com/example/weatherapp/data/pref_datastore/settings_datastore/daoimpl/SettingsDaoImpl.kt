package com.example.weatherapp.data.pref_datastore.settings_datastore.daoimpl

import android.content.Context
import androidx.datastore.preferences.core.intPreferencesKey
import com.example.weatherapp.data.pref_datastore.dataStore
import com.example.weatherapp.data.pref_datastore.getValue
import com.example.weatherapp.data.pref_datastore.setValue
import com.example.weatherapp.data.pref_datastore.settings_datastore.dao.SettingsDao
import com.example.weatherapp.data.pref_datastore.settings_datastore.entities.AppThemeEntity
import com.example.weatherapp.data.pref_datastore.settings_datastore.entities.UnitsSystemEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDaoImpl @Inject constructor(
    @ApplicationContext context: Context
) : SettingsDao {

    private val dataStore = context.dataStore

    private val appThemeEntityValues = AppThemeEntity.values()
    private val unitsSystemEntityValues = UnitsSystemEntity.values()

    override suspend fun getUnitsSystem(): Flow<UnitsSystemEntity> = dataStore.getValue(
        UNITS_SYSTEM_PREF_KEY, UnitsSystemEntity.METRIC_SYSTEM.ordinal
    ).map {
        unitsSystemEntityValues[it]
    }

    override suspend fun setUnitsSystem(value: UnitsSystemEntity) =
        dataStore.setValue(UNITS_SYSTEM_PREF_KEY, value.ordinal)

    override suspend fun getAppTheme(): Flow<AppThemeEntity> = dataStore.getValue(
        APP_THEME_PREF_KEY, AppThemeEntity.NIGHT_THEME.ordinal
    ).map {
        appThemeEntityValues[it]
    }

    override suspend fun setAppTheme(value: AppThemeEntity) = dataStore.setValue(
        APP_THEME_PREF_KEY, value.ordinal
    )

    companion object {
        private val APP_THEME_PREF_KEY = intPreferencesKey("APP_THEME_PREF_KEY")
        private val UNITS_SYSTEM_PREF_KEY = intPreferencesKey("UNITS_SYSTEM_PREF_KEY")
    }
}