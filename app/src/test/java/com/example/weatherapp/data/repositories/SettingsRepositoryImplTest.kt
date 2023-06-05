package com.example.weatherapp.data.repositories

import com.example.weatherapp.data.mappers.settings.AppThemeDomainEntityMapper
import com.example.weatherapp.data.mappers.settings.UnitsSystemDomainEntityMapper
import com.example.weatherapp.data.pref_datastore.settings_datastore.dao.SettingsDao
import com.example.weatherapp.data.pref_datastore.settings_datastore.entities.AppThemeEntity
import com.example.weatherapp.data.pref_datastore.settings_datastore.entities.UnitsSystemEntity
import com.example.weatherapp.domain.models.AppTheme
import com.example.weatherapp.domain.models.AppUnitsSystem
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SettingsRepositoryImplTest {

    @get:Rule
    val rule = MockKRule(this)

    @MockK
    lateinit var settingsDao: SettingsDao

    @MockK
    lateinit var appThemeDomainEntityMapper: AppThemeDomainEntityMapper

    @MockK
    lateinit var unitsSystemDomainEntityMapper: UnitsSystemDomainEntityMapper

    private lateinit var repositoryImpl: SettingsRepositoryImpl

    @Before
    fun setUp() {
        repositoryImpl = SettingsRepositoryImpl(
            settingsDao,
            appThemeDomainEntityMapper,
            unitsSystemDomainEntityMapper
        )
    }

    @Test
    fun getAppThemeReturnsFlowWithAppTheme() = runTest {
        every { settingsDao.getAppTheme() } returns flow {
            emit(AppThemeEntity.DAY_THEME)
        }
        every { appThemeDomainEntityMapper.reverseMap(AppThemeEntity.DAY_THEME) } returns AppTheme.DAY_THEME

        val actualAppThemeFlow = repositoryImpl.getCurrentAppTheme()

        Assert.assertEquals(AppTheme.DAY_THEME, actualAppThemeFlow.first())
    }

    @Test
    fun getAppUnitsSystemReturnsFlowWithUnitsSystem() = runTest {
        every { settingsDao.getUnitsSystem() } returns flow {
            emit(UnitsSystemEntity.METRIC_SYSTEM)
        }
        every { unitsSystemDomainEntityMapper.reverseMap(UnitsSystemEntity.METRIC_SYSTEM) } returns AppUnitsSystem.METRIC_SYSTEM

        val actualAppThemeFlow = repositoryImpl.getCurrentUnitsSystem()

        Assert.assertEquals(AppUnitsSystem.METRIC_SYSTEM, actualAppThemeFlow.first())
    }

    @Test
    fun setAppUnitsSystemCallsSettingsDaoSetUnitsSystem() = runTest {
        coEvery { settingsDao.setUnitsSystem(UnitsSystemEntity.METRIC_SYSTEM) } just runs
        every { unitsSystemDomainEntityMapper.map(AppUnitsSystem.METRIC_SYSTEM) } returns UnitsSystemEntity.METRIC_SYSTEM

        repositoryImpl.setUnitsSystem(AppUnitsSystem.METRIC_SYSTEM)

        coVerify(exactly = 1) {
            settingsDao.setUnitsSystem(UnitsSystemEntity.METRIC_SYSTEM)
        }
        confirmVerified(settingsDao)
    }

    @Test
    fun setAppThemeCallsSettingsDaoSetAppTheme() = runTest {
        coEvery { settingsDao.setAppTheme(AppThemeEntity.DAY_THEME) } just runs
        every { appThemeDomainEntityMapper.map(AppTheme.DAY_THEME) } returns AppThemeEntity.DAY_THEME

        repositoryImpl.setAppTheme(AppTheme.DAY_THEME)

        coVerify(exactly = 1) {
            settingsDao.setAppTheme(AppThemeEntity.DAY_THEME)
        }
        confirmVerified(settingsDao)
    }
}