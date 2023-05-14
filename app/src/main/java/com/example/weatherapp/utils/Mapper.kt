package com.example.weatherapp.utils

/**
 * interface for mapping in one direction
 */
interface Mapper<T, R> {
    fun map(valueToMap: T): R
}

/**
 * interface for mapping in both directions
 */
interface BidirectionalMapper<T, R> : Mapper<T, R> {
    fun reverseMap(valueToMap: R): T
}

/**
 * interface for mapping in one direction with special parameter
 */
interface ParameterizedMapper<T, R, P> {

    fun mapWithParameter(valueToMap: T, parameter: P): R
}