package com.example.weatherapp.presentation.ui.main_activity

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.domain.models.AppTheme
import com.example.weatherapp.domain.models.AppUnitsSystem
import com.example.weatherapp.presentation.contract.PermissionCallback
import com.example.weatherapp.presentation.contract.PermissionsApi
import com.example.weatherapp.presentation.contract.SideEffectsApi
import com.example.weatherapp.presentation.contract.UnitsSystemApi
import com.example.weatherapp.presentation.contract.toolbar.*
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui_utils.collectFlow
import com.example.weatherapp.presentation.ui_utils.getStringOrNull
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

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

        collectFlow(viewModel.unitsSystemSetting) { unitsSystemUiState ->
            if (unitsSystemUiState is UiState.Success) {
                appCurrentUnitsSystem = unitsSystemUiState.data
            }
        }
        collectFlow(viewModel.appThemeSetting) { appThemeUiState ->
            if (appThemeUiState is UiState.Success)
                changeAppTheme(appThemeUiState.data)
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

    override fun hasPermissions(permissions: Array<String>): Boolean =
        permissions.any { permission ->
            ActivityCompat.checkSelfPermission(
                this,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }


    // Side Effects

    override fun showToast(stringRes: Int) {
        showToast(getString(stringRes))
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showSnackBar(
        @StringRes stringRes: Int,
        duration: Int,
        @StringRes actionTitle: Int?,
        onAction: View.OnClickListener?
    ) {
        showSnackBar(
            getString(stringRes),
            duration,
            actionTitle?.let { getString(it) },
            onAction
        )
    }

    override fun showSnackBar(
        message: String,
        duration: Int,
        actionTitle: String?,
        onAction: View.OnClickListener?
    ) {
        val snackbar = Snackbar.make(binding.root, message, duration)
        if (actionTitle != null && onAction != null)
            snackbar.setAction(actionTitle, onAction)
        snackbar.show()
    }

    override fun showSimpleDialog(
        title: String,
        message: String,
        isCancelable: Boolean,
        positiveButtonTitle: String?,
        negativeButtonTitle: String?,
        neutralButtonTitle: String?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setCancelable(isCancelable)
            .apply {
                positiveButtonTitle?.let {
                    setPositiveButton(it, onClickListener)
                }
                negativeButtonTitle?.let {
                    setNegativeButton(it, onClickListener)
                }
                neutralButtonTitle?.let {
                    setNeutralButton(it, onClickListener)
                }
            }
            .show()
    }

    override fun showSimpleDialog(
        titleRes: Int,
        messageRes: Int,
        isCancelable: Boolean,
        positiveButtonTitleRes: Int?,
        negativeButtonTitleRes: Int?,
        neutralButtonTitleRes: Int?,
        onClickListener: DialogInterface.OnClickListener?
    ) {
        showSimpleDialog(
            getString(titleRes),
            getString(messageRes),
            isCancelable,
            getStringOrNull(positiveButtonTitleRes),
            getStringOrNull(negativeButtonTitleRes),
            getStringOrNull(neutralButtonTitleRes),
            onClickListener
        )
    }
    // Units System API

    private var appCurrentUnitsSystem: AppUnitsSystem =
        AppUnitsSystem.METRIC_SYSTEM

    override fun getCurrentUnitsSystem(): AppUnitsSystem = appCurrentUnitsSystem

    override fun updateCurrentUnitsSystem(unitsSystem: AppUnitsSystem) {
        viewModel.setAppUnitsSystem(unitsSystem)
    }

    // UI

    private fun changeAppTheme(appThemeSetting: AppTheme) {
        AppCompatDelegate.setDefaultNightMode(
            getNightModeByThemeSettings(
                appThemeSetting
            )
        )
    }

    private fun getNightModeByThemeSettings(themeSetting: AppTheme) =
        when (themeSetting) {
            AppTheme.DAY_THEME -> AppCompatDelegate.MODE_NIGHT_NO
            AppTheme.NIGHT_THEME -> AppCompatDelegate.MODE_NIGHT_YES
            AppTheme.SYSTEM_THEME -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

    // Updates Toolbar for each started Fragment
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