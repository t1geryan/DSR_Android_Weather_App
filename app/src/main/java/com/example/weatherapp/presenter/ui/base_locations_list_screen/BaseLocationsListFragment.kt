package com.example.weatherapp.presenter.ui.base_locations_list_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentLocationsListBinding
import com.example.weatherapp.domain.models.LocationItem
import com.example.weatherapp.presenter.state.UiState
import com.example.weatherapp.presenter.ui.base_locations_list_screen.adapter.LocationsAdapter
import com.example.weatherapp.presenter.ui_utls.collectWhenStarted

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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = LocationsAdapter(listener = viewModel)
        binding.locationsRV.adapter = adapter

        collectWhenStarted {
            viewModel.locationItems.collect { locationItems ->
                collectLocationItemsUiState(locationItems)
            }
        }
    }

    private fun collectLocationItemsUiState(uiState: UiState<List<LocationItem>>) {
        hideSupportingViews()
        when (uiState) {
            is UiState.Loading -> binding.progressBar.visibility = View.VISIBLE
            is UiState.Success -> adapter.locationsWithWeather = uiState.data
            is UiState.EmptyOrNull -> showEmptyListMessage(getString(getEmptyListMessage()))
            is UiState.Error -> showErrorDialog(uiState.message)
        }
    }

    private fun hideSupportingViews() {
        binding.progressBar.visibility = View.GONE
        binding.emptyListMessageTV.visibility = View.GONE
    }

    private fun showEmptyListMessage(message: String) = with(binding.emptyListMessageTV) {
        binding.emptyListMessageTV.text = message
        binding.emptyListMessageTV.visibility = View.VISIBLE
    }

    private fun showErrorDialog(message: String?) {
        // todo (not implemented yet - replaced by mock)
        Toast.makeText(
            requireContext(),
            message ?: getString(R.string.default_exception_message),
            Toast.LENGTH_SHORT
        ).show()
    }
}