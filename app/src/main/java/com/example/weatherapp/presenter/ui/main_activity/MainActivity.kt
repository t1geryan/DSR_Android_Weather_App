package com.example.weatherapp.presenter.ui.main_activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.R

/**
 * Container for all screens in the app.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}