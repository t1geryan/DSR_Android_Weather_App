package com.example.weatherapp.presentation.ui.trigger_addition_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherapp.databinding.FragmentTriggerAdditionBinding
import com.example.weatherapp.presentation.ui.trigger_addition_screen.adapters.condition.ConditionsAdapter
import com.example.weatherapp.presentation.ui_utils.addVerticalDividerItemDecoration
import com.example.weatherapp.presentation.ui_utils.collectFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TriggerAdditionFragment : Fragment() {

    private lateinit var binding: FragmentTriggerAdditionBinding

    private lateinit var conditionsAdapter: ConditionsAdapter

    private val viewModel: TriggerAdditionViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTriggerAdditionBinding.inflate(inflater, container, false)

        binding.conditionsRV.addVerticalDividerItemDecoration(requireContext())
        conditionsAdapter = ConditionsAdapter(viewModel)
        binding.conditionsRV.adapter = conditionsAdapter

        binding.progressBar.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectFlow(viewModel.conditionItems) {
            conditionsAdapter.conditions = it
        }
    }


}