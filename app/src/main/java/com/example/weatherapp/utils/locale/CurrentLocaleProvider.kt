package com.example.weatherapp.utils.locale

/**
 * Interface for getting current locale codes among supported ones
 */
interface CurrentLocaleProvider {

    /**
     * Provides Current Locale Code in ISO-3166 format
     *
     * For example: ru, en
     */
    fun provideIso3166Code(): String

    /**
     * Provides Current Locale Code in RFC-3066 format
     *
     * For example: ru_RU, en_US
     */
    fun provideRfc3060Code(): String
}