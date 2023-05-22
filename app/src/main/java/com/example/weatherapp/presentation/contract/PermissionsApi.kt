package com.example.weatherapp.presentation.contract

typealias PermissionCallback = () -> Unit

/**
 * API allowing easy access to permissions from any screen
 */
interface PermissionsApi {

    /**
     * @return true if all [permissions] are granted
     */
    fun hasPermissions(permissions: Array<String>): Boolean

    /**
     * Request [permissions]
     * [allPermissionsGrantedCallback] starts only if all permissions from [permissions] are granted
     */
    fun requestPermission(
        permissions: Array<String>,
        allPermissionsGrantedCallback: PermissionCallback?
    )
}