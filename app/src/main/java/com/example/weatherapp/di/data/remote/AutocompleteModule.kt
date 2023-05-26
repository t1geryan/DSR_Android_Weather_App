package com.example.weatherapp.di.data.remote

import com.example.weatherapp.data.remote.autocomplete.api.AutocompleteApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AutocompleteModule {
    @Provides
    @Singleton
    fun providesAutocompleteApi(
        retrofit: Retrofit
    ): AutocompleteApi = retrofit.create(AutocompleteApi::class.java)
}