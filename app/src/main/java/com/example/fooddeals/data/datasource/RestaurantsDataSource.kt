package com.example.fooddeals.data.datasource

import com.example.fooddeals.data.network.RestaurantsApiService
import javax.inject.Inject

/**
 * Restaurants data source
 *
 * @property restaurantsApi
 * @constructor Create empty Restaurants data source
 */
class RestaurantsDataSource @Inject constructor(private val restaurantsApi: RestaurantsApiService) {
    suspend fun getRestaurants() = restaurantsApi.getRestaurants()
}