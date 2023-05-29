package com.example.weatherapp.presentation.ui.main_activity

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.AppTheme
import com.example.weatherapp.domain.models.AppUnitsSystem
import com.example.weatherapp.domain.repositories.SettingsRepository
import com.example.weatherapp.presentation.ui_utils.tryEmitFlow
import com.example.weatherapp.presentation.ui_utils.viewModelScopeIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _unitsSystemSetting = MutableStateFlow(AppUnitsSystem.METRIC_SYSTEM)
    val unitsSystemSetting: StateFlow<AppUnitsSystem>
        get() = _unitsSystemSetting.asStateFlow()

    private val _appThemeSetting = MutableStateFlow(AppTheme.SYSTEM_THEME)
    val appThemeSetting: StateFlow<AppTheme>
        get() = _appThemeSetting.asStateFlow()

    init {
        fetchUnitsSystemSetting()
        fetchAppThemeSetting()
    }

    private fun fetchUnitsSystemSetting() {
        tryEmitFlow {
            settingsRepository.getCurrentUnitsSystem().collect {
                _unitsSystemSetting.value = it
            }
        }
    }

    private fun fetchAppThemeSetting() {
        tryEmitFlow {
            settingsRepository.getCurrentAppTheme().collect {
                _appThemeSetting.value = it
            }
        }
    }

    fun setAppUnitsSystem(value: AppUnitsSystem) {
        viewModelScopeIO.launch {
            settingsRepository.setUnitsSystem(value)
        }
    }
}