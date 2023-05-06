package com.example.weatherapp.data.mappers

import com.example.weatherapp.data.databases.location_database.entities.LocationEntity
import com.example.weatherapp.domain.models.Location
import com.example.weatherapp.utils.BidirectionalMapper
import javax.inject.Inject

/**
 * Class for mapping [Location] <-> [LocationEntity]
 * @see BidirectionalMapper
 */
class LocationMapper @Inject constructor() : BidirectionalMapper<Location, LocationEntity> {

    /**
     * Maps from [Location] to [LocationEntity]
     */
    override fun map(valueToMap: Location): LocationEntity = with(valueToMap) {
        LocationEntity(
            id = id,
            name = name,
            lat = lat,
            lon = long,
            isFavorite = isFavorite
        )
    }

    /**
     * Maps from [LocationEntity] to [Location]
     */
    override fun reverseMap(valueToMap: LocationEntity): Location = with(valueToMap) {
        Location(
            id = id,
            name = name,
            lat = lat,
            long = lon,
            isFavorite = isFavorite
        )
    }
}