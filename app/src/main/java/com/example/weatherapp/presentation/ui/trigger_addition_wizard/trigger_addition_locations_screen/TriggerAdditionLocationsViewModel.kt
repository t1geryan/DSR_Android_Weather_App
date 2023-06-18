package com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_locations_screen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.repositories.LocationsWeatherRepository
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_locations_screen.adapter.locationcheckitem.LocationCheckItem
import com.example.weatherapp.presentation.ui_utils.collectUiStateFromFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class TriggerAdditionLocationsViewModel @Inject constructor(
    private val locationsWeatherRepository: LocationsWeatherRepository
) : ViewModel() {

    private val _locationCheckItems =
        MutableStateFlow<UiState<List<LocationCheckItem>>>(UiState.Loading())
    val locationCheckItems: StateFlow<UiState<List<LocationCheckItem>>>
        get() = _locationCheckItems.asStateFlow()

    init {
        fetchLocationCheckItems()
    }

    fun fetchLocationCheckItems() {
        collectUiStateFromFlow(_locationCheckItems) {
            locationsWeatherRepository.getAllLocations(false).map { list ->
                list.map { location ->
                    LocationCheckItem(location, false)
                }
            }
        }
    }
}