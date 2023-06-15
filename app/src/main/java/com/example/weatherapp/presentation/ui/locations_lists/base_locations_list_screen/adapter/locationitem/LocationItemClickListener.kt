package com.example.weatherapp.presentation.ui.locations_lists.base_locations_list_screen.adapter.locationitem

interface LocationItemClickListener {
    fun changeFavoriteStatus(locationItem: LocationItem)

    fun showDetails(locationItem: LocationItem)
}