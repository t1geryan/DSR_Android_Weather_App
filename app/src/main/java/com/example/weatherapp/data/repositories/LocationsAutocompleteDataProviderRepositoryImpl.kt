package com.example.weatherapp.data.repositories

import com.example.weatherapp.data.mappers.autocomplete.AutocompleteDtoToDomainMapper
import com.example.weatherapp.data.remote.autocomplete.api.AutocompleteApi
import com.example.weatherapp.domain.repositories.LocationsAutocompleteDataProviderRepository
import com.example.weatherapp.utils.extensions.wrapRetrofitExceptions
import com.example.weatherapp.utils.locale.CurrentLocaleProvider
import javax.inject.Inject

class LocationsAutocompleteDataProviderRepositoryImpl @Inject constructor(
    private val autocompleteApi: AutocompleteApi,
    private val currentLocaleProvider: CurrentLocaleProvider,
    private val autocompleteDtoToDomainMapper: AutocompleteDtoToDomainMapper,
) : LocationsAutocompleteDataProviderRepository {

    override suspend fun getAutocompleteData(input: String): List<String> {
        return wrapRetrofitExceptions {
            fetchAutocompleteData(input)
        }
    }

    private suspend fun fetchAutocompleteData(input: String): List<String> {
        val autocompleteResponse = autocompleteApi.getAutocompleteData(
            input,
            locale = currentLocaleProvider.provideIso3166Code()
        )
        val autocompleteData = if (autocompleteResponse.isSuccessful) {
            autocompleteResponse.body()?.let { autocompleteDtoList ->
                autocompleteDtoList.map { autocompleteDto ->
                    autocompleteDtoToDomainMapper.map(autocompleteDto)
                }
            } ?: emptyList()
        } else {
            emptyList()
        }
        return autocompleteData
    }
}