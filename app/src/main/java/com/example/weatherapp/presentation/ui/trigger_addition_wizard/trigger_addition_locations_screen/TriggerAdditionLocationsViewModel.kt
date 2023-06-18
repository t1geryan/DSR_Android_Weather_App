package com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_locations_screen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.repositories.LocationsWeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TriggerAdditionLocationsViewModel @Inject constructor(
    locationsWeatherRepository: LocationsWeatherRepository
) : ViewModel() {

}