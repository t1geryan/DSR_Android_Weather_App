package com.example.weatherapp.presentation.ui.location_addition_map_screen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.LatLng
import com.example.weatherapp.domain.repositories.LocationTrackerRepository
import com.example.weatherapp.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LocationAdditionMapViewModel @Inject constructor(
    private val locationTrackerRepository: LocationTrackerRepository
) : ViewModel() {

    private val _currentLocation =
        MutableStateFlow<UiState<LatLng>>(UiState.Success(INITIAL_CAMERA_POSITION))
    val currentLocation: StateFlow<UiState<LatLng>>
        get() = _currentLocation.asStateFlow()


    fun getCurrentLocation() {
        _currentLocation.value = UiState.Loading()
        try {
            locationTrackerRepository.getCurrentLocation {
                _currentLocation.value = UiState.Success(it)
            }
        } catch (e: Exception) {
            _currentLocation.value = UiState.Error(e)
        }
    }

    companion object {
        private val INITIAL_CAMERA_POSITION = LatLng(55.751574, 37.573856)
    }
}