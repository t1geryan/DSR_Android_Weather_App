package com.example.weatherapp.presentation.ui.weather_details_screen

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentWeatherDetailsBinding
import com.example.weatherapp.domain.models.CurrentWeather
import com.example.weatherapp.domain.models.LocationWeather
import com.example.weatherapp.presentation.contract.sideeffects.dialogs.SimpleDialogProvider
import com.example.weatherapp.presentation.contract.sideeffects.snakbars.SnackbarProvider
import com.example.weatherapp.presentation.contract.toolbar.HasCustomActionToolbar
import com.example.weatherapp.presentation.contract.toolbar.HasCustomTitleToolbar
import com.example.weatherapp.presentation.contract.toolbar.ToolbarAction
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui.weather_details_screen.adapter.ForecastItem
import com.example.weatherapp.presentation.ui.weather_details_screen.adapter.ForecastsAdapter
import com.example.weatherapp.presentation.ui_utils.*
import com.example.weatherapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherDetailsFragment : Fragment(), HasCustomTitleToolbar, HasCustomActionToolbar {

    private lateinit var binding: FragmentWeatherDetailsBinding

    private lateinit var adapter: ForecastsAdapter

    private val args: WeatherDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var dialogProvider: SimpleDialogProvider

    @Inject
    lateinit var snackbarProvider: SnackbarProvider

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
        adapter = ForecastsAdapter(unitsSystemProvider())
        binding.forecastsRV.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.collectFlow(viewModel.locationWeather) { locationWeatherUiState ->
            collectLocationWeatherUiState(locationWeatherUiState)
        }
        binding.weatherDetailsSwipeRefresh.apply {
            setOnRefreshListener {
                viewModel.fetchLocationWeather()
                handler.postDelayed(Constants.DELAY.SWIPE_TO_REFRESH_END_DELAY) {
                    isRefreshing = false
                }
            }
        }
    }

    private fun collectLocationWeatherUiState(uiState: UiState<LocationWeather>) {
        hideSupportingViews()
        when (uiState) {
            is UiState.Loading -> binding.progressBar.visibility = View.VISIBLE
            is UiState.Success -> showLocationWeather(uiState.data)
            is UiState.Error -> showErrorDialog(uiState.exception?.message)
        }
    }


    private fun hideSupportingViews() = with(binding) {
        progressBar.visibility = View.INVISIBLE
        horizontalBarrier.visibility = View.INVISIBLE
        // make invisible all text views with drawable left
        drawableStartTVGroup.visibility = View.INVISIBLE
    }

    private fun showLocationWeather(locationWeather: LocationWeather) {
        if (!requireContext().hasNetworkConnection()) {
            showRefreshRequest(snackbarProvider) {
                viewModel.fetchLocationWeather()
            }
        }

        // make visible all needed views (see hideSupportingViews)
        binding.horizontalBarrier.visibility = View.VISIBLE
        binding.drawableStartTVGroup.visibility = View.VISIBLE

        // show forecasts
        adapter.forecastsList = locationWeather.weatherForecasts.map {
            ForecastItem.fromForecast(it)
        }

        // show current weather
        val currentWeather = locationWeather.currentWeather
        loadCurrentWeatherDataToTextViews(currentWeather)
        loadCurrentWeatherIcon(currentWeather.weatherIconName)
    }

    private fun loadCurrentWeatherDataToTextViews(currentWeather: CurrentWeather) = with(binding) {
        with(currentWeather) {
            val context = requireContext()
            val currentUnitsSystemKey = unitsSystemProvider().getCurrentUnitsSystem()
            // see string.xml
            temperatureTV.text =
                context.getTemperatureString(temperature, currentUnitsSystemKey)
            val forecastedTimeUnixMillis =
                (dateTimeUnixUtc + shiftFromUtcSeconds) * Constants.Time.MILLIS_IN_SEC
            currentDateTV.text = requireContext().unixUtcTimeToPattern(
                forecastedTimeUnixMillis,
                Constants.Time.WEEKDAY_HOUR_MINUTE_PATTERN
            )
            descriptionTV.text = weatherDescription
            windDataTV.text = getString(
                R.string.wind_data,
                context.getWindSpeedString(windSpeed, currentUnitsSystemKey),
                context.windDirectionDegreesToString(windDirectionDegrees)
            )
            pressureDataTv.text = getString(R.string.pressure_data, pressure)
            humidityDataTV.text = getString(R.string.humidity_data, humidity)
        }
    }

    private fun loadCurrentWeatherIcon(weatherIconName: String) {
        Glide.with(requireContext())
            .load(getString(R.string.weather_icon_path, weatherIconName))
            .error(R.drawable.icon_weather_cloudy_24)
            .placeholder(R.drawable.icon_weather_cloudy_24)
            .into(binding.currentWeatherIconIV)
    }

    private fun showErrorDialog(message: String?) {
        dialogProvider.showSimpleDialog(
            getString(R.string.location_weather_loading_exception),
            message ?: getString(R.string.default_exception_message),
            true,
            getString(R.string.refresh),
            null,
            getString(R.string.close),
        ) { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> viewModel.fetchLocationWeather()
            }
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