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
import com.example.weatherapp.presentation.contract.HasCustomActionToolbar
import com.example.weatherapp.presentation.contract.HasCustomTitleToolbar
import com.example.weatherapp.presentation.contract.ToolbarAction
import com.example.weatherapp.presentation.ui_utls.collectWhenStarted
import com.example.weatherapp.presentation.ui_utls.viewModelCreator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class WeatherDetailsFragment : Fragment(), HasCustomTitleToolbar, HasCustomActionToolbar {

    private lateinit var binding: FragmentWeatherDetailsBinding

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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectWhenStarted {
            viewModel.locationWeather.collect { uiState ->
                when (uiState) {
                    // todo add handling
                    else -> {

                    }
                }
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