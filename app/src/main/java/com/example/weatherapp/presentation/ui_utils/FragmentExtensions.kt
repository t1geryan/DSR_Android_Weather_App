package com.example.weatherapp.presentation.ui_utils

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.presentation.contract.PermissionsApi
import com.example.weatherapp.presentation.contract.SideEffectsApi
import com.example.weatherapp.presentation.contract.UnitsSystemApi

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
 * Function for getting [PermissionsApi] from the require Activity
 * @throws IllegalStateException if Activity doesn't implement [PermissionsApi]
 */
fun Fragment.permissionsProvider() = requireActivity() as? PermissionsApi
    ?: throw IllegalStateException("Activity doesn't implement PermissionsApi")

/**
 * Function for getting [SideEffectsApi] from the require Activity
 * @throws IllegalStateException if Activity doesn't implement [SideEffectsApi]
 */
fun Fragment.sideEffectsProvider() = requireActivity() as? SideEffectsApi
    ?: throw IllegalStateException("Activity doesn't implement SideEffectsApi")

/**
 * Function for getting [UnitsSystemApi] from the require Activity
 * @throws IllegalStateException if Activity doesn't implement [UnitsSystemApi]
 */
fun Fragment.unitsSystemProvider() = requireActivity() as? UnitsSystemApi
    ?: throw IllegalStateException("Activity doesn't implement UnitsSystemApi")
