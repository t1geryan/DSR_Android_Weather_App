package com.example.weatherapp.presentation.ui.location_addition_map_screen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.LatLng
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
    private val locationTrackerRepository: LocationTrackerRepository
) : ViewModel() {

    private val _currentLocation =
        MutableSharedFlow<UiState<LatLng?>>()
    val currentLocation: SharedFlow<UiState<LatLng?>>
        get() = _currentLocation.asSharedFlow()

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
}