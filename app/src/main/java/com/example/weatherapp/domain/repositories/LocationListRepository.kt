package com.example.weatherapp.domain.repositories

import com.example.weatherapp.domain.models.Location
import com.example.weatherapp.domain.models.LocationDetails
import kotlinx.coroutines.flow.Flow

interface LocationListRepository {

    suspend fun getAllLocation(onlyFavorites: Boolean = false): Flow<List<Location>>

    suspend fun getLocationWithoutDetails(locationId: Int) : Flow<Location>

    suspend fun getLocationDetailsById(locationId: Int): Flow<LocationDetails>

    suspend fun addLocationWithDetails(locationDetails: LocationDetails)

    suspend fun deleteLocationById(locationId: Int)

    suspend fun changeLocationFavoriteStatusById(locationId: Int)
}