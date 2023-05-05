package com.example.weatherapp.presenter.ui.main_activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.presenter.contract.HasCustomTitleToolbar
import com.example.weatherapp.presenter.contract.ScreenContainer
import dagger.hilt.android.AndroidEntryPoint

/**
 * Container for all screens in the app.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var currentFragmentNavController: NavController? = null

    private val fragmentCreateViewListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (f is NavHostFragment || f is ScreenContainer || f is DialogFragment) return
            updateNavController(f.findNavController())
            updateToolbar(f)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        // first we use the root navigator controller to bind the toolbar,
        // but later will be used the controller of the current fragment
        val navHost =
            supportFragmentManager.findFragmentById(R.id.rootFragmentContainer) as NavHostFragment
        val navController = navHost.navController
        binding.materialToolbar.setupWithNavController(navController)

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentCreateViewListener, true)
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCreateViewListener)
        currentFragmentNavController = null
        super.onDestroy()
    }
    // Navigation

    private fun updateNavController(navController: NavController) {
        if (currentFragmentNavController == navController) return
        currentFragmentNavController = navController
    }

    // UI

    // Updates Toolbar for each Fragment
    private fun updateToolbar(fragment: Fragment) {
        // if current fragment implements HasCustomTitleToolbar returns its custom title
        val customTitle: String? = (fragment as? HasCustomTitleToolbar)?.getTitle()
        binding.materialToolbar.title =
            customTitle ?: currentFragmentNavController?.currentDestination?.label
                    ?: getString(R.string.app_name)
    }
}