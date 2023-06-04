package com.example.weatherapp.data.mappers.autocomplete

import com.example.weatherapp.data.remote.autocomplete.dto.AutocompleteResponseDto
import com.example.weatherapp.data.remote.weather.dto.CurrentWeatherResponseDto
import com.example.weatherapp.utils.Mapper
import javax.inject.Inject

/**
 * Interface for mapping [CurrentWeatherResponseDto] -> [String]
 * @see Mapper
 */
interface AutocompleteDtoToDomainMapper : Mapper<AutocompleteResponseDto, String>

class AutocompleteDtoToDomainMapperImpl @Inject constructor() : AutocompleteDtoToDomainMapper {

    /**
     * Maps [CurrentWeatherResponseDto] -> [String]
     * @return string in format "city_name, country_name"
     */
    override fun map(valueToMap: AutocompleteResponseDto): String = with(valueToMap) {
        "$name, $countryName"
    }
}