package com.example.weatherapp.di

import com.example.weatherapp.data.pref_datastore.settings_datastore.dao.SettingsDao
import com.example.weatherapp.data.pref_datastore.settings_datastore.daoimpl.SettingsDaoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataStoreModule {

    @Binds
    @Singleton
    abstract fun bindSettingsDao(
        settingsDaoImpl: SettingsDaoImpl
    ): SettingsDao
}