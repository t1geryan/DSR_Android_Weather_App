package com.example.weatherapp.presentation.ui.location_addition_map_screen

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
import com.example.weatherapp.domain.GpsException
import com.example.weatherapp.domain.PermissionException
import com.example.weatherapp.domain.models.GeocodingResult
import com.example.weatherapp.domain.models.LatLng
import com.example.weatherapp.presentation.contract.sideeffects.toasts.ToastProvider
import com.example.weatherapp.presentation.contract.toolbar.HasNoActivityToolbar
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui_utils.*
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
class LocationAdditionMapFragment : Fragment(), HasNoActivityToolbar {

    private var enteredLocationName = ""
    private var hasResult = false
    private var latLng = LatLng(0.0f, 0.0f)

    private lateinit var binding: FragmentLocationAdditionMapBinding

    @Inject
    lateinit var toastProvider: ToastProvider

    private val viewModel: LocationAdditionMapViewModel by viewModels()

    private var singlePlacemark: PlacemarkMapObject? = null // map single placemark

    private val mapInputListener = object : InputListener {
        override fun onMapTap(map: Map, point: Point) {
            val latLng = LatLng(point.latitude.toFloat(), point.longitude.toFloat())
            showAndRememberResult(latLng, "", false)
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

        savedInstanceState?.let { bundle ->
            if (bundle.getBoolean(STATE_KEY_RESULT)) {
                enteredLocationName = bundle.getString(STATE_KEY_ENTERED_NAME, "")
                val latLng = bundle.getParcelableData(STATE_KEY_LATLNG, LatLng::class.java)
                latLng?.let {
                    showAndRememberResult(it, enteredLocationName)
                }
            }
        }
        MapKitFactory.initialize(requireContext())
        binding.mapview.map.move(
            CameraPosition(INITIAL_CAMERA_POSITION, MAP_ZOOM, 0.0f, 0.0f)
        )
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
            collectFlow(viewModel.autocompleteData) { autocompleteData ->
                setupAutocompleteAdapter(autocompleteData)
            }

            collectFlow(viewModel.geocodingResult) { geocodingResult ->
                collectGeocodingResult(geocodingResult)
            }

            nextButton.setOnClickListener {
                toNextScreen()
            }

            getCurrentLocationButton.setOnClickListener {
                getCurrentLocation()
            }

            locationAutoCompleteLayout.setEndIconOnClickListener {
                val input = locationsAutoCompleteTV.text.toString()
                viewModel.getCoordinatesByLocationName(input)
            }

            locationAutoCompleteLayout.setStartIconOnClickListener {
                findNavController().popBackStack(R.id.bottomNavigationFragment, false)
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
        // show the previous result when returning back from next screen
        if (hasResult) {
            showAndRememberResult(latLng, enteredLocationName)
        }
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_KEY_RESULT, hasResult)
        outState.putParcelable(STATE_KEY_LATLNG, latLng)
        outState.putString(STATE_KEY_ENTERED_NAME, enteredLocationName)
    }

    private fun collectCurrentLocationUiState(uiState: UiState<LatLng>) {
        when (uiState) {
            is UiState.Loading -> binding.mapProgressBar.visibility = View.VISIBLE
            is UiState.Error -> onCurrentLocationGettingError(uiState.exception)
            is UiState.Success -> showAndRememberResult(uiState.data, "")
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

    private fun collectGeocodingResult(geocodingResult: GeocodingResult?) {
        geocodingResult?.let {
            val locationName = "${it.locationName}, ${it.countryName}"
            showAndRememberResult(it.latLng, locationName)
        } ?: toastProvider.showToast(R.string.empty_geocoding_result_message)
    }

    private fun getCurrentLocation() {
        var isCalled = false
        permissionsProvider().requestPermission(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        ) {
            isCalled = true
            viewModel.getCurrentLocation()
        }
        if (!isCalled)
            toastProvider.showToast(R.string.current_location_permissions_exception)
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

    private fun toNextScreen() {
        if (hasResult) {
            val destination =
                LocationAdditionMapFragmentDirections.actionLocationAdditionMapFragmentToLocationAdditionNameFragment(
                    latLng, enteredLocationName
                )
            findNavController().navigate(destination)
        }
    }

    private fun showAndRememberResult(
        latLng: LatLng,
        locationName: String,
        shouldMoveCameraPosition: Boolean = true
    ) {
        rememberResult(latLng, locationName)
        showResult(latLng, shouldMoveCameraPosition)
    }

    private fun rememberResult(latLng: LatLng, locationName: String) {
        hasResult = true
        this.latLng = latLng
        enteredLocationName = locationName
    }

    private fun showResult(latLng: LatLng, shouldMoveCameraPosition: Boolean = true) {
        binding.nextButton.visibility = View.VISIBLE

        val point = Point(latLng.latitude.toDouble(), latLng.longitude.toDouble())
        addSinglePlacemarkToMap(point)
        if (shouldMoveCameraPosition)
            moveMapCameraPosition(point)
    }

    // Map

    private fun moveMapCameraPosition(point: Point) = binding.mapview.map.move(
        CameraPosition(point, MAP_ZOOM, 0.0f, 0.0f),
        Animation(Animation.Type.SMOOTH, 0.0f),
        null
    )

    private fun addSinglePlacemarkToMap(point: Point) {
        singlePlacemark?.let {
            it.isVisible = false // make previous placemark invisible
        }
        // add new placemark
        singlePlacemark = binding.mapview.map.mapObjects.addPlacemark(
            point,
            ImageProvider.fromBitmap(requireContext().getBitmapFromVectorDrawable(R.drawable.icon_location_on_24))
        )
    }

    //

    companion object {
        private const val STATE_KEY_RESULT = "STATE_KEY_RESULT"
        private const val STATE_KEY_LATLNG = "STATE_KEY_LATLNG"
        private const val STATE_KEY_ENTERED_NAME = "STATE_KEY_ENTERED_NAME"

        private const val MAP_ZOOM = 8.0f
        private val INITIAL_CAMERA_POSITION = Point(55.751574, 37.573856)
    }
}
