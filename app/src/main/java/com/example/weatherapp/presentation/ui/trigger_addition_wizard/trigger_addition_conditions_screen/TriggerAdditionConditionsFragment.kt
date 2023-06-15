package com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_conditions_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentTriggerAdditionConditionsBinding
import com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_conditions_screen.adapter.ConditionsAdapter
import com.example.weatherapp.presentation.ui_utils.addVerticalDividerItemDecoration
import com.example.weatherapp.presentation.ui_utils.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TriggerAdditionConditionsFragment : Fragment() {

    private lateinit var binding: FragmentTriggerAdditionConditionsBinding

    private lateinit var conditionsAdapter: ConditionsAdapter

    private val viewModel: TriggerAdditionConditionsViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTriggerAdditionConditionsBinding.inflate(inflater, container, false)

        binding.conditionsRV.addVerticalDividerItemDecoration(requireContext())
        conditionsAdapter = ConditionsAdapter(viewModel)
        binding.conditionsRV.adapter = conditionsAdapter



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            toNextScreen()
        }

        collectFlow(viewModel.conditionItems) {
            conditionsAdapter.conditions = it
        }
    }

    private fun toNextScreen() {
        findNavController().navigate(R.id.action_triggerAdditionConditionsFragment_to_triggerAdditionTimeFragment)
    }
}