package com.example.weatherapp.di.data.local

import android.content.Context
import android.location.Geocoder
import com.example.weatherapp.utils.locale.CurrentLocaleProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeocoderModule {

    @Provides
    @Singleton
    fun providesGeocoder(
        @ApplicationContext context: Context,
        localeProvider: CurrentLocaleProvider,
    ): Geocoder = Geocoder(context, Locale.forLanguageTag(localeProvider.provideIso3166Code()))
}