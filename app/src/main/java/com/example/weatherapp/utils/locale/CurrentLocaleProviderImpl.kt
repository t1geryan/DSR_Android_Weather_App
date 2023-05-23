package com.example.weatherapp.utils.locale

import android.util.Log
import com.example.weatherapp.utils.Constants
import java.util.*
import javax.inject.Inject

class CurrentLocaleProviderImpl @Inject constructor() : CurrentLocaleProvider {

    override fun provideIso3166Code(): String = getAndFilterCurrentLocaleTag().also {
        Log.d("LOCALE", "ISO: $it")
    }

    override fun provideRfc3060Code(): String {
        val currentLocaleCode = provideIso3166Code()
        val locationCode = Constants.Locale.SupportedLocaleAndRegionCodes[currentLocaleCode]
        return "${currentLocaleCode}_${locationCode}".also {
            Log.d("LOCALE", "RFC: $it")
        }
    }

    private fun getAndFilterCurrentLocaleTag(): String = filterLocaleCode(getCurrentLocaleCode())

    /**
     * Gets current locale tag even among the unsupported in Iso3116Code
     */
    private fun getCurrentLocaleCode(): String =
        Locale.getDefault().language

    /**
     * If locale is unsupported map it's tag to [Constants.Locale.DEFAULT_LOCALE_TAG]
     */
    private fun filterLocaleCode(localeCode: String): String {
        return if (localeCode in Constants.Locale.SupportedLocaleAndRegionCodes.keys) {
            localeCode
        } else {
            Constants.Locale.DEFAULT_LOCALE_TAG
        }
    }
}