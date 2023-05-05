package com.example.weatherapp.presenter.contract

import com.example.weatherapp.domain.models.LocationItem

interface LocationItemClickListener {
    fun changeFavoriteStatus(locationItem: LocationItem)

    fun showDetails(locationItem: LocationItem)
}