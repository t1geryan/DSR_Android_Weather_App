package com.example.weatherapp.presentation.ui.all_locations_list_screen

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.presentation.ui.base_locations_list_screen.BaseLocationsListFragment
import com.example.weatherapp.presentation.ui.base_locations_list_screen.BaseLocationsListViewModel
import com.example.weatherapp.presentation.ui_utils.findTopLevelNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllLocationsListFragment : BaseLocationsListFragment() {

    override val viewModel: BaseLocationsListViewModel by viewModels<AllLocationsListViewModel>()

    @StringRes
    override fun getEmptyListMessage(): Int = R.string.empty_locations_list_message

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.itemAddingFab.setOnClickListener {
            startLocationAddingWizard()
        }
    }

    private fun startLocationAddingWizard() {
        findTopLevelNavController().navigate(R.id.action_bottomNavigationFragment_to_locationAdditionMapFragment)
    }
}