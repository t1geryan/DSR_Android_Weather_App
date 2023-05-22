package com.example.weatherapp.presentation.ui.settings_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSettingsBinding
import com.example.weatherapp.domain.models.AppTheme
import com.example.weatherapp.domain.models.AppUnitsSystem
import com.example.weatherapp.presentation.contract.sideeffects.toasts.ToastProvider
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui_utils.collectFlow
import com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    @Inject
    lateinit var toastProvider: ToastProvider

    private val viewModel: SettingsViewModel by viewModels()

    private val themeToggleChangeListener = OnButtonCheckedListener { _, checkedId, isChecked ->
        if (isChecked)
            viewModel.setAppTheme(getThemeSettingByToggleButtonId(checkedId))
    }
    private val unitsSystemToggleChangeListener =
        OnButtonCheckedListener { _, checkedId, isChecked ->
            if (isChecked)
                viewModel.setAppUnitsSystem(getUnitsSettingByToggleButtonId(checkedId))
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        binding.appVersionTV.text = getString(R.string.app_version, BuildConfig.VERSION_NAME)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.collectFlow(viewModel.appThemeSetting) { appThemeUiState ->
            collectSettingUiState(appThemeUiState) { appThemeSetting ->
                binding.themeToggleGroup.check(
                    getThemeToggleButtonIdBySetting(appThemeSetting)
                )
            }
        }

        viewLifecycleOwner.collectFlow(viewModel.unitsSystemSetting) { unitsSystemUiState ->
            collectSettingUiState(unitsSystemUiState) { unitsSystemSetting ->
                binding.unitsToggleGroup.check(
                    getUnitsSystemToggleButtonIdBySetting(unitsSystemSetting)
                )
            }
        }

        binding.themeToggleGroup.addOnButtonCheckedListener(themeToggleChangeListener)
        binding.unitsToggleGroup.addOnButtonCheckedListener(unitsSystemToggleChangeListener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        with(binding) {
            themeToggleGroup.removeOnButtonCheckedListener(themeToggleChangeListener)
            unitsToggleGroup.removeOnButtonCheckedListener(unitsSystemToggleChangeListener)
        }
    }

    private fun <T> collectSettingUiState(settingUiState: UiState<T>, successBlock: (T) -> Unit) =
        with(binding) {
            settingsProgressBar.visibility = View.INVISIBLE
            when (settingUiState) {
                is UiState.Loading -> settingsProgressBar.visibility = View.VISIBLE
                is UiState.Success -> successBlock(settingUiState.data)
                is UiState.Error -> toastProvider.showToast(R.string.default_exception_message)
            }
        }

    private fun getThemeToggleButtonIdBySetting(themeSetting: AppTheme) =
        when (themeSetting) {
            AppTheme.DAY_THEME -> R.id.dayThemeToggleButton
            AppTheme.NIGHT_THEME -> R.id.nightThemeToggleButton
            AppTheme.SYSTEM_THEME -> R.id.systemThemeToggleButton
        }

    private fun getUnitsSystemToggleButtonIdBySetting(unitsSystemSetting: AppUnitsSystem) =
        when (unitsSystemSetting) {
            AppUnitsSystem.METRIC_SYSTEM -> R.id.metricUnitsToggleButton
            AppUnitsSystem.IMPERIAL_SYSTEM -> R.id.imperialUnitsToggleButton
        }

    private fun getUnitsSettingByToggleButtonId(@IdRes toggleButtonId: Int) =
        when (toggleButtonId) {
            R.id.metricUnitsToggleButton -> AppUnitsSystem.METRIC_SYSTEM
            R.id.imperialUnitsToggleButton -> AppUnitsSystem.IMPERIAL_SYSTEM
            else -> throw IllegalArgumentException()
        }


    private fun getThemeSettingByToggleButtonId(@IdRes toggleButtonId: Int) =
        when (toggleButtonId) {
            R.id.dayThemeToggleButton -> AppTheme.DAY_THEME
            R.id.nightThemeToggleButton -> AppTheme.NIGHT_THEME
            R.id.systemThemeToggleButton -> AppTheme.SYSTEM_THEME
            else -> throw IllegalArgumentException()
        }
}