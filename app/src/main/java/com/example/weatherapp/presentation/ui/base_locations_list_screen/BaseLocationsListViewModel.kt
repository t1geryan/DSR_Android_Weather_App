package com.example.weatherapp.presentation.ui.base_locations_list_screen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.models.AppUnitsSystem
import com.example.weatherapp.domain.repositories.LocationsWeatherRepository
import com.example.weatherapp.domain.repositories.SettingsRepository
import com.example.weatherapp.presentation.event.Event
import com.example.weatherapp.presentation.event.SingleEvent
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui.base_locations_list_screen.adapter.LocationItem
import com.example.weatherapp.presentation.ui.base_locations_list_screen.adapter.LocationItemClickListener
import com.example.weatherapp.presentation.ui_utils.collectUiState
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

    private val _unitsSystemSetting = MutableStateFlow<UiState<AppUnitsSystem>>(UiState.Loading())
    val unitsSystemSetting: StateFlow<UiState<AppUnitsSystem>>
        get() = _unitsSystemSetting.asStateFlow()

    // heirs will independently determine how to get the list
    protected abstract suspend fun getLocationItemsFromRepository(): Flow<List<LocationItem>>

    init {
        viewModelScopeIO.launch {
            collectUiState(
                settingsRepository.getCurrentUnitsSystem(),
                _unitsSystemSetting
            )
        }
        fetchLocationItems()
    }

    fun fetchLocationItems() {
        viewModelScopeIO.launch {
            collectUiState(
                getLocationItemsFromRepository(),
                _locationItems,
            )
        }
    }

    override fun changeFavoriteStatus(locationItem: LocationItem) {
        viewModelScopeIO.launch {
            locationsWeatherRepository.changeLocationFavoriteStatusById(locationItem.location.id)
        }
    }

    override fun showDetails(locationItem: LocationItem) {
        viewModelScopeIO.launch {
            _showDetailsEvent.emit(SingleEvent(locationItem))
        }
    }
}