package com.example.weatherapp.presentation.ui.settings_screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.weatherapp.BuildConfig
import com.example.weatherapp.R
import com.example.weatherapp.presentation.contract.sideeffects.toasts.ToastProvider
import com.example.weatherapp.presentation.state.UiState
import com.example.weatherapp.presentation.ui_utils.collectFlow
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {

    @Inject
    lateinit var toastProvider: ToastProvider

    private val viewModel: SettingsViewModel by viewModels()

    private lateinit var unitsSystemPreference: ListPreference
    private lateinit var appThemePreference: ListPreference
    private lateinit var appVersionPreference: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.fragment_settings, rootKey)

        unitsSystemPreference = findPreference(getString(R.string.units_system_preference_key))!!
        appThemePreference = findPreference(getString(R.string.app_theme_preference_key))!!

        appVersionPreference = findPreference(getString(R.string.app_version_preference_key))!!
        appVersionPreference.summary = getString(R.string.app_version, BuildConfig.VERSION_NAME)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.collectFlow(viewModel.appThemeSetting) { appThemeUiState ->
            collectSettingUiState(appThemeUiState) { appThemeSetting ->
                appThemePreference.value = appThemeSetting.ordinal.toString()
            }
        }

        viewLifecycleOwner.collectFlow(viewModel.unitsSystemSetting) { unitsSystemUiState ->
            collectSettingUiState(unitsSystemUiState) { unitsSystemSetting ->
                unitsSystemPreference.value = unitsSystemSetting.ordinal.toString()
            }
        }

        unitsSystemPreference.setOnPreferenceChangeListener { _, newValue ->
            viewModel.setAppUnitsSystemByOrdinal((newValue as String).toInt())
            true
        }

        appThemePreference.setOnPreferenceChangeListener { _, newValue ->
            viewModel.setAppThemeByOrdinal((newValue as String).toInt())
            true
        }
    }

    private fun <T> collectSettingUiState(settingUiState: UiState<T>, successBlock: (T) -> Unit) {
        when (settingUiState) {
            is UiState.Loading -> {}
            is UiState.Success -> successBlock(settingUiState.data)
            is UiState.Error -> toastProvider.showToast(R.string.default_exception_message)
        }
    }
}