package com.example.weatherapp.domain.usecases.declaration

import com.example.weatherapp.domain.models.LocationWeather

interface AddLocationToListUseCase {

    suspend operator fun invoke(locationWeather: LocationWeather)
}