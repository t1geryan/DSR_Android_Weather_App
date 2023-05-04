package com.example.weatherapp.domain.usecases.implementation

import com.example.weatherapp.domain.models.LocationWeather
import com.example.weatherapp.domain.repositories.LocationListRepository
import com.example.weatherapp.domain.usecases.declaration.AddLocationToListUseCase

class AddLocationToListUseCaseImpl(
    private val locationListRepository: LocationListRepository,
) : AddLocationToListUseCase {

    override suspend fun invoke(locationWeather: LocationWeather) {
        locationListRepository.addLocationWeather(locationWeather)
    }
}