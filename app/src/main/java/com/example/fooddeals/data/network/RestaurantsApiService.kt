package com.example.fooddeals.data.network

import com.example.fooddeals.model.RestaurantResponse
import retrofit2.Response
import retrofit2.http.GET

/**
 * Restaurant api service
 *
 * @constructor Create empty Restaurant api service
 */
interface RestaurantsApiService {

/**
 * Get restaurants - get the restaurant list from the api
 *
 * @return
 */
@GET("challengedata.json")
suspend fun getRestaurants(): Response<RestaurantResponse>

}