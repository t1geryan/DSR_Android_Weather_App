package com.example.weatherapp.presentation.ui.settings_screen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.AppTheme
import com.example.weatherapp.domain.models.AppUnitsSystem
import com.example.weatherapp.domain.repositories.SettingsRepository
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui_utils.collectUiStateFromFlow
import com.example.weatherapp.presentation.ui_utils.viewModelScopeIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _unitsSystemSetting = MutableStateFlow<UiState<AppUnitsSystem>>(UiState.Loading())
    val unitsSystemSetting: StateFlow<UiState<AppUnitsSystem>>
        get() = _unitsSystemSetting.asStateFlow()

    private val _appThemeSetting = MutableStateFlow<UiState<AppTheme>>(UiState.Loading())
    val appThemeSetting: StateFlow<UiState<AppTheme>>
        get() = _appThemeSetting.asStateFlow()

    init {
        collectUiStateFromFlow(_appThemeSetting) {
            settingsRepository.getCurrentAppTheme()
        }
        collectUiStateFromFlow(_unitsSystemSetting) {
            settingsRepository.getCurrentUnitsSystem()
        }
    }

    fun setAppThemeByOrdinal(ordinal: Int) = viewModelScopeIO.launch {
        settingsRepository.setAppTheme(getAppThemeByOrdinal(ordinal))
    }

    fun setAppUnitsSystemByOrdinal(ordinal: Int) = viewModelScopeIO.launch {
        settingsRepository.setUnitsSystem(getAppUnitsSystemByOrdinal(ordinal))
    }

    private fun getAppThemeByOrdinal(ordinal: Int): AppTheme {
        return APP_THEMES[ordinal % APP_THEMES.size]
    }

    private fun getAppUnitsSystemByOrdinal(ordinal: Int): AppUnitsSystem {
        return UNITS_SYSTEMS[ordinal % APP_THEMES.size]
    }

    companion object {
        private val APP_THEMES = AppTheme.values()
        private val UNITS_SYSTEMS = AppUnitsSystem.values()
    }
}