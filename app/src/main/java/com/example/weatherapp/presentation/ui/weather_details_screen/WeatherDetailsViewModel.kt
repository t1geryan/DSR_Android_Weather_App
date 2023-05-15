package com.example.weatherapp.presentation.ui.weather_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.models.LocationWeather
import com.example.weatherapp.domain.repositories.LocationsWeatherRepository
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui_utils.collectUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class WeatherDetailsViewModel @AssistedInject constructor(
    @Assisted private val locationId: Long,
    private val locationsWeatherRepository: LocationsWeatherRepository,
) : ViewModel() {

    private val _locationWeather: MutableStateFlow<UiState<LocationWeather>> =
        MutableStateFlow(UiState.Loading())
    val locationWeather: StateFlow<UiState<LocationWeather>>
        get() = _locationWeather.asStateFlow()

    init {
        viewModelScope.launch {
            collectUiState(
                fetchLocationWeatherById(),
                _locationWeather,
            )
        }
    }

    private suspend fun fetchLocationWeatherById(): Flow<LocationWeather> =
        locationsWeatherRepository.getLocationWeatherById(locationId)

    fun deleteLocationById(locationId: Long) = viewModelScope.launch {
        locationsWeatherRepository.deleteLocationById(locationId)
    }

    @AssistedFactory
    interface Factory {
        fun create(locationId: Long): WeatherDetailsViewModel
    }
}