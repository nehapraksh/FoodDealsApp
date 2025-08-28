package com.example.fooddeals.data.network

import retrofit2.Response


/**
 * Base api response - Generic class to make all the api calls and setting the response in the callback as per the result
 *
 * @constructor Create empty Base api response
 */
abstract class BaseApiResponse {
    // needs to be called from another suspend function or from coroutines
    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): NetworkResult<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return NetworkResult.Success(body)
                }
            }
            return error("${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }
    private fun <T> error(errorMessage: String): NetworkResult<T> =
        NetworkResult.Error("Api call failed: $errorMessage")
}