package com.example.weatherapp.utils.extensions

import com.example.weatherapp.domain.AppException
import com.example.weatherapp.domain.BackendException
import com.example.weatherapp.domain.ConnectionException
import com.example.weatherapp.domain.ParseBackendResponseException
import com.google.gson.JsonParseException
import okio.IOException
import org.json.JSONException
import retrofit2.HttpException

/**
 * Map network and parse exceptions into in-app exceptions.
 * @throws BackendException
 * @throws ParseBackendResponseException
 * @throws ConnectionException
 * @throws AppException
 */
suspend fun <T> wrapRetrofitExceptions(apiCallBlock: suspend () -> T): T {
    return try {
        apiCallBlock()
        // retrofit
    } catch (e: HttpException) {
        throw BackendException(e.code(), e)
    } catch (e: IOException) {
        throw ConnectionException(e)
        // gson
    } catch (e: JsonParseException) {
        throw ParseBackendResponseException(e)
    } catch (e: JSONException) {
        throw ParseBackendResponseException(e)
        // other
    } catch (e: AppException) {
        throw e
    } catch (e: Exception) {
        throw AppException(e)
    }
}