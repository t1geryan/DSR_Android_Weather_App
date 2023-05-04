package com.example.weatherapp.data.repositories

import com.example.weatherapp.domain.models.Location
import com.example.weatherapp.domain.models.LocationDetails
import com.example.weatherapp.domain.repositories.LocationListRepository
import kotlinx.coroutines.flow.Flow

class LocationListRepositoryImpl : LocationListRepository {
    override suspend fun getAllLocation(onlyFavorites: Boolean): Flow<List<Location>> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocationWithoutDetails(locationId: Int): Flow<Location> {
        TODO("Not yet implemented")
    }

    override suspend fun getLocationDetailsById(locationId: Int): Flow<LocationDetails> {
        TODO("Not yet implemented")
    }

    override suspend fun addLocationWithDetails(locationDetails: LocationDetails) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteLocationById(locationId: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun changeLocationFavoriteStatusById(locationId: Int) {
        TODO("Not yet implemented")
    }
}