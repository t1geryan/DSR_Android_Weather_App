package com.example.weatherapp.presentation.event

/**
 * A Event class which gives value only one time
 * @see Event
 */
class SingleEvent<out T>(
    private val value: T,
) : Event<T> {
    private var isHandled = false

    override fun getValue(): T? {
        if (isHandled) return null
        isHandled = true
        return value
    }

}