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
import com.example.weatherapp.presentation.ui.weather_details_screen.adapter.ForecastsAdapter
import com.example.weatherapp.presentation.ui.weather_details_screen.adapter.forecastitem.ForecastToForecastItemMapper
import com.example.weatherapp.presentation.ui_utils.*
import com.example.weatherapp.utils.Constants
import com.example.weatherapp.utils.locale.CurrentLocaleProvider
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
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

    @Inject
    lateinit var currentLocaleProvider: CurrentLocaleProvider

    @Inject
    lateinit var forecastToForecastItemMapper: ForecastToForecastItemMapper

    private val viewModel: WeatherDetailsViewModel by viewModelCreator {
        factory.create(args.locationId)
    }

    private var firstDataLoad = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherDetailsBinding.inflate(inflater, container, false)
        adapter = ForecastsAdapter(unitsSystemProvider())
        binding.forecastsRV.adapter = adapter

        binding.temperatureChart.styleTemperatureLineChart(
            requireContext(),
            R.string.temperature_chart_title
        )

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
        binding.progressBar.visibility = View.INVISIBLE
        when (uiState) {
            is UiState.Loading -> {
                if (firstDataLoad) {
                    setContentViewsVisibility(View.INVISIBLE)
                }
                binding.progressBar.visibility = View.VISIBLE
            }
            is UiState.Success -> {
                if (firstDataLoad) {
                    setContentViewsVisibility(View.VISIBLE)
                }
                firstDataLoad = false
                showLocationWeather(uiState.data)
            }
            is UiState.Error -> showErrorDialog(uiState.exception?.message)
        }
    }


    // control the visibility of all views that are visible even with empty data
    private fun setContentViewsVisibility(visibility: Int) = with(binding) {
        horizontalBarrier.visibility = visibility
        horizontalBarrier2.visibility = visibility
        temperatureChartTitle.visibility = visibility
        temperatureChartCV.visibility = visibility
        drawableStartTVGroup.visibility = visibility
    }

    private fun showLocationWeather(locationWeather: LocationWeather) {
        if (!requireContext().hasNetworkConnection()) {
            showRefreshRequest(snackbarProvider) {
                viewModel.fetchLocationWeather()
            }
        }
        // show forecasts
        adapter.forecastsList = locationWeather.weatherForecasts.map { forecast ->
            forecastToForecastItemMapper.map(forecast)
        }

        // show current weather
        val currentWeather = locationWeather.currentWeather
        loadCurrentWeatherDataToTextViews(currentWeather)
        loadCurrentWeatherIcon(currentWeather.weatherIconName)

        // show temperature chart
        loadTemperatureChart(locationWeather)
    }

    private fun loadCurrentWeatherDataToTextViews(currentWeather: CurrentWeather) = with(binding) {
        with(currentWeather) {
            val context = requireContext()
            val currentUnitsSystemKey = unitsSystemProvider().getCurrentUnitsSystem()
            // see string.xml
            temperatureTV.text =
                context.getTemperatureString(temperature, currentUnitsSystemKey)
            currentDateTV.text = unixUtcSecondsTimeToPattern(
                getCurrentLocationTimeUnixSeconds(shiftFromUtcSeconds),
                Constants.Time.WEEKDAY_HOUR_MINUTE_PATTERN,
                currentLocaleProvider.provideIso3166Code(),
            ).capitalize()
            descriptionTV.text = weatherDescription.capitalize()
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

    private fun loadTemperatureChart(locationWeather: LocationWeather) {
        val dataValues = viewModel.createTemperatureChartEntries(locationWeather)
        val lineDataSet = LineDataSet(dataValues, getString(R.string.temperature_chart_title))
        lineDataSet.styleTemperatureLineDataSet(requireContext())
        binding.temperatureChart.data = LineData(lineDataSet)
        binding.temperatureChart.invalidate()
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
        dialogProvider.showSimpleDialog(
            R.string.confirm_title,
            R.string.location_delete_message,
            true,
            R.string.confirm,
            R.string.deny,
            null
        ) { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.deleteLocationById(args.locationId)
                    findNavController().popBackStack()
                }
            }
        }
    }
}