package com.example.weatherapp.presentation.ui.trigger_addition_conditions_screen.adapters.condition.conditionitem

import com.example.weatherapp.domain.models.TriggerCondition
import com.example.weatherapp.utils.BidirectionalMapper
import javax.inject.Inject

/**
 * Interface for mapping [TriggerCondition] <-> [ConditionItem]
 * @see BidirectionalMapper
 */
interface ConditionDomainPresentationMapper : BidirectionalMapper<TriggerCondition, ConditionItem>

class ConditionDomainPresentationMapperImpl @Inject constructor() :
    ConditionDomainPresentationMapper {

    /**
     * Maps from [TriggerCondition] to [ConditionItem]
     * @throws
     */
    override fun map(valueToMap: TriggerCondition): ConditionItem = with(valueToMap) {
        ConditionItem(
            conditionType,
            expressionType,
            amount.toString()
        )
    }

    /**
     * Maps from [ConditionItem] to [TriggerCondition]
     * @throws
     */
    override fun reverseMap(valueToMap: ConditionItem): TriggerCondition = with(valueToMap) {
        TriggerCondition(
            conditionType!!,
            expressionType!!,
            amount.toDouble()
        )
    }
}