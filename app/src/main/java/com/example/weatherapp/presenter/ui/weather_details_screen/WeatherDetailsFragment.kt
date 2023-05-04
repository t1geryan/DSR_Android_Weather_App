package com.example.weatherapp.presenter.ui.weather_details_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.presenter.contract.HasCustomTitleToolbar

class WeatherDetailsFragment : Fragment(), HasCustomTitleToolbar {

    private lateinit var binding: FragmentWeatherDetailsBinding

    private val args: WeatherDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun getTitle(): String = args.title ?: getString(R.string.weather_details_title)
}