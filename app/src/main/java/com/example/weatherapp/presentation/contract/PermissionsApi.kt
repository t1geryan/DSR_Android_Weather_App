package com.example.weatherapp.presentation.contract

typealias PermissionCallback = () -> Unit

/**
 * API allowing easy access to permissions from any screen
 *
 * [requestPermission] can also be used to check for permission by sending the required task as a callback
 * [hasPermission] only checks single permission
 */
interface PermissionsApi {

    fun hasPermission(permission: String): Boolean

    /**
     * [allPermissionsGrantedCallback] starts only if all permissions from [permissions] are granted
     */
    fun requestPermission(
        permissions: Array<String>,
        allPermissionsGrantedCallback: PermissionCallback?
    )
}