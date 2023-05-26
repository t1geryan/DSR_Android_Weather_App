package com.example.weatherapp.data.remote.autocomplete.api

import com.example.weatherapp.data.remote.autocomplete.dto.AutocompleteResponseDto
import com.example.weatherapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface AutocompleteApi {

    @GET("${AUTOCOMPLETE_API_BASE_URL}places2")
    suspend fun getAutocompleteData(
        @Query("term") input: String,
        @Query("locale") locale: String,
        @Query("types[]") types: String = Constants.AutocompleteApi.TYPE_CITY
    ): Response<List<AutocompleteResponseDto>>

    companion object {
        private const val AUTOCOMPLETE_API_BASE_URL = "https://autocomplete.travelpayouts.com/"
    }
}