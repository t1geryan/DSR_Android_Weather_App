package com.example.weatherapp.presenter.ui.location_addition_details_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentLocationAdditionDetailsBinding

class LocationAdditionDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLocationAdditionDetailsBinding

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
            backToStartScreen()
        }
    }

    private fun backToStartScreen() {
        findNavController().navigate(R.id.action_locationAdditionDetailsFragment_to_bottomNavigationFragment)
    }
}