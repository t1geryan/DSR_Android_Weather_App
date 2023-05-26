package com.example.weatherapp.presentation.ui.location_addition_map_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.LatLng
import com.example.weatherapp.domain.repositories.AutocompleteDataProviderRepository
import com.example.weatherapp.domain.repositories.LocationTrackerRepository
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui_utils.viewModelScopeIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationAdditionMapViewModel @Inject constructor(
    private val locationTrackerRepository: LocationTrackerRepository,
    private val autocompleteDataProviderRepository: AutocompleteDataProviderRepository,
) : ViewModel() {

    private val _currentLocation =
        MutableSharedFlow<UiState<LatLng>>()
    val currentLocation: SharedFlow<UiState<LatLng>>
        get() = _currentLocation.asSharedFlow()

    private val _autocompleteData =
        MutableSharedFlow<List<String>>()
    val autocompleteData: SharedFlow<List<String>>
        get() = _autocompleteData.asSharedFlow()

    fun getCurrentLocation() {
        viewModelScopeIO.launch {
            _currentLocation.emit(UiState.Loading())
            try {
                locationTrackerRepository.getCurrentLocation {
                    viewModelScopeIO.launch {
                        _currentLocation.emit(UiState.Success(it))
                    }
                }
            } catch (e: Exception) {
                _currentLocation.emit(UiState.Error(e))
            }
        }
    }

    fun getPlacesAutocompleteDataByInput(input: String) {
        viewModelScopeIO.launch {
            try {
                _autocompleteData.emit(autocompleteDataProviderRepository.getAutocompleteData(input))
            } catch (e: Exception) {
                Log.e("AutocompleteError", "Exception $e caught while getting autocomplete data")
            }
        }
    }
}