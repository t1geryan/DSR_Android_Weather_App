package com.example.weatherapp.data.repositories

import com.example.weatherapp.data.remote.autocomplete.api.AutocompleteApi
import com.example.weatherapp.domain.repositories.AutocompleteDataProviderRepository
import com.example.weatherapp.utils.locale.CurrentLocaleProvider
import javax.inject.Inject

class AutocompleteDataProviderRepositoryImpl @Inject constructor(
    private val autocompleteApi: AutocompleteApi,
    private val currentLocaleProvider: CurrentLocaleProvider,
) : AutocompleteDataProviderRepository {

    override suspend fun getAutocompleteData(input: String): List<String> {
        return fetchAutocompleteData(input)
    }

    private suspend fun fetchAutocompleteData(input: String): List<String> {
        val autocompleteResponse = autocompleteApi.getAutocompleteData(
            input,
            locale = currentLocaleProvider.provideIso3166Code()
        )
        val autocompleteData = if (autocompleteResponse.isSuccessful) {
            autocompleteResponse.body()?.let { autocompleteDtoList ->
                autocompleteDtoList.map { autocompleteDto ->
                    "${autocompleteDto.name}, ${autocompleteDto.countryName}"
                }
            } ?: emptyList()
        } else {
            emptyList()
        }
        return autocompleteData
    }
}