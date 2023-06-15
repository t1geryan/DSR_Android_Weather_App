package com.example.weatherapp.presentation.ui.location_addition_wizard.location_addition_map_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.GeocodingResult
import com.example.weatherapp.domain.models.LatLng
import com.example.weatherapp.domain.repositories.GeocoderRepository
import com.example.weatherapp.domain.repositories.LocationTrackerRepository
import com.example.weatherapp.domain.repositories.LocationsAutocompleteDataProviderRepository
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui.location_addition_wizard.location_addition_map_screen.state.LocationAdditionState
import com.example.weatherapp.presentation.ui_utils.emitUiState
import com.example.weatherapp.presentation.ui_utils.tryEmitFlow
import com.example.weatherapp.presentation.ui_utils.viewModelScopeIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationAdditionMapViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val locationTrackerRepository: LocationTrackerRepository,
    private val locationsAutocompleteDataProviderRepository: LocationsAutocompleteDataProviderRepository,
    private val geocoderRepository: GeocoderRepository,
) : ViewModel() {

    private val _currentLocation =
        MutableSharedFlow<UiState<LatLng>>()
    val currentLocation: SharedFlow<UiState<LatLng>>
        get() = _currentLocation.asSharedFlow()

    private val _autocompleteData =
        MutableSharedFlow<UiState<List<String>>>()
    val autocompleteData: SharedFlow<UiState<List<String>>>
        get() = _autocompleteData.asSharedFlow()

    private val _geocodingResult =
        MutableSharedFlow<GeocodingResult?>()
    val geocodingResult: SharedFlow<GeocodingResult?>
        get() = _geocodingResult.asSharedFlow()

    val locationAdditionState =
        savedStateHandle.getStateFlow(STATE_KEY, LocationAdditionState(INITIAL_LOCATION))

    fun updateLocationAdditionState(locationAdditionState: LocationAdditionState) {
        savedStateHandle[STATE_KEY] = locationAdditionState
    }

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
        emitUiState(_autocompleteData) {
            _autocompleteData.emit(
                UiState.Success(
                    locationsAutocompleteDataProviderRepository.getAutocompleteData(
                        input
                    )
                )
            )
        }

    }

    fun getCoordinatesByLocationName(locationName: String) {
        if (locationName.isNotBlank()) {
            tryEmitFlow {
                geocoderRepository.getCoordinatesByLocationName(locationName) { geocodingResult ->
                    viewModelScopeIO.launch {
                        _geocodingResult.emit(geocodingResult)
                    }
                }
            }
        }
    }

    companion object {
        private const val STATE_KEY = "STATE_KEY"
        private val INITIAL_LOCATION = LatLng(55.7515f, 37.5738f)
    }
}