package com.example.weatherapp.presentation.ui.main_activity

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.AppTheme
import com.example.weatherapp.domain.models.AppUnitsSystem
import com.example.weatherapp.domain.repositories.SettingsRepository
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui_utils.collectUiState
import com.example.weatherapp.presentation.ui_utils.viewModelScopeIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _unitsSystemSetting = MutableStateFlow<UiState<AppUnitsSystem>>(UiState.Loading())
    val unitsSystemSetting: StateFlow<UiState<AppUnitsSystem>>
        get() = _unitsSystemSetting.asStateFlow()

    private val _appThemeSetting = MutableStateFlow<UiState<AppTheme>>(UiState.Loading())
    val appThemeSetting: StateFlow<UiState<AppTheme>>
        get() = _appThemeSetting.asStateFlow()

    init {
        viewModelScopeIO.launch {
            collectUiState(
                settingsRepository.getCurrentUnitsSystem(),
                _unitsSystemSetting
            )
            collectUiState(
                settingsRepository.getCurrentAppTheme(),
                _appThemeSetting
            )
        }
    }

    fun setAppUnitsSystem(value: AppUnitsSystem) {
        viewModelScopeIO.launch {
            settingsRepository.setUnitsSystem(value)
        }
    }
}