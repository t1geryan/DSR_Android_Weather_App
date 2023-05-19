package com.example.weatherapp.presentation.ui.main_activity

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.domain.models.AppUnitsSystem
import com.example.weatherapp.presentation.contract.PermissionCallback
import com.example.weatherapp.presentation.contract.PermissionsApi
import com.example.weatherapp.presentation.contract.SideEffectsApi
import com.example.weatherapp.presentation.contract.UnitsSystemApi
import com.example.weatherapp.presentation.contract.toolbar.*
import com.example.weatherapp.presentation.state.UiState
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Container for all screens in the app.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PermissionsApi, SideEffectsApi, UnitsSystemApi {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainActivityViewModel by viewModels()

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

    private var currentPermissionGrantedCallback: (() -> Unit)? = null

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted)
                currentPermissionGrantedCallback?.invoke()
        }

    private lateinit var appCurrentUnitsSystem: AppUnitsSystem

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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.unitsSystemSetting.collect { unitsSystemUiState ->
                    if (unitsSystemUiState is UiState.Success)
                        appCurrentUnitsSystem = unitsSystemUiState.data
                }
            }
        }
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentCreateViewListener)
        currentFragmentNavController = null
        currentPermissionGrantedCallback = null
        super.onDestroy()
    }
    // Navigation

    private fun updateNavController(navController: NavController) {
        if (currentFragmentNavController == navController) return
        currentFragmentNavController = navController
    }

    // Permissions

    override fun requestPermission(
        permissions: Array<String>,
        allPermissionsGrantedCallback: PermissionCallback?
    ) {
        currentPermissionGrantedCallback = allPermissionsGrantedCallback
        if (permissions.any { permission ->
                ActivityCompat.checkSelfPermission(
                    this,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            currentPermissionGrantedCallback?.invoke()
        } else {
            permissions.forEach { permission ->
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
                ) {
                    // todo add show request permission rationale block
                } else {
                    requestPermissionLauncher.launch(permission)
                }

            }
        }
    }

    override fun hasPermission(permission: String): Boolean =
        ActivityCompat.checkSelfPermission(
            this,
            permission
        ) == PackageManager.PERMISSION_GRANTED

    // Side Effects

    override fun showToast(stringRes: Int) {
        showToast(getString(stringRes))
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // Units System API

    override fun getCurrentUnitsSystem(): AppUnitsSystem = appCurrentUnitsSystem

    override fun updateCurrentUnitsSystem(unitsSystem: AppUnitsSystem) {
        viewModel.setAppUnitsSystem(unitsSystem)
    }

    // UI

    // Updates Toolbar for each Fragment
    private fun updateToolbar(fragment: Fragment) {
        if (fragment is HasNoActivityToolbar) {
            binding.materialToolbar.visibility = View.GONE
        } else {
            // todo: bug: fix blinking when returning from map fragment to tabs fragment
            binding.materialToolbar.visibility = View.VISIBLE

            // if current fragment implements HasCustomTitleToolbar returns its custom title
            val customTitle: String? = (fragment as? HasCustomTitleToolbar)?.getTitle()
            binding.materialToolbar.title =
                customTitle ?: currentFragmentNavController?.currentDestination?.label
                        ?: getString(R.string.app_name)

            // updating custom actions
            binding.materialToolbar.menu.clear()
            if (fragment is HasCustomActionToolbar)
                addCustomToolbarAction(fragment.getCustomAction())
        }
    }

    private fun addCustomToolbarAction(action: ToolbarAction) {
        val iconDrawable =
            DrawableCompat.wrap(
                ContextCompat.getDrawable(this, action.icon)
                    ?: throw IllegalStateException("Illegal Drawable Reference in Custom Action")
            )

        @ColorInt val colorOnPrimary = MaterialColors.getColor(
            this, com.google.android.material.R.attr.colorOnPrimary, Color.BLACK
        )
        iconDrawable.setTint(colorOnPrimary)

        val menuItem = binding.materialToolbar.menu.add(action.title)
        menuItem.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
        menuItem.icon = iconDrawable
        menuItem.setOnMenuItemClickListener {
            action.onAction.run()
            true
        }
    }
}