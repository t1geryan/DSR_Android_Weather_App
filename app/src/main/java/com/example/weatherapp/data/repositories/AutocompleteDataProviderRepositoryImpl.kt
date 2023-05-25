package com.example.weatherapp.data.repositories

import com.example.weatherapp.domain.repositories.AutocompleteDataProviderRepository
import javax.inject.Inject

class AutocompleteDataProviderRepositoryImpl @Inject constructor(

) : AutocompleteDataProviderRepository {

    override suspend fun getAutocompleteData(): List<String> {
        TODO("Not yet implemented")
    }
}