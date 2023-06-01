package com.example.weatherapp.domain.repositories

interface AutocompleteDataProviderRepository {

    /**
     * Function for getting locations autocomplete
     */
    suspend fun getAutocompleteData(input: String): List<String>
}