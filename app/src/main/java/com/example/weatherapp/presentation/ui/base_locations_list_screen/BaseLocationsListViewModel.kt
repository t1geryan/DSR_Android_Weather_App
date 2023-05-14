package com.example.weatherapp.presentation.ui.base_locations_list_screen

import androidx.lifecycle.ViewModel
import com.example.weatherapp.domain.repositories.LocationsWeatherRepository
import com.example.weatherapp.presentation.ui.base_locations_list_screen.adapter.LocationItemClickListener
import com.example.weatherapp.presentation.event.Event
import com.example.weatherapp.presentation.event.SingleEvent
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui.base_locations_list_screen.adapter.LocationItem
import com.example.weatherapp.presentation.ui_utils.collectUiState
import com.example.weatherapp.presentation.ui_utils.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseLocationsListViewModel(
    private val locationsWeatherRepository: LocationsWeatherRepository
) : ViewModel(), LocationItemClickListener {
    private val _locationItems = MutableStateFlow<UiState<List<LocationItem>>>(UiState.Loading())
    val locationItems: StateFlow<UiState<List<LocationItem>>>
        get() = _locationItems.asStateFlow()

    private val _showDetailsEvent = MutableSharedFlow<Event<LocationItem>>()
    val showDetailsEvent: SharedFlow<Event<LocationItem>>
        get() = _showDetailsEvent.asSharedFlow()

    // heirs will independently determine how to get the list
    abstract suspend fun fetchLocationItems(): Flow<List<LocationItem>>

    init {
        viewModelScope.launch {
            collectUiState(
                fetchLocationItems(),
                _locationItems
            ) {
                it.isEmpty()
            }
        }
    }

    override fun changeFavoriteStatus(locationItem: LocationItem) {
        viewModelScope.launch {
            locationsWeatherRepository.changeLocationFavoriteStatusById(locationItem.location.id)
        }
    }

    override fun showDetails(locationItem: LocationItem) {
        viewModelScope.launch {
            _showDetailsEvent.emit(SingleEvent(locationItem))
        }
    }
}