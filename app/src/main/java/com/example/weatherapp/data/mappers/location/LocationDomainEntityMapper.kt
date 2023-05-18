package com.example.weatherapp.data.mappers.location

import com.example.weatherapp.data.databases.location_database.entities.LocationEntity
import com.example.weatherapp.domain.models.Location
import com.example.weatherapp.utils.BidirectionalMapper
import javax.inject.Inject

/**
 * Interface for mapping [Location] <-> [LocationEntity]
 * @see BidirectionalMapper
 */
interface LocationDomainEntityMapper : BidirectionalMapper<Location, LocationEntity>

class LocationDomainEntityMapperImpl @Inject constructor() : LocationDomainEntityMapper {

    /**
     * Maps from [Location] to [LocationEntity]
     */
    override fun map(valueToMap: Location): LocationEntity = with(valueToMap) {
        LocationEntity(
            id = id,
            name = name,
            lat = lat,
            lon = long,
            isFavorite = isFavorite,
            hasNextDayForecast = hasNextDayForecast
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
            isFavorite = isFavorite,
            hasNextDayForecast = hasNextDayForecast
        )
    }
}