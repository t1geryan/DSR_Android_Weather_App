package com.example.weatherapp.presentation.ui.locations_lists.base_locations_list_screen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.AppUnitsSystem
import com.example.weatherapp.domain.repositories.LocationsWeatherRepository
import com.example.weatherapp.domain.repositories.SettingsRepository
import com.example.weatherapp.presentation.event.Event
import com.example.weatherapp.presentation.event.SingleEvent
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui.locations_lists.base_locations_list_screen.adapter.locationitem.LocationItem
import com.example.weatherapp.presentation.ui.locations_lists.base_locations_list_screen.adapter.locationitem.LocationItemClickListener
import com.example.weatherapp.presentation.ui_utils.collectUiStateFromFlow
import com.example.weatherapp.presentation.ui_utils.tryEmitFlow
import com.example.weatherapp.presentation.ui_utils.viewModelScopeIO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseLocationsListViewModel(
    private val locationsWeatherRepository: LocationsWeatherRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel(), LocationItemClickListener {
    private val _locationItems = MutableStateFlow<UiState<List<LocationItem>>>(UiState.Loading())
    val locationItems: StateFlow<UiState<List<LocationItem>>>
        get() = _locationItems.asStateFlow()

    private val _showDetailsEvent = MutableSharedFlow<Event<LocationItem>>()
    val showDetailsEvent: SharedFlow<Event<LocationItem>>
        get() = _showDetailsEvent.asSharedFlow()

    private val _unitsSystemSetting = MutableStateFlow<AppUnitsSystem?>(null)
    val unitsSystemSetting: StateFlow<AppUnitsSystem?>
        get() = _unitsSystemSetting.asStateFlow()

    private val _isLoadingState = MutableStateFlow(false)
    val isLoadingState: StateFlow<Boolean>
        get() = _isLoadingState.asStateFlow()

    // heirs will independently determine how to get the list
    protected abstract suspend fun getLocationItemsFromRepository(): Flow<List<LocationItem>>

    init {
        fetchUnitsSystemSetting()
        fetchLocationItems()
    }

    private fun fetchUnitsSystemSetting() {
        tryEmitFlow {
            settingsRepository.getCurrentUnitsSystem().collect {
                _unitsSystemSetting.emit(it)
            }
        }
    }

    fun fetchLocationItems() {
        collectUiStateFromFlow(_locationItems) {
            getLocationItemsFromRepository()
        }
    }

    override fun onFavoriteStatusButtonClickListener(locationItem: LocationItem) {
        viewModelScopeIO.launch {
            _isLoadingState.value = false
            locationsWeatherRepository.changeLocationFavoriteStatusById(locationItem.location.id)
            _isLoadingState.value = true
        }
    }

    override fun onItemClickListener(locationItem: LocationItem) {
        viewModelScopeIO.launch {
            _showDetailsEvent.emit(SingleEvent(locationItem))
        }
    }
}