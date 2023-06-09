package com.example.weatherapp.presentation.ui.triggers_list_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.R
import com.example.weatherapp.presentation.ui.base_list_screen.BaseListFragment
import com.example.weatherapp.presentation.ui_utils.findTopLevelNavController

class TriggersListFragment : BaseListFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.itemAddingFab.setOnClickListener {
            startTriggerAddingWizard()
        }
    }

    private fun startTriggerAddingWizard() {
        findTopLevelNavController().navigate(R.id.action_bottomNavigationFragment_to_triggerAdditionFragment)
    }
}