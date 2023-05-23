package com.example.weatherapp.utils.locale

import com.example.weatherapp.utils.Constants
import java.util.*
import javax.inject.Inject

class CurrentLocaleProviderImpl @Inject constructor() : CurrentLocaleProvider {

    override fun provideIso3166Code(): String = getAndFilterCurrentLocaleTag()

    override fun provideRfc3060Code(): String {
        val currentLocaleCode = provideIso3166Code()
        val locationCode = when (currentLocaleCode) {
            Constants.Locale.ENGLISH_LOCALE_TAG -> Constants.Regions.US_RFS_CODE
            Constants.Locale.RUSSIAN_LOCALE_TAG -> Constants.Regions.RU_RFS_CODE
            else -> Constants.Regions.US_RFS_CODE
        }
        return "${currentLocaleCode}_${locationCode}"
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
        return if (localeCode in Constants.Locale.SupportedLocalesTags) {
            localeCode
        } else {
            Constants.Locale.DEFAULT_LOCALE_TAG
        }
    }
}