package com.example.weatherapp.presentation.ui.weather_details_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.domain.models.LocationWeather
import com.example.weatherapp.presentation.contract.toolbar.HasCustomActionToolbar
import com.example.weatherapp.presentation.contract.toolbar.HasCustomTitleToolbar
import com.example.weatherapp.presentation.contract.toolbar.ToolbarAction
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui.weather_details_screen.adapter.ForecastItem
import com.example.weatherapp.presentation.ui.weather_details_screen.adapter.ForecastsAdapter
import com.example.weatherapp.presentation.ui_utils.collectWhenStarted
import com.example.weatherapp.presentation.ui_utils.viewModelCreator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherDetailsFragment : Fragment(), HasCustomTitleToolbar, HasCustomActionToolbar {

    private lateinit var binding: FragmentWeatherDetailsBinding

    private lateinit var adapter: ForecastsAdapter

    private val args: WeatherDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var factory: WeatherDetailsViewModel.Factory

    private val viewModel: WeatherDetailsViewModel by viewModelCreator {
        factory.create(args.locationId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)

        adapter = ForecastsAdapter()
        binding.forecastsRV.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectWhenStarted {
            viewModel.locationWeather.collect { uiState ->
                when (uiState) {
                    is UiState.Success -> showLocationWeather(uiState.data)
                    else -> {

                    }
                }
            }
        }
    }

    private fun showLocationWeather(locationWeather: LocationWeather) {
        adapter.forecastsList = locationWeather.weatherForecast.map {
            ForecastItem.fromWeather(it)
        }
    }

    override fun getTitle(): String = args.title

    override fun getCustomAction(): ToolbarAction = ToolbarAction(
        title = R.string.delete_location,
        icon = R.drawable.icon_delete_outline_24,
    ) {
        viewModel.deleteLocationById(args.locationId)
        findNavController().popBackStack()
    }
}