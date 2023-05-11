package com.example.weatherapp.presentation.ui.app

import android.app.Application
import com.example.weatherapp.utils.Constants
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // set yandex map api key
        MapKitFactory.setApiKey(Constants.MAP_API_KEY)
    }
}