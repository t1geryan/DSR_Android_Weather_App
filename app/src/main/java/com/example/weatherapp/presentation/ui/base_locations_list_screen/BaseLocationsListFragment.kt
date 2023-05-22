package com.example.weatherapp.presentation.ui.base_locations_list_screen

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.os.postDelayed
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentLocationsListBinding
import com.example.weatherapp.presentation.contract.sideeffects.dialogs.SimpleDialogProvider
import com.example.weatherapp.presentation.contract.sideeffects.snakbars.SnackbarProvider
import com.example.weatherapp.presentation.event.Event
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui.base_locations_list_screen.adapter.LocationItem
import com.example.weatherapp.presentation.ui.base_locations_list_screen.adapter.LocationsAdapter
import com.example.weatherapp.presentation.ui.bottom_navigation_screen.BottomNavigationFragmentDirections
import com.example.weatherapp.presentation.ui_utils.*
import com.example.weatherapp.utils.Constants
import javax.inject.Inject

abstract class BaseLocationsListFragment : Fragment() {

    protected lateinit var binding: FragmentLocationsListBinding

    private lateinit var adapter: LocationsAdapter

    abstract val viewModel: BaseLocationsListViewModel

    @Inject
    lateinit var dialogProvider: SimpleDialogProvider

    @Inject
    lateinit var snackbarProvider: SnackbarProvider

    @StringRes
    abstract fun getEmptyListMessage(): Int

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsListBinding.inflate(inflater, container, false)

        // Add divider between recycler view elements
        binding.locationsRV.addItemDecoration(
            DividerItemDecoration(
                requireContext(), DividerItemDecoration.VERTICAL
            )
        )

        adapter = LocationsAdapter(unitsSystemProvider(), listener = viewModel)
        binding.locationsRV.adapter = adapter

        // Remove default item change animation (reason: annoying blinking)
        val itemAnimator = binding.locationsRV.itemAnimator
        if (itemAnimator is DefaultItemAnimator) itemAnimator.supportsChangeAnimations = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.collectFlow(viewModel.locationItems) { locationItemsUiState ->
            collectLocationItemListUiState(locationItemsUiState)
        }

        viewLifecycleOwner.collectFlow(viewModel.showDetailsEvent) { event ->
            collectShowDetailsEvent(event)
        }

        viewLifecycleOwner.collectFlow(viewModel.unitsSystemSetting) { unitsSystemUiState ->
            if (unitsSystemUiState is UiState.Success)
                viewModel.fetchLocationItems()
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
            is UiState.Error -> showErrorDialog(uiState.exception?.message)
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

    private fun hideSupportingViews() = with(binding) {
        progressBar.visibility = View.GONE
        emptyListMessageTV.visibility = View.GONE
    }

    private fun showEmptyListMessage(message: String) = with(binding.emptyListMessageTV) {
        text = message
        visibility = View.VISIBLE
    }

    private fun showErrorDialog(message: String?) {
        dialogProvider.showSimpleDialog(
            getString(R.string.location_list_loading_exception),
            message ?: getString(R.string.default_exception_message),
            true,
            getString(R.string.refresh),
            null,
            getString(R.string.close),
        ) { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> viewModel.fetchLocationItems()
            }
        }
    }
}