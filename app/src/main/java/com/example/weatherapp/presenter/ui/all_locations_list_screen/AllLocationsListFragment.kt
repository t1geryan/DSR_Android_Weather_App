package com.example.weatherapp.presenter.ui.all_locations_list_screen

import android.os.Bundle
import android.view.View
import com.example.weatherapp.R
import com.example.weatherapp.presenter.ui.base_locations_list_screen.BaseLocationsListFragment
import com.example.weatherapp.presenter.ui_utls.findTopLevelNavController

class AllLocationsListFragment : BaseLocationsListFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addLocationFAB.setOnClickListener {
            addNewLocation()
        }
    }

    private fun addNewLocation() {
        findTopLevelNavController().navigate(R.id.action_bottomNavigationFragment_to_locationAdditionMapFragment)
    }
}