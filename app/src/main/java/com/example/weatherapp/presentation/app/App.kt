package com.example.weatherapp.presentation.app

import android.app.Application
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.utils.locale.CurrentLocaleProvider
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var currentLocaleProvider: CurrentLocaleProvider

    override fun onCreate() {
        super.onCreate()
        // set yandex map locale
        MapKitFactory.setLocale(currentLocaleProvider.provideRfc3060Code())
        // set yandex map api key
        MapKitFactory.setApiKey(BuildConfig.MAPS_API_KEY)
    }
}