package com.example.fooddeals.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddeals.data.network.NetworkResult
import com.example.fooddeals.data.repository.RestaurantsRepository
import com.example.fooddeals.model.Restaurant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Restaurants view model- ViewModel for the Main Activity
 *
 * @property repository
 *
 */
@HiltViewModel
class RestaurantsViewModel @Inject constructor(private val repository: RestaurantsRepository) : ViewModel() {

    // class variables
    lateinit var clickedItem: Restaurant

    // variables to store different responses after the api call
    var loading: Boolean by mutableStateOf(true)
    var errorMessage: String by mutableStateOf("")
    var restaurantsList: List<Restaurant> by mutableStateOf(emptyList())

    init {
        // call the api to get data
        getRestaurantFromApi()
    }


    /* *
    * function to retrieve data from the server using coroutine and
    * storing the data based on Success and Failure
    * */
    fun getRestaurantFromApi() {
        // launch the api call within the viewmodel scope
        viewModelScope.launch{
            when (val response = repository.fetchRestaurantsFromApi()) {
                // if the response is success
                is NetworkResult.Success -> {
                    // set the loading to false
                    loading = false
                    // store the success response after sorting with the best deals
                    restaurantsList = response.data?.restaurants?.sortedByDescending { restaurant ->
                        // Find the maximum discount in the deals list for the current restaurant
                        restaurant.deals.maxOfOrNull { it.discount} ?: 0
                    }?:emptyList()
                }
                // if the response is failure
                is NetworkResult.Error -> {
                    // set the loading to false
                    loading = false
                    // store the failure message
                    errorMessage = response.message.toString()

                }
            }
        }
    }

    /**
     * Item clicked - function to be used to move to the details screen from the main list
     *
     * @param item
     */
    fun itemClicked(item: Restaurant) {
        clickedItem = item
    }


}