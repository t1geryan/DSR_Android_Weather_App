package com.example.weatherapp.domain.repositories

import com.example.weatherapp.domain.AppException
import com.example.weatherapp.domain.BackendException
import com.example.weatherapp.domain.ConnectionException

interface LocationsAutocompleteDataProviderRepository {

    /**
     * The function of obtaining a list of locations for autocompletion at the input
     * @throws ConnectionException
     * @throws BackendException
     * @throws AppException
     */
    suspend fun getAutocompleteData(input: String): List<String>
}