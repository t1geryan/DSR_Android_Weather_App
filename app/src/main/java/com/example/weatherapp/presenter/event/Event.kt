package com.example.weatherapp.presenter.event

/**
 * Interface for creating events from ViewModel to UI-layer classes
 *
 * When getValue() returns value it means that event should be started
 */
interface Event<out T> {

    fun getValue(): T?
}