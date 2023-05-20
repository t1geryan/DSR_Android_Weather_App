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

    /**
     * Maps from [AppUnitsSystem] to [UnitsSystemEntity]
     * @throws IllegalArgumentException when [AppUnitsSystem] does not match any enum constant
     */
    override fun map(valueToMap: AppUnitsSystem): UnitsSystemEntity = when (valueToMap) {
        AppUnitsSystem.METRIC_SYSTEM -> UnitsSystemEntity.METRIC_SYSTEM
        AppUnitsSystem.IMPERIAL_SYSTEM -> UnitsSystemEntity.IMPERIAL_SYSTEM
    }

    /**
     * Maps from [UnitsSystemEntity] to [AppUnitsSystem]
     */
    override fun reverseMap(valueToMap: UnitsSystemEntity): AppUnitsSystem = when (valueToMap) {
        UnitsSystemEntity.METRIC_SYSTEM -> AppUnitsSystem.METRIC_SYSTEM
        UnitsSystemEntity.IMPERIAL_SYSTEM -> AppUnitsSystem.IMPERIAL_SYSTEM
    }
}