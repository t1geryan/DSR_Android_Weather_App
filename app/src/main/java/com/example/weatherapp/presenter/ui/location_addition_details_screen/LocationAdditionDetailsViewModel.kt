package com.example.weatherapp.presenter.ui.location_addition_details_screen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.Location
import com.example.weatherapp.domain.repositories.LocationListRepository
import com.example.weatherapp.presenter.ui_utls.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationAdditionDetailsViewModel @Inject constructor(
    private val locationListRepository: LocationListRepository
) : ViewModel() {

    fun addLocationToList(
        name: String,
        longitude: Float,
        latitude: Float,
        hasNextDayForecast: Boolean
    ) = viewModelScope.launch {
        val enteredLocation = Location(
            name = name,
            id = 0,
            isFavorite = false,
            long = longitude,
            lat = latitude,
            hasNextDayForecast = hasNextDayForecast
        )
        locationListRepository.addLocation(enteredLocation)
    }
}