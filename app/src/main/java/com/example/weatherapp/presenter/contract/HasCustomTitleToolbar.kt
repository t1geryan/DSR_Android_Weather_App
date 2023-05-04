package com.example.weatherapp.presenter.contract

/**
 * if a class (e.g. Fragment) implements this interface,
 * it means that it has a special title for the toolbar that needs to be displayed
 */
interface HasCustomTitleToolbar {

    fun getTitle(): String
}