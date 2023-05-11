package com.example.weatherapp.presentation.ui.base_locations_list_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentLocationsListBinding
import com.example.weatherapp.domain.models.LocationItem
import com.example.weatherapp.presentation.contract.sideEffectsProvider
import com.example.weatherapp.presentation.event.Event
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui.base_locations_list_screen.adapter.LocationsAdapter
import com.example.weatherapp.presentation.ui.bottom_navigation_screen.BottomNavigationFragmentDirections
import com.example.weatherapp.presentation.ui_utils.collectWhenStarted
import com.example.weatherapp.presentation.ui_utils.findTopLevelNavController

abstract class BaseLocationsListFragment : Fragment() {

    protected lateinit var binding: FragmentLocationsListBinding

    private lateinit var adapter: LocationsAdapter

    abstract val viewModel: BaseLocationsListViewModel

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

        // Remove default item change animation (reason: annoying blinking)
        val itemAnimator = binding.locationsRV.itemAnimator
        if (itemAnimator is DefaultItemAnimator) itemAnimator.supportsChangeAnimations = false

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LocationsAdapter(listener = viewModel)
        binding.locationsRV.adapter = adapter

        collectWhenStarted {
            viewModel.locationItems.collect { locationItems ->
                collectLocationItemListUiState(locationItems)
            }
        }
        collectWhenStarted {
            viewModel.showDetailsEvent.collect { event ->
                collectShowDetailsEvent(event)
            }
        }
    }

    private fun collectLocationItemListUiState(uiState: UiState<List<LocationItem>>) {
        hideSupportingViews()
        when (uiState) {
            is UiState.Loading -> binding.progressBar.visibility = View.VISIBLE
            is UiState.Success -> adapter.locationsWithWeather = uiState.data
            is UiState.EmptyOrNull -> {
                showEmptyListMessage(getString(getEmptyListMessage()))
                // must be replaced with an empty list, since the replacement occurs only in the Success block
                adapter.locationsWithWeather = listOf()
            }
            is UiState.Error -> showErrorDialog(uiState.message)
        }
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
        // todo (not implemented yet - replaced by mock)
        sideEffectsProvider().showToast(message ?: getString(R.string.default_exception_message))
    }
}