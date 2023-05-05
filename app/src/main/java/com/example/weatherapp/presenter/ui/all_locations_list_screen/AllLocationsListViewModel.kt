package com.example.weatherapp.presenter.ui.all_locations_list_screen

import com.example.weatherapp.domain.repositories.LocationListRepository
import com.example.weatherapp.presenter.ui.base_locations_list_screen.BaseLocationsListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AllLocationsListViewModel @Inject constructor(
    locationListRepository: LocationListRepository
) : BaseLocationsListViewModel(locationListRepository) {

    override val isOnlyFavoriteContacts: Boolean = false
}