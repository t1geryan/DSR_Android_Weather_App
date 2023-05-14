package com.example.weatherapp.presentation.ui.base_locations_list_screen.adapter

interface LocationItemClickListener {
    fun changeFavoriteStatus(locationItem: LocationItem)

    fun showDetails(locationItem: LocationItem)
}