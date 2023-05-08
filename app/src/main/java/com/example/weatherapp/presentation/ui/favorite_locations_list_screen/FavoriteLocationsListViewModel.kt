package com.example.weatherapp.presentation.ui.favorite_locations_list_screen

import com.example.weatherapp.domain.models.LocationItem
import com.example.weatherapp.domain.repositories.LocationListRepository
import com.example.weatherapp.presentation.ui.base_locations_list_screen.BaseLocationsListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoriteLocationsListViewModel @Inject constructor(
    private val locationListRepository: LocationListRepository
) : BaseLocationsListViewModel(locationListRepository) {

    override suspend fun fetchLocationItems(): Flow<List<LocationItem>> =
        locationListRepository.getAllLocationsWeather(true)
}