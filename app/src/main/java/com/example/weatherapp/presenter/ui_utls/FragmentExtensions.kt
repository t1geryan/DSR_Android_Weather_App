package com.example.weatherapp.presenter.ui_utls

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import kotlinx.coroutines.launch

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
 * Function for collecting Flow from ViewModel when Fragment gets started
 */
fun Fragment.collectWhenStarted(collectBlock: suspend () -> Unit) =
    viewLifecycleOwner.lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            collectBlock()
        }
    }