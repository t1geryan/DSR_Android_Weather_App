package com.example.weatherapp.data.remote.weather.dto

import com.google.gson.annotations.SerializedName

data class TriggerRequestDto(
    @SerializedName("time_period") val timePeriod: TriggerRequestTimePeriod,
    @SerializedName("conditions") val conditions: List<TriggerRequestCondition>,
    @SerializedName("area") val area: List<TriggerRequestArea>,
)

data class TriggerRequestTimePeriod(
    @SerializedName("start") val start: TriggerRequestTimeExpression,
    @SerializedName("end") val end: TriggerRequestTimeExpression,
)

data class TriggerRequestTimeExpression(
    @SerializedName("expression") val expression: String,
    @SerializedName("amount") val amount: Long,
)

data class TriggerRequestCondition(
    @SerializedName("name") val name: String,
    @SerializedName("expression") val expression: String,
    @SerializedName("amount") val amount: Int,
)

data class TriggerRequestArea(
    @SerializedName("type") val type: String,
    @SerializedName("coordinates") val coordinates: List<Coordinates>
)