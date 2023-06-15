package com.example.weatherapp.presentation.ui.location_addition_wizard.location_addition_map_screen

import android.Manifest
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentLocationAdditionMapBinding
import com.example.weatherapp.domain.ConnectionException
import com.example.weatherapp.domain.GpsException
import com.example.weatherapp.domain.PermissionException
import com.example.weatherapp.domain.models.GeocodingResult
import com.example.weatherapp.domain.models.LatLng
import com.example.weatherapp.presentation.contract.sideeffects.dialogs.SimpleDialogProvider
import com.example.weatherapp.presentation.contract.sideeffects.toasts.ToastProvider
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui.location_addition_wizard.location_addition_map_screen.state.LocationAdditionState
import com.example.weatherapp.presentation.ui_utils.collectFlow
import com.example.weatherapp.presentation.ui_utils.getBitmapFromVectorDrawable
import com.example.weatherapp.presentation.ui_utils.hideKeyboardFrom
import com.example.weatherapp.presentation.ui_utils.permissionsProvider
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.runtime.image.ImageProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LocationAdditionMapFragment : Fragment() {

    private lateinit var binding: FragmentLocationAdditionMapBinding

    @Inject
    lateinit var toastProvider: ToastProvider

    @Inject
    lateinit var dialogProvider: SimpleDialogProvider

    private val viewModel: LocationAdditionMapViewModel by viewModels()

    private var singlePlacemark: PlacemarkMapObject? = null // map single placemark

    private val mapInputListener = object : InputListener {
        override fun onMapTap(map: Map, point: Point) {
            val latLng = LatLng(point.latitude.toFloat(), point.longitude.toFloat())
            rememberResult(latLng, shouldMoveCameraPosition = false)
        }

        override fun onMapLongTap(map: Map, point: Point) {
            onMapTap(map, point)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationAdditionMapBinding.inflate(inflater, container, false)
        binding.nextButton.visibility = View.INVISIBLE
        binding.mapProgressBar.visibility = View.INVISIBLE

        MapKitFactory.initialize(requireContext())
        binding.mapview.map.addInputListener(mapInputListener)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            super.onViewCreated(view, savedInstanceState)
            collectFlow(viewModel.currentLocation) { uiState ->
                mapProgressBar.visibility = View.INVISIBLE
                collectCurrentLocationUiState(uiState)
            }
            collectFlow(viewModel.autocompleteData) { autocompleteUiState ->
                collectAutocompleteUiState(autocompleteUiState)
            }

            collectFlow(viewModel.geocodingResult) { geocodingResult ->
                collectGeocodingResult(geocodingResult)
            }
            collectFlow(viewModel.locationAdditionState) { locationAdditionState ->
                showResult(locationAdditionState)
            }

            nextButton.setOnClickListener {
                toNextScreen()
            }

            getCurrentLocationButton.setOnClickListener {
                getCurrentLocation()
            }

            locationAutoCompleteLayout.setStartIconOnClickListener {
                val input = locationsAutoCompleteTV.text.toString()
                viewModel.getCoordinatesByLocationName(input)
            }

            locationsAutoCompleteTV.addTextChangedListener {
                viewModel.getPlacesAutocompleteDataByInput(it.toString())
            }

            locationsAutoCompleteTV.setOnEditorActionListener { textView, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    viewModel.getCoordinatesByLocationName(textView.text.toString())
                    if (requireContext().resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
                        hideKeyboardFrom(textView)
                    return@setOnEditorActionListener true
                }
                false
            }
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
        // be sure to move the camera when returning from the next fragment
        showResult(viewModel.locationAdditionState.value.copy(shouldMoveCameraPosition = true))
    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
        binding.mapview.onStop()
    }

    override fun onDestroyView() {
        binding.mapview.map.removeInputListener(mapInputListener)
        super.onDestroyView()
    }

    // autocomplete api
    private fun collectAutocompleteUiState(uiState: UiState<List<String>>) {
        binding.locationsAutoCompleteTV.error = when (uiState) {
            is UiState.Loading -> null
            is UiState.Success -> {
                setupAutocompleteAdapter(uiState.data)
                null
            }
            is UiState.Error -> when (uiState.exception) {
                is ConnectionException -> getString(R.string.network_error)
                else -> getString(R.string.error)
            }
        }
    }

    private fun setupAutocompleteAdapter(data: List<String>) {
        binding.locationsAutoCompleteTV.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                data
            )
        )
    }
    //

    // geocoding api
    private fun collectGeocodingResult(geocodingResult: GeocodingResult?) {
        geocodingResult?.let {
            val locationName = "${it.locationName}, ${it.countryName}"
            rememberResult(it.latLng, locationName)
        } ?: toastProvider.showToast(R.string.empty_geocoding_result_message)
    }
    //

    // current location api
    private fun getCurrentLocation() {
        var isCalled = false
        val locationPermissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION)
        permissionsProvider().requestPermission(
            locationPermissions
        ) {
            isCalled = true
            viewModel.getCurrentLocation()
        }
        if (!isCalled)
            toastProvider.showToast(R.string.current_location_permissions_exception)
    }

    private fun collectCurrentLocationUiState(uiState: UiState<LatLng>) {
        when (uiState) {
            is UiState.Loading -> binding.mapProgressBar.visibility = View.VISIBLE
            is UiState.Error -> onCurrentLocationGettingError(uiState.exception)
            is UiState.Success -> rememberResult(uiState.data)
        }
    }

    private fun onCurrentLocationGettingError(exception: Exception?) {
        when (exception) {
            is GpsException -> toastProvider.showToast(R.string.gps_off_error)
            is PermissionException -> toastProvider.showToast(R.string.current_location_permissions_exception)
            else -> toastProvider.showToast(
                exception?.message
                    ?: getString(R.string.default_exception_message)
            )
        }
    }
    //

    private fun toNextScreen() {
        with(viewModel.locationAdditionState.value) {
            if (hasResult) {
                val destination =
                    LocationAdditionMapFragmentDirections.actionLocationAdditionMapFragmentToLocationAdditionNameFragment(
                        latLng, enteredLocationName
                    )
                findNavController().navigate(destination)
            }
        }
    }

    // work with state
    private fun rememberResult(
        latLng: LatLng,
        locationName: String = "",
        shouldMoveCameraPosition: Boolean = true,
    ) {
        viewModel.updateLocationAdditionState(
            LocationAdditionState(
                latLng,
                true,
                locationName,
                shouldMoveCameraPosition,
                binding.mapview.map.cameraPosition.zoom,
            )
        )
    }

    private fun showResult(locationAdditionState: LocationAdditionState) {
        with(locationAdditionState) {
            val point = Point(latLng.latitude.toDouble(), latLng.longitude.toDouble())
            if (shouldMoveCameraPosition) {
                moveMapCameraPosition(point, currentMapZoom)
            }
            if (hasResult) {
                binding.nextButton.visibility = View.VISIBLE
                addSinglePlacemarkToMap(point)
            }
        }
    }
    //

    // map
    private fun moveMapCameraPosition(point: Point, zoom: Float) = binding.mapview.map.move(
        CameraPosition(point, zoom, 0.0f, 0.0f),
        Animation(Animation.Type.SMOOTH, 0.0f),
        null
    )

    private fun addSinglePlacemarkToMap(point: Point) {
        singlePlacemark?.let {
            it.isVisible = false // make previous placemark invisible
        }
        singlePlacemark = binding.mapview.map.mapObjects.addPlacemark(
            point,
            ImageProvider.fromBitmap(requireContext().getBitmapFromVectorDrawable(R.drawable.icon_location_on_24))
        )
    }
    //
}
