package com.example.weatherapp.domain.usecases.implementation

import com.example.weatherapp.domain.models.LocationDetails
import com.example.weatherapp.domain.repositories.LocationListRepository
import com.example.weatherapp.domain.usecases.declaration.AddLocationToListUseCase

class AddLocationToListUseCaseImpl(
    private val locationListRepository: LocationListRepository,
) : AddLocationToListUseCase {

    override suspend fun invoke(locationDetails: LocationDetails) {
        locationListRepository.addLocationWithDetails(locationDetails)
    }
}