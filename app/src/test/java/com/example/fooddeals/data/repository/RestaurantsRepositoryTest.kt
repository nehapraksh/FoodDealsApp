package com.example.fooddeals.data.repository

import com.example.fooddeals.MockResponseFileReader
import com.example.fooddeals.data.datasource.RestaurantsDataSource
import com.example.fooddeals.data.network.BaseApiResponse
import com.example.fooddeals.data.network.NetworkResult
import com.example.fooddeals.data.network.RestaurantsApiService
import com.example.fooddeals.model.RestaurantResponse
import com.google.gson.GsonBuilder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

/**
 * Restaurants repository test
 *
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class RestaurantsRepositoryTest: BaseApiResponse() {

    lateinit var restaurantsRepository: RestaurantsRepository

    @Mock
    lateinit var apiService: RestaurantsApiService

    @Mock
    lateinit var restaurantsDataSource: RestaurantsDataSource

    private var emptyRestaurantList: RestaurantResponse = RestaurantResponse(emptyList())
    private lateinit var mockRestaurantList: RestaurantResponse

    /**
     * Setup
     *
     */
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        restaurantsDataSource = RestaurantsDataSource(apiService)
        restaurantsRepository = RestaurantsRepository(restaurantsDataSource)
        mockData()
    }


    // test success response with valid restaurants list from api call
    @Test
    fun test_GetRestaurant_List() = runTest {
        Mockito.`when`(apiService.getRestaurants()).thenReturn(
            Response.success(200,
                mockRestaurantList
            ))
        val response = restaurantsRepository.fetchRestaurantsFromApi()
        assertEquals(true,  response is NetworkResult.Success)
        assertEquals(200,  Response.success(response).code())
        assertEquals(6,  response.data!!.restaurants.size)
    }

    /**
     * Test_get assets_empty list - test success response with empty list from api call
     *
     */
    @Test
    fun test_GetRestaurant_EmptyList() = runTest {
        Mockito.`when`(apiService.getRestaurants()).thenReturn(
            Response.success(200,
                emptyRestaurantList
            ))
        val response = restaurantsRepository.fetchRestaurantsFromApi()
        assertEquals(true,  response is NetworkResult.Success)
        assertEquals(200,  Response.success(response).code())
        assertEquals(0,  response.data!!.restaurants.size)
    }

    /**
     * Test_get assets_error - test failure response from api call
     *
     */
    @Test
    fun test_GetRestaurant_Error() = runTest {
        Mockito.`when`(apiService.getRestaurants()).thenReturn(Response.error(403,"Forbidden".toResponseBody()))
        val response = restaurantsRepository.fetchRestaurantsFromApi()
        assertEquals(true,  response is NetworkResult.Error)
    }


    private fun mockData() {
        val gson = GsonBuilder().create()
        val restaurants: RestaurantResponse = gson.fromJson(
            MockResponseFileReader("challengedata.json").content,
            RestaurantResponse::class.java
        )
        mockRestaurantList = restaurants
    }
}