package com.example.weatherapp.presentation.ui.location_addition_wizard.location_addition_details_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentLocationAdditionDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationAdditionDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLocationAdditionDetailsBinding

    private val args: LocationAdditionDetailsFragmentArgs by navArgs()

    private val viewModel: LocationAdditionDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationAdditionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            finishLocationAddition()
        }
    }

    private fun finishLocationAddition() {
        val hasNextDayForecast =
            binding.radioGroup.checkedRadioButtonId == binding.confirmRB.id
        viewModel.addLocationToList(
            args.locationName,
            args.latLng,
            hasNextDayForecast,
        )
        findNavController().popBackStack(R.id.bottomNavigationFragment, false)
    }
}