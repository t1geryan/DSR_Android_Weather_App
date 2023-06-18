package com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_locations_screen

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentTriggerAdditionLocationsBinding
import com.example.weatherapp.presentation.contract.sideeffects.dialogs.SimpleDialogProvider
import com.example.weatherapp.presentation.contract.sideeffects.toasts.ToastProvider
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_locations_screen.adapter.LocationsCheckAdapter
import com.example.weatherapp.presentation.ui.trigger_addition_wizard.trigger_addition_locations_screen.adapter.locationcheckitem.LocationCheckItem
import com.example.weatherapp.presentation.ui_utils.collectFlow
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TriggerAdditionLocationsFragment : Fragment() {

    private lateinit var binding: FragmentTriggerAdditionLocationsBinding

    private lateinit var adapter: LocationsCheckAdapter

    private val viewModel: TriggerAdditionLocationsViewModel by viewModels()

    @Inject
    lateinit var dialogProvider: SimpleDialogProvider

    @Inject
    lateinit var toastProvider: ToastProvider

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTriggerAdditionLocationsBinding.inflate(inflater, container, false)

        adapter = LocationsCheckAdapter()
        binding.locationsChoosingRV.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextButton.setOnClickListener {
            toNextScreen()
        }

        collectFlow(viewModel.locationCheckItems) { uiState ->
            collectLocationCheckItems(uiState)
        }
    }

    private fun collectLocationCheckItems(uiState: UiState<List<LocationCheckItem>>) {
        with(binding) {
            progressBar.visibility = View.INVISIBLE
            emptyListMessageTV.visibility = View.INVISIBLE
            when (uiState) {
                is UiState.Loading -> progressBar.visibility = View.VISIBLE
                is UiState.Success -> {
                    val list = uiState.data
                    if (list.isEmpty())
                        emptyListMessageTV.visibility = View.VISIBLE
                    adapter.locationCheckItems = uiState.data
                }
                is UiState.Error -> showErrorDialog(uiState.exception?.message)
            }
        }
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
            if (which == DialogInterface.BUTTON_POSITIVE)
                viewModel.fetchLocationCheckItems()
        }
    }

    private fun toNextScreen() {
        val selectedLocationsId = adapter.getSelectedLocationsId().toLongArray()
        if (selectedLocationsId.isEmpty()) {
            toastProvider.showToast(R.string.no_locations_choosen_message)
        } else {
            findNavController().navigate(
                TriggerAdditionLocationsFragmentDirections.actionTriggerAdditionsLocationsFragmentToTriggerAdditionConditionsFragment(
                    selectedLocationsId
                )
            )
        }
    }
}