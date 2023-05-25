package com.example.weatherapp.domain.repositories

interface AutocompleteDataProviderRepository {

    suspend fun getAutocompleteData(): List<String>
}