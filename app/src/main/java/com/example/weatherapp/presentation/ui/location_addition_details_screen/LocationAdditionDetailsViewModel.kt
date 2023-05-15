package com.example.weatherapp.presentation.ui.location_addition_details_screen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.Location
import com.example.weatherapp.domain.repositories.LocationsWeatherRepository
import com.example.weatherapp.presentation.ui_utils.viewModelScopeIO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationAdditionDetailsViewModel @Inject constructor(
    private val locationsWeatherRepository: LocationsWeatherRepository
) : ViewModel() {

    fun addLocationToList(
        name: String,
        longitude: Float,
        latitude: Float,
        hasNextDayForecast: Boolean
    ) = viewModelScopeIO.launch {
        val enteredLocation = Location(
            name = name,
            id = 0,
            isFavorite = false,
            long = longitude,
            lat = latitude,
            hasNextDayForecast = hasNextDayForecast
        )
        locationsWeatherRepository.addLocation(enteredLocation)
    }
}