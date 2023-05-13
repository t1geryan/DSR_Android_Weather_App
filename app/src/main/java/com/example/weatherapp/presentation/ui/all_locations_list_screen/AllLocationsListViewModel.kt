package com.example.weatherapp.presentation.ui.all_locations_list_screen

import com.example.weatherapp.domain.models.LocationItem
import com.example.weatherapp.domain.repositories.LocationsWeatherRepository
import com.example.weatherapp.presentation.ui.base_locations_list_screen.BaseLocationsListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AllLocationsListViewModel @Inject constructor(
    private val locationsWeatherRepository: LocationsWeatherRepository
) : BaseLocationsListViewModel(locationsWeatherRepository) {

    override suspend fun fetchLocationItems(): Flow<List<LocationItem>> =
        locationsWeatherRepository.getAllLocationsWeather(false)
}