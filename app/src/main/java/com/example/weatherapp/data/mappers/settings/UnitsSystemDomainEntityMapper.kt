package com.example.weatherapp.data.mappers.settings

import com.example.weatherapp.data.pref_datastore.settings_datastore.entities.UnitsSystemEntity
import com.example.weatherapp.domain.models.AppUnitsSystem
import com.example.weatherapp.utils.BidirectionalMapper
import javax.inject.Inject

/**
 * Interface for mapping [AppUnitsSystem] <-> [UnitsSystemEntity]
 * @see BidirectionalMapper
 */
interface UnitsSystemDomainEntityMapper : BidirectionalMapper<AppUnitsSystem, UnitsSystemEntity>

class UnitsSystemDomainEntityMapperImpl @Inject constructor() : UnitsSystemDomainEntityMapper {

    private val unitsSystemEntityValues = UnitsSystemEntity.values()

    /**
     * Maps from [AppUnitsSystem] to [UnitsSystemEntity]
     * @throws IllegalArgumentException when [AppUnitsSystem.systemKey] does not match any enum constant
     */
    override fun map(valueToMap: AppUnitsSystem): UnitsSystemEntity =
        unitsSystemEntityValues[valueToMap.systemKey]

    /**
     * Maps from [UnitsSystemEntity] to [AppUnitsSystem]
     */
    override fun reverseMap(valueToMap: UnitsSystemEntity): AppUnitsSystem =
        AppUnitsSystem(valueToMap.ordinal)
}