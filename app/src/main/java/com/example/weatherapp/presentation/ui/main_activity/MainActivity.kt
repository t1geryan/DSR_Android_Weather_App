package com.example.weatherapp.presentation.ui.main_activity

import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
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
import com.example.weatherapp.presentation.contract.*
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.AndroidEntryPoint

/**
 * Container for all screens in the app.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity(), PermissionsApi {

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

    // UI

    // Updates Toolbar for each Fragment
    private fun updateToolbar(fragment: Fragment) {
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