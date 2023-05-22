package com.example.weatherapp.domain

open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class ConnectionException(cause: Throwable) : AppException(cause)

class BackendException(
    val code: Int,
    message: String
) : AppException(message)

class GpsException : AppException()

class PermissionException : AppException()