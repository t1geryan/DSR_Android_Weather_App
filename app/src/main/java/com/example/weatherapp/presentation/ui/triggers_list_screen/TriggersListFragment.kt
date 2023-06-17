package com.example.weatherapp.presentation.ui.triggers_list_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.presentation.ui.base_list_screen.BaseListFragment
import com.example.weatherapp.presentation.ui.triggers_list_screen.adapter.TriggersAdapter
import com.example.weatherapp.presentation.ui_utils.findTopLevelNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TriggersListFragment : BaseListFragment() {

    private lateinit var adapter: TriggersAdapter

    private val viewModel: TriggersListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        adapter = TriggersAdapter(viewModel)
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.itemAddingFab.setOnClickListener {
            startTriggerAddingWizard()
        }
    }

    private fun startTriggerAddingWizard() {
        findTopLevelNavController().navigate(R.id.action_bottomNavigationFragment_to_triggerAdditionsLocationsFragment)
    }
}