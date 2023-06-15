package com.example.weatherapp.presentation.ui.trigger_addition_locations_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentTriggerAdditionLocationsBinding

class TriggerAdditionsLocationsFragment : Fragment() {

    private lateinit var binding: FragmentTriggerAdditionLocationsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTriggerAdditionLocationsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            toNextScreen()
        }
    }

    private fun toNextScreen() {
        findNavController().navigate(R.id.action_triggerAdditionsLocationsFragment_to_triggerAdditionConditionsFragment)
    }
}