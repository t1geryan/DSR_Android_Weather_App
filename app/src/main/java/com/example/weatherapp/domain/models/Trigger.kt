package com.example.weatherapp.domain.models

data class Trigger(
    val id: Long,
    val latLng: LatLng,
    val conditions: List<TriggerCondition>,
)

data class TriggerCondition(
    val conditionType: ConditionType,
    val expressionType: ExpressionType,
    val amount: Double
)

enum class ConditionType {
    TEMPERATURE,
    PRESSURE,
    HUMIDITY,
    WIND_SPEED,
    WIND_DIRECTION
}

enum class ExpressionType {
    GREATER,
    LESS,
    GREATER_OR_EQUAL,
    LESS_OR_EQUAL,
    EQUAL,
    NOT_EQUAL,
}