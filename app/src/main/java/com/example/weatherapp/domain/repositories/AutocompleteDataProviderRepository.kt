package com.example.weatherapp.domain.repositories

interface AutocompleteDataProviderRepository {

    suspend fun getAutocompleteData(input: String): List<String>
}