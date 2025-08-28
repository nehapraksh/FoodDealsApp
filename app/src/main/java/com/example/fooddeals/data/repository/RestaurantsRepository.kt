package com.example.fooddeals.data.repository


import com.example.fooddeals.data.datasource.RestaurantsDataSource
import com.example.fooddeals.data.network.BaseApiResponse
import com.example.fooddeals.data.network.NetworkResult
import com.example.fooddeals.model.RestaurantResponse
import javax.inject.Inject

/**
 * Restaurants repository
 *
 * @property restaurantsDataSource
 * @constructor Create empty Restaurant repository
 */
class RestaurantsRepository @Inject constructor(
    private val restaurantsDataSource: RestaurantsDataSource
) : BaseApiResponse() {

    /*
    * Method to get the data from api
    * It is to be called by coroutines , so declared as suspended
    * */
    suspend fun fetchRestaurantsFromApi(): NetworkResult<RestaurantResponse>
    {
        return safeApiCall { restaurantsDataSource.getRestaurants() }
    }
}