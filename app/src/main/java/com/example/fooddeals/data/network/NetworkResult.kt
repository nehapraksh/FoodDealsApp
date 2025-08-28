package com.example.fooddeals.data.network

/**
 * Network result - Sealed class acting as a network response callback to handle the API responses - Success & Error/Failure
 *
 * @param T
 * @property data
 * @property message
 * @constructor Create empty Network result
 */
sealed class NetworkResult<T>(val data:T? =null,
                              val message:String? = null) {
    // to handle the success result
    class Success<T>(data: T): NetworkResult<T>(data)

    // to handle the error result
    class Error<T>(message: String, data: T?=null): NetworkResult<T>(data, message)

}