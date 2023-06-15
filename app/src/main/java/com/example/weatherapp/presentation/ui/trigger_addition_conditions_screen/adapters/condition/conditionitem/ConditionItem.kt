package com.example.weatherapp.presentation.ui.trigger_addition_conditions_screen.adapters.condition.conditionitem

import com.example.weatherapp.domain.models.ConditionType
import com.example.weatherapp.domain.models.ExpressionType

data class ConditionItem(
    val conditionType: ConditionType? = null,
    val expressionType: ExpressionType? = null,
    val amount: String = "",
)