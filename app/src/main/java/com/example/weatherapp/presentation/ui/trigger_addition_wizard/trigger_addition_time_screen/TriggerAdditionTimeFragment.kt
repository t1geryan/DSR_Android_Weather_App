package com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_time_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentTriggerAdditionTimeBinding

class TriggerAdditionTimeFragment : Fragment() {

    private lateinit var binding: FragmentTriggerAdditionTimeBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTriggerAdditionTimeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.nextButton.setOnClickListener {
            finishTriggerAdditionWizard()
        }
    }

    private fun finishTriggerAdditionWizard() {
        findNavController().navigate(R.id.action_triggerAdditionTimeFragment_to_bottomNavigationFragment)
    }
}