package com.example.weatherapp.presenter.ui.location_addition_map_screen

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.databinding.FragmentLocationAdditionMapBinding
import com.example.weatherapp.presenter.ui_utls.permissionsProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationAdditionMapFragment : Fragment() {

    private lateinit var binding: FragmentLocationAdditionMapBinding

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var hasResult = false
    private var latitude: Float = 0.0f
    private var longitude: Float = 0.0f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationAdditionMapBinding.inflate(inflater, container, false)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            toNextScreen()
        }

        binding.getCurrentLocationButton.setOnClickListener {
            getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        permissionsProvider().requestPermission(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener(requireActivity()) { location ->
                    hasResult = true
                    latitude = location.latitude.toFloat()
                    longitude = location.longitude.toFloat()
                    println("$latitude $longitude")
                }
        }
    }

    private fun toNextScreen() {
        if (hasResult) {
            val destination =
                LocationAdditionMapFragmentDirections.actionLocationAdditionMapFragmentToLocationAdditionNameFragment(
                    latitude,
                    longitude
                )
            findNavController().navigate(destination)
        } else {
            Toast.makeText(requireContext(), "Dont have location", Toast.LENGTH_SHORT).show()
        }
    }
}