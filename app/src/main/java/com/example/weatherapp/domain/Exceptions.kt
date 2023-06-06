package com.example.weatherapp.domain

open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String?) : super(message)
    constructor(cause: Throwable?) : super(cause)
}

class ConnectionException(cause: Throwable?) : AppException(cause)

open class BackendException(
    val code: Int?,
    cause: Throwable?
) : AppException(cause)

class ParseBackendResponseException(
    cause: Throwable?
) : BackendException(null, cause)

class GpsException : AppException()

class PermissionException : AppException()

class GeocoderMissingException : AppException()