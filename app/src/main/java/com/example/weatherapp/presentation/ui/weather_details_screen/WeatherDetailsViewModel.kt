package com.example.weatherapp.presentation.ui.weather_details_screen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.LocationWeather
import com.example.weatherapp.domain.repositories.LocationsWeatherRepository
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui_utils.collectUiStateFromFlow
import com.example.weatherapp.presentation.ui_utils.viewModelScopeIO
import com.github.mikephil.charting.data.Entry
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
) : ViewModel() {

    private val _locationWeather: MutableStateFlow<UiState<LocationWeather>> =
        MutableStateFlow(UiState.Loading())
    val locationWeather: StateFlow<UiState<LocationWeather>>
        get() = _locationWeather.asStateFlow()

    init {
        fetchLocationWeather()
    }

    fun fetchLocationWeather() {
        collectUiStateFromFlow(_locationWeather) {
            locationsWeatherRepository.getLocationWeatherById(locationId)
        }
    }

    fun deleteLocationById(locationId: Long) = viewModelScopeIO.launch {
        locationsWeatherRepository.deleteLocationById(locationId)
    }

    fun createTemperatureChartEntries(locationWeather: LocationWeather): List<Entry> {
        val dataValues = mutableListOf<Entry>()
        // first entry will be current weather temperature
        dataValues += (Entry(0.0f, locationWeather.currentWeather.temperature.toFloat()))
        val forecasts = locationWeather.weatherForecasts
        // next entries will be each forecast temperature
        for (i in forecasts.indices) {
            dataValues += Entry((i + 1).toFloat(), forecasts[i].temperature.toFloat())
        }
        return dataValues
    }

    @AssistedFactory
    interface Factory {
        fun create(locationId: Long): WeatherDetailsViewModel
    }
}