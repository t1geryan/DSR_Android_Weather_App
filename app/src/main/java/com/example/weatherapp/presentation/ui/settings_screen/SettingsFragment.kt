package com.example.weatherapp.presentation.ui.settings_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentSettingsBinding
import com.example.weatherapp.domain.models.AppTheme
import com.example.weatherapp.domain.models.AppUnitsSystem
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui_utils.collectWhenStarted
import com.example.weatherapp.presentation.ui_utils.sideEffectsProvider
import com.google.android.material.button.MaterialButtonToggleGroup.OnButtonCheckedListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

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

        collectWhenStarted {
            viewModel.appThemeSetting.collect { appThemeUiState ->
                collectSettingUiState(appThemeUiState) { appThemeSetting ->
                    AppCompatDelegate.setDefaultNightMode(
                        getNightModeByThemeSettings(
                            appThemeSetting
                        )
                    )
                    binding.themeToggleGroup.check(
                        getThemeToggleButtonIdBySetting(appThemeSetting)
                    )
                }
            }
        }
        collectWhenStarted {
            viewModel.unitsSystemSetting.collect { unitsSystemUiState ->
                collectSettingUiState(unitsSystemUiState) { unitsSystemSetting ->
                    binding.unitsToggleGroup.check(
                        getUnitsSystemToggleButtonIdBySetting(unitsSystemSetting)
                    )
                }
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
                is UiState.Error -> sideEffectsProvider().showToast(R.string.default_exception_message)
            }
        }

    private fun getThemeToggleButtonIdBySetting(themeSetting: AppTheme) =
        when (themeSetting.themeKey) {
            AppTheme.DAY_THEME_KEY -> R.id.dayThemeToggleButton
            AppTheme.NIGHT_THEME_KEY -> R.id.nightThemeToggleButton
            AppTheme.SYSTEM_THEME_KEY -> R.id.systemThemeToggleButton
            else -> throw IllegalArgumentException()
        }

    private fun getUnitsSystemToggleButtonIdBySetting(unitsSystemSetting: AppUnitsSystem) =
        when (unitsSystemSetting.systemKey) {
            AppUnitsSystem.METRIC_SYSTEM_KEY -> R.id.metricUnitsToggleButton
            AppUnitsSystem.IMPERIAL_SYSTEM_KEY -> R.id.imperialUnitsToggleButton
            else -> throw IllegalArgumentException()
        }

    private fun getUnitsSettingByToggleButtonId(@IdRes toggleButtonId: Int) =
        AppUnitsSystem(
            when (toggleButtonId) {
                R.id.metricUnitsToggleButton -> AppUnitsSystem.METRIC_SYSTEM_KEY
                R.id.imperialUnitsToggleButton -> AppUnitsSystem.IMPERIAL_SYSTEM_KEY
                else -> throw IllegalArgumentException()
            }
        )

    private fun getThemeSettingByToggleButtonId(@IdRes toggleButtonId: Int) =
        AppTheme(
            when (toggleButtonId) {
                R.id.dayThemeToggleButton -> AppTheme.DAY_THEME_KEY
                R.id.nightThemeToggleButton -> AppTheme.NIGHT_THEME_KEY
                R.id.systemThemeToggleButton -> AppTheme.SYSTEM_THEME_KEY
                else -> throw IllegalArgumentException()
            }
        )

    private fun getNightModeByThemeSettings(themeSetting: AppTheme) =
        when (themeSetting.themeKey) {
            AppTheme.DAY_THEME_KEY -> AppCompatDelegate.MODE_NIGHT_NO
            AppTheme.NIGHT_THEME_KEY -> AppCompatDelegate.MODE_NIGHT_YES
            AppTheme.SYSTEM_THEME_KEY -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            else -> throw IllegalArgumentException()
        }
}