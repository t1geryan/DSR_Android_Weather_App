package com.example.weatherapp.presenter.ui.base_locations_list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.models.LocationItem
import com.example.weatherapp.domain.repositories.LocationListRepository
import com.example.weatherapp.presenter.contract.LocationItemClickListener
import com.example.weatherapp.presenter.event.Event
import com.example.weatherapp.presenter.event.SingleEvent
import com.example.weatherapp.presenter.state.UiState
import com.example.weatherapp.presenter.ui_utls.collectUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseLocationsListViewModel(
    private val locationListRepository: LocationListRepository
) : ViewModel(), LocationItemClickListener {
    private val _locationItems = MutableStateFlow<UiState<List<LocationItem>>>(UiState.Loading())
    val locationItems: StateFlow<UiState<List<LocationItem>>>
        get() = _locationItems.asStateFlow()

    private val _showDetailsEvent = MutableSharedFlow<Event<LocationItem>>()
    val showDetailsEvent: SharedFlow<Event<LocationItem>>
        get() = _showDetailsEvent.asSharedFlow()

    abstract val isOnlyFavoriteContacts: Boolean

    init {
        viewModelScope.launch(Dispatchers.IO) {
            collectUiState(
                locationListRepository.getAllLocationsWeather(isOnlyFavoriteContacts),
                _locationItems
            ) {
                it.isEmpty()
            }
        }
    }

    override fun changeFavoriteStatus(locationItem: LocationItem) {
        viewModelScope.launch {
            locationListRepository.changeLocationFavoriteStatusById(locationItem.location.id)
        }
    }

    override fun showDetails(locationItem: LocationItem) {
        viewModelScope.launch {
            _showDetailsEvent.emit(SingleEvent(locationItem))
        }
    }
}