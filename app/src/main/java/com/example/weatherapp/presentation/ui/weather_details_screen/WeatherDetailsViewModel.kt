package com.example.weatherapp.presentation.ui.weather_details_screen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.AppUnitsSystem
import com.example.weatherapp.domain.models.LocationWeather
import com.example.weatherapp.domain.repositories.LocationsWeatherRepository
import com.example.weatherapp.domain.repositories.SettingsRepository
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui_utils.collectUiState
import com.example.weatherapp.presentation.ui_utils.viewModelScopeIO
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherDetailsViewModel @AssistedInject constructor(
    @Assisted private val locationId: Long,
    private val locationsWeatherRepository: LocationsWeatherRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _locationWeather: MutableStateFlow<UiState<LocationWeather>> =
        MutableStateFlow(UiState.Loading())
    val locationWeather: StateFlow<UiState<LocationWeather>>
        get() = _locationWeather.asStateFlow()

    private val _unitsSystemSetting = MutableStateFlow<UiState<AppUnitsSystem>>(UiState.Loading())
    val unitsSystemSetting: StateFlow<UiState<AppUnitsSystem>>
        get() = _unitsSystemSetting.asStateFlow()

    init {
        viewModelScopeIO.launch {
            fetchLocationWeather()
            collectUiState(
                settingsRepository.getCurrentUnitsSystem(),
                _unitsSystemSetting
            )
        }
    }

    fun fetchLocationWeather() {
        viewModelScopeIO.launch {
            collectUiState(
                locationsWeatherRepository.getLocationWeatherById(locationId),
                _locationWeather,
            )
        }
    }


    fun deleteLocationById(locationId: Long) = viewModelScopeIO.launch {
        locationsWeatherRepository.deleteLocationById(locationId)
    }

    @AssistedFactory
    interface Factory {
        fun create(locationId: Long): WeatherDetailsViewModel
    }
}