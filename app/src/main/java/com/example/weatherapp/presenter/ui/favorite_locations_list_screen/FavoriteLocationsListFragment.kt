package com.example.weatherapp.presenter.ui.favorite_locations_list_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.presenter.ui.base_locations_list_screen.BaseLocationsListFragment

class FavoriteLocationsListFragment : BaseLocationsListFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding.addLocationFAB.visibility = View.GONE
        return binding.root
    }
}