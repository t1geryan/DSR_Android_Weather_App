package com.example.weatherapp.presentation.ui.weather_details_screen

import android.content.DialogInterface
import android.graphics.Color
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
import com.example.weatherapp.utils.locale.CurrentLocaleProvider
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.color.MaterialColors
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

        styleTemperatureChart()
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

    private fun styleTemperatureChart() = with(binding.temperatureChart) {
        val context = requireContext()
        val colorSurface = MaterialColors.getColor(
            context, com.google.android.material.R.attr.colorSurface, Color.BLACK
        )
        val colorOnSurface = MaterialColors.getColor(
            context, com.google.android.material.R.attr.colorOnSurface, Color.BLACK
        )
        setNoDataText(getString(R.string.temperature_chart_title))
        setNoDataTextColor(colorOnSurface)
        setBackgroundColor(colorSurface)

        description.isEnabled = false
        setTouchEnabled(false)
        setScaleEnabled(false)
        isDragEnabled = false
        setDrawGridBackground(false)
        setDrawBorders(false)
        legend.isEnabled = false
        xAxis.isEnabled = false
        axisRight.isEnabled = false
        axisLeft.isEnabled = true
        axisLeft.axisLineColor = colorOnSurface
        axisLeft.textColor = colorOnSurface
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
        adapter.forecastsList = locationWeather.weatherForecasts.map {
            ForecastItem.fromForecast(it)
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
            val forecastedTimeUnixMillis =
                (dateTimeUnixUtc + shiftFromUtcSeconds) * Constants.Time.MILLIS_IN_SEC
            currentDateTV.text = requireContext().unixUtcTimeToPattern(
                forecastedTimeUnixMillis,
                Constants.Time.WEEKDAY_HOUR_MINUTE_PATTERN,
                currentLocaleProvider.provideIso3166Code(),
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

    private fun loadTemperatureChart(locationWeather: LocationWeather) {
        val dataValues = mutableListOf<Entry>()
        // first entry will be current weather temperature
        dataValues += (Entry(0.0f, locationWeather.currentWeather.temperature.toFloat()))
        val forecasts = locationWeather.weatherForecasts
        // next entries will be each forecast temperature
        for (i in forecasts.indices) {
            dataValues += Entry((i + 1).toFloat(), forecasts[i].temperature.toFloat())
        }
        val lineDataSet = LineDataSet(dataValues, getString(R.string.temperature_chart_title))
        val context = requireContext()
        val colorPrimary = MaterialColors.getColor(
            requireContext(), com.google.android.material.R.attr.colorPrimary, Color.BLACK
        )
        val colorOnSurface = MaterialColors.getColor(
            context, com.google.android.material.R.attr.colorOnSurface, Color.BLACK
        )
        with(lineDataSet) {
            color = colorPrimary
            setCircleColor(colorPrimary)
            lineWidth = CHART_LINE_WIDTH
            circleRadius = CHART_CIRCLE_RADIUS
            circleHoleRadius = CHART_HOLE_RADIUS
            valueTextColor = colorOnSurface
            binding.temperatureChart.data = LineData(lineDataSet)
            binding.temperatureChart.invalidate()
        }
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

    companion object {
        private const val CHART_LINE_WIDTH = 1.75f
        private const val CHART_CIRCLE_RADIUS = 3.0f
        private const val CHART_HOLE_RADIUS = 0.5f
    }
}