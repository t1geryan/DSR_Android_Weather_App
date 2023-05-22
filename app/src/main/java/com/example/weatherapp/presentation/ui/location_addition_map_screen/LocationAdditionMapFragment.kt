package com.example.weatherapp.presentation.ui.location_addition_map_screen

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentLocationAdditionMapBinding
import com.example.weatherapp.domain.GpsException
import com.example.weatherapp.domain.PermissionException
import com.example.weatherapp.presentation.contract.sideeffects.toasts.ToastProvider
import com.example.weatherapp.presentation.contract.toolbar.HasNoActivityToolbar
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui_utils.collectFlow
import com.example.weatherapp.presentation.ui_utils.getBitmapFromVectorDrawable
import com.example.weatherapp.presentation.ui_utils.permissionsProvider
import com.google.android.gms.location.*
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

    // todo: add autocomplete

    private var hasResult = false
    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    private lateinit var binding: FragmentLocationAdditionMapBinding

    @Inject
    lateinit var toastProvider: ToastProvider

    private val viewModel: LocationAdditionMapViewModel by viewModels()
    //private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var singlePlacemark: PlacemarkMapObject? = null // map single placemark

    private val mapInputListener = object : InputListener {
        override fun onMapTap(map: Map, point: Point) {
            hasResult = true
            latitude = point.latitude
            longitude = point.longitude
            addSinglePlacemarkToMap(point)
            binding.nextButton.visibility = View.VISIBLE
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
                showResult(
                    bundle.getDouble(STATE_KEY_LATITUDE),
                    bundle.getDouble(STATE_KEY_LONGITUDE)
                )
            }
        }

        //fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        MapKitFactory.initialize(requireContext())
        binding.mapview.map.addInputListener(mapInputListener)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            super.onViewCreated(view, savedInstanceState)

            nextButton.setOnClickListener {
                toNextScreen()
            }

            getCurrentLocationButton.setOnClickListener {
                getCurrentLocation()
            }

            navigateUpButton.setOnClickListener {
                findNavController().popBackStack(R.id.bottomNavigationFragment, false)
            }
        }
        collectFlow(viewModel.currentLocation) { uiState ->
            binding.mapProgressBar.visibility = View.INVISIBLE
            when (uiState) {
                is UiState.Loading -> binding.mapProgressBar.visibility = View.VISIBLE
                is UiState.Error -> onCurrentLocationGettingError(uiState.exception)
                is UiState.Success -> {
                    val latLng = uiState.data
                    showResult(latLng.latitude, latLng.longitude)
                }
            }
        }
    }


    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
        // show the previous result when returning back from next screen
        if (hasResult) {
            showResult(latitude, longitude)
        }
    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
        binding.mapview.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapview.map.removeInputListener(mapInputListener)
    }

    // save the state using a bundle (for not to write an extra view model class)
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_KEY_RESULT, hasResult)
        outState.putDouble(STATE_KEY_LATITUDE, latitude)
        outState.putDouble(STATE_KEY_LONGITUDE, longitude)
    }

    @SuppressLint("MissingPermission")
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
                    latitude.toFloat(), longitude.toFloat()
                )
            findNavController().navigate(destination)
        }
    }

    // Map

    private fun showResult(latitude: Double, longitude: Double) {
        val point = Point(latitude, longitude)
        mapInputListener.onMapTap(binding.mapview.map, point)
        moveMapCameraPosition(point)
    }

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
        private const val STATE_KEY_LATITUDE = "STATE_KEY_LATITUDE"
        private const val STATE_KEY_LONGITUDE = "STATE_KEY_LONGITUDE"

        private const val MAP_ZOOM = 8.0f
    }
}
