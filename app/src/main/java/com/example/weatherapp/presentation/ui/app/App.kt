package com.example.weatherapp.presentation.ui.app

import android.app.Application
import com.example.weatherapp.BuildConfig
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // set yandex map api key
        MapKitFactory.setApiKey(BuildConfig.MAPS_API_KEY)
    }
}