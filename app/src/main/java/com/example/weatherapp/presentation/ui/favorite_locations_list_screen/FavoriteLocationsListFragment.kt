package com.example.weatherapp.presentation.ui.favorite_locations_list_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.presentation.ui.base_locations_list_screen.BaseLocationsListFragment
import com.example.weatherapp.presentation.ui.base_locations_list_screen.BaseLocationsListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteLocationsListFragment : BaseLocationsListFragment() {

    override val viewModel: BaseLocationsListViewModel by viewModels<FavoriteLocationsListViewModel>()

    @StringRes
    override fun getEmptyListMessage(): Int = R.string.no_favorite_locations_message

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