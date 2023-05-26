package com.example.weatherapp.presentation.ui_utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.presentation.contract.PermissionsApi
import com.example.weatherapp.presentation.contract.UnitsSystemApi
import com.example.weatherapp.presentation.contract.sideeffects.snakbars.SnackbarProvider
import com.google.android.material.snackbar.Snackbar

/**
 * Function to find top level navController (for using top level actions from BottomNavigationFragment)
 *
 * For example launching WeatherFragment or LocationAdditionMapFragment from AllLocationsFragment
 *
 * @return NavController of rootFragmentContainer if is possible else result of findNavController()
 */
fun Fragment.findTopLevelNavController(): NavController {
    val topLevelHost =
        requireActivity().supportFragmentManager.findFragmentById(R.id.rootFragmentContainer) as NavHostFragment?
    return topLevelHost?.navController ?: findNavController()
}

/**
 * Function to show the user a simple prompt to refresh the screen when not connected to the network.
 */
fun Fragment.showRefreshRequest(
    snackbarProvider: SnackbarProvider,
    onRefresh: () -> Unit
) {
    snackbarProvider.showSnackBar(
        requireActivity().findViewById(android.R.id.content),
        R.string.no_network_connection_error,
        Snackbar.LENGTH_LONG,
        R.string.refresh
    ) {
        onRefresh()
    }
}

/**
 * Function for hiding keyboard from view using [InputMethodManager]
 */
fun Fragment.hideKeyboardFrom(view: View) {
    val inputMethodManager: InputMethodManager =
        requireContext().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

/**
 * Function for getting [PermissionsApi] from the require Activity
 * @throws IllegalStateException if Activity doesn't implement [PermissionsApi]
 */
fun Fragment.permissionsProvider() = requireActivity() as? PermissionsApi
    ?: throw IllegalStateException("Activity doesn't implement PermissionsApi")

/**
 * Function for getting [UnitsSystemApi] from the require Activity
 * @throws IllegalStateException if Activity doesn't implement [UnitsSystemApi]
 */
fun Fragment.unitsSystemProvider() = requireActivity() as? UnitsSystemApi
    ?: throw IllegalStateException("Activity doesn't implement UnitsSystemApi")
