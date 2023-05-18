package com.example.weatherapp.presentation.ui.favorite_locations_list_screen

import com.example.weatherapp.domain.repositories.LocationsWeatherRepository
import com.example.weatherapp.domain.repositories.SettingsRepository
import com.example.weatherapp.presentation.ui.base_locations_list_screen.BaseLocationsListViewModel
import com.example.weatherapp.presentation.ui.base_locations_list_screen.adapter.LocationItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class FavoriteLocationsListViewModel @Inject constructor(
    private val locationsWeatherRepository: LocationsWeatherRepository,
    settingsRepository: SettingsRepository
) : BaseLocationsListViewModel(locationsWeatherRepository, settingsRepository) {

    override suspend fun getLocationItemsFromRepository(): Flow<List<LocationItem>> =
        locationsWeatherRepository.getAllLocationsWeather(true)
}