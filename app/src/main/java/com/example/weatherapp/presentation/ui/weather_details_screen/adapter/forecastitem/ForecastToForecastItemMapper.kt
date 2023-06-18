package com.example.weatherapp.presentation.ui.weather_details_screen.adapter.forecastitem

import com.example.weatherapp.domain.models.Forecast
import com.example.weatherapp.utils.Mapper
import javax.inject.Inject

/**
 * Interface for mapping [Forecast] -> [ForecastItem]
 */
interface ForecastToForecastItemMapper : Mapper<Forecast, ForecastItem>

class ForecastToForecastItemMapperImpl @Inject constructor() : ForecastToForecastItemMapper {

    /**
     * Maps [Forecast] to [ForecastItem]
     */
    override fun map(valueToMap: Forecast): ForecastItem = with(valueToMap) {
        ForecastItem(
            weatherIconName = weatherIconName,
            temperature = temperature,
            dateTimeUnixUtc = dateTimeUnixUtc,
            shiftFromUtcSeconds = shiftFromUtcSeconds,
        )
    }
}