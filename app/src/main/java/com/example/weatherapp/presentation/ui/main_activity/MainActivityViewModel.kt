package com.example.weatherapp.presentation.ui.main_activity

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.AppTheme
import com.example.weatherapp.domain.models.AppUnitsSystem
import com.example.weatherapp.domain.repositories.SettingsRepository
import com.example.weatherapp.presentation.ui_utils.tryEmitFlow
import com.example.weatherapp.presentation.ui_utils.viewModelScopeIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _unitsSystemSetting = MutableSharedFlow<AppUnitsSystem>()
    val unitsSystemSetting: SharedFlow<AppUnitsSystem>
        get() = _unitsSystemSetting.asSharedFlow()

    private val _appThemeSetting = MutableSharedFlow<AppTheme>()
    val appThemeSetting: SharedFlow<AppTheme>
        get() = _appThemeSetting.asSharedFlow()

    init {
        fetchUnitsSystemSetting()
        fetchAppThemeSetting()
    }

    private fun fetchUnitsSystemSetting() {
        tryEmitFlow {
            settingsRepository.getCurrentUnitsSystem().collect {
                _unitsSystemSetting.emit(it)
            }
        }
    }

    private fun fetchAppThemeSetting() {
        tryEmitFlow {
            settingsRepository.getCurrentAppTheme().collect {
                _appThemeSetting.emit(it)
            }
        }
    }

    fun setAppUnitsSystem(value: AppUnitsSystem) {
        viewModelScopeIO.launch {
            settingsRepository.setUnitsSystem(value)
        }
    }
}