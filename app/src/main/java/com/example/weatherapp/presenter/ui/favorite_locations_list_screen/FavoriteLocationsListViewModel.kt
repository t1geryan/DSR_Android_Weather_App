package com.example.weatherapp.presenter.ui.favorite_locations_list_screen

import com.example.weatherapp.domain.repositories.LocationListRepository
import com.example.weatherapp.presenter.ui.base_locations_list_screen.BaseLocationsListViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteLocationsListViewModel @Inject constructor(
    locationListRepository: LocationListRepository
) : BaseLocationsListViewModel(locationListRepository) {

    override val isOnlyFavoriteContacts: Boolean = true
}