package com.example.weatherapp.presenter.ui.location_addition_map_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentLocationAdditionMapBinding

class LocationAdditionMapFragment : Fragment() {

    private lateinit var binding: FragmentLocationAdditionMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationAdditionMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            toNextScreen()
        }
    }

    private fun toNextScreen() {
        findNavController().navigate(R.id.action_locationAdditionMapFragment_to_locationAdditionNameFragment)
    }
}