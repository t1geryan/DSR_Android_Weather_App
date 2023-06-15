package com.example.weatherapp.presentation.ui.locations_lists.base_locations_list_screen

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.os.postDelayed
import com.example.weatherapp.R
import com.example.weatherapp.presentation.event.Event
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui.base_list_screen.BaseListFragment
import com.example.weatherapp.presentation.ui.bottom_navigation_screen.BottomNavigationFragmentDirections
import com.example.weatherapp.presentation.ui.locations_lists.base_locations_list_screen.adapter.LocationsAdapter
import com.example.weatherapp.presentation.ui.locations_lists.base_locations_list_screen.adapter.locationitem.LocationItem
import com.example.weatherapp.presentation.ui_utils.*
import com.example.weatherapp.utils.Constants

abstract class BaseLocationsListFragment : BaseListFragment() {

    abstract val viewModel: BaseLocationsListViewModel

    private lateinit var adapter: LocationsAdapter

    @StringRes
    abstract fun getEmptyListMessage(): Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)

        adapter = LocationsAdapter(unitsSystemProvider(), listener = viewModel)
        binding.recyclerView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(viewLifecycleOwner) {
            collectFlow(viewModel.locationItems) { locationItemsUiState ->
                collectLocationItemListUiState(locationItemsUiState)
            }
            collectFlow(viewModel.isLoadingState) { isLoading ->
                binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
            }
            collectFlow(viewModel.showDetailsEvent) { event ->
                collectShowDetailsEvent(event)
            }
            collectFlow(viewModel.unitsSystemSetting) {
                viewModel.fetchLocationItems()
            }
        }

        binding.locationsListSwipeRefresh.apply {
            setOnRefreshListener {
                viewModel.fetchLocationItems()
                handler.postDelayed(Constants.DELAY.SWIPE_TO_REFRESH_END_DELAY) {
                    isRefreshing = false
                }
            }
        }
    }

    private fun collectLocationItemListUiState(uiState: UiState<List<LocationItem>>) {
        hideSupportingViews()
        when (uiState) {
            is UiState.Loading -> binding.progressBar.visibility = View.VISIBLE
            is UiState.Success -> {
                if (uiState.data.isEmpty()) {
                    showEmptyListMessage(getString(getEmptyListMessage()))
                    adapter.locationsWithWeather = emptyList()
                }
                showLocationItemList(uiState.data)
            }
            is UiState.Error -> showErrorDialogOnLocationListFetching(uiState.exception?.message)
        }
    }

    private fun showLocationItemList(locationItemList: List<LocationItem>) {
        if (!requireContext().hasNetworkConnection()) {
            showRefreshRequest(snackbarProvider) {
                viewModel.fetchLocationItems()
            }
        }
        adapter.locationsWithWeather = locationItemList
    }

    private fun collectShowDetailsEvent(event: Event<LocationItem>) {
        event.getValue()?.let { locationItem ->
            val destination =
                BottomNavigationFragmentDirections.actionBottomNavigationFragmentToWeatherFragment(
                    locationItem.location.id,
                    locationItem.location.name
                )
            findTopLevelNavController().navigate(destination)
        }
    }

    private fun showErrorDialogOnLocationListFetching(message: String?) {
        showErrorDialog(getString(R.string.location_list_loading_exception), message) { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> viewModel.fetchLocationItems()
            }
        }
    }

}