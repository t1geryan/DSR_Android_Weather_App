package com.example.weatherapp.presenter.ui.all_locations_list_screen

import com.example.weatherapp.domain.models.LocationItem
import com.example.weatherapp.domain.repositories.LocationListRepository
import com.example.weatherapp.presenter.ui.base_locations_list_screen.BaseLocationsListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AllLocationsListViewModel @Inject constructor(
    private val locationListRepository: LocationListRepository
) : BaseLocationsListViewModel(locationListRepository) {

    override suspend fun fetchLocationItems(): Flow<List<LocationItem>> =
        locationListRepository.getAllLocationsWeather(false)
}