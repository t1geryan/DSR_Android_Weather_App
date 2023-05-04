package com.example.weatherapp.domain.usecases.declaration

import com.example.weatherapp.domain.models.LocationDetails

interface AddLocationToListUseCase {

    suspend operator fun invoke(locationDetails: LocationDetails)
}