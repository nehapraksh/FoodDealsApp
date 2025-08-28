package com.example.fooddeals.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.fooddeals.MockResponseFileReader
import com.example.fooddeals.data.network.NetworkResult
import com.example.fooddeals.data.repository.RestaurantsRepository
import com.example.fooddeals.model.RestaurantResponse
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Restaurant view model test
 *
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class RestaurantsViewModelTest
{
    // class variables declaration
    // test dispatcher to test the coroutine
    private val testDispatcher = StandardTestDispatcher()

    // view model
    private lateinit var viewModel: RestaurantsViewModel

    // mock the repository
    @Mock
    private lateinit var mockRepository: RestaurantsRepository

//    // empty restaurant list for testing empty response
    private val  emptyRestaurantList: RestaurantResponse = RestaurantResponse(emptyList())

    // restaurant list for testing success response restaurant list
    private lateinit var mockRestaurantList: RestaurantResponse

    // set the rule
    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * Setup
     *
     */
    @Before
    fun setup() {
        // initialize the mockito component
        MockitoAnnotations.openMocks(this)
        // set the testDispatcher
        Dispatchers.setMain(testDispatcher)
        // initialise the view model
        viewModel = RestaurantsViewModel(mockRepository)
        // get the mock data from the json file
        mockData()
    }

    /**
     * Tear down
     *
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    // test the success response with correct restaurant list from the api using the coroutine in view model
    @Test
    fun test_FetchDataFromApi_expectedSuccess() = runTest {
        Mockito.`when`(mockRepository.fetchRestaurantsFromApi())
            .thenReturn(NetworkResult.Success(mockRestaurantList))
        viewModel.getRestaurantFromApi()
        // Await the change
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.restaurantsList
        assertEquals(viewModel.errorMessage, "")
        assertTrue(!viewModel.loading)
        assertEquals(result, mockRestaurantList.restaurants)
        assertEquals(20, result.size)
        //assert(result[0].color == "Red")
        //assert(result[1].year == 2020)
    }

    // test the success response with empty restaurant list from the api using the coroutine in view model
    @Test
    fun `retrieve restaurants with ViewModel and Repository returns empty data`() = runTest {
        Mockito.`when`(mockRepository.fetchRestaurantsFromApi())
            .thenReturn(NetworkResult.Success(emptyRestaurantList))
        viewModel.getRestaurantFromApi()
        // Await the change
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.restaurantsList
        assertEquals(viewModel.errorMessage, "")
        assertTrue(!viewModel.loading)
        assertTrue(result.isEmpty())
        assertEquals(0, result.size)
    }

    // test the error response from the api using the coroutine in view model
    @Test
    fun test_FetchDataFromApi_expectedError() = runTest {
        Mockito.`when`(mockRepository.fetchRestaurantsFromApi())
            .thenReturn(NetworkResult.Error("Api call failed"))
        viewModel.getRestaurantFromApi()
        // Await the change
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.errorMessage
        assertNotEquals(viewModel.errorMessage, "")
        assertTrue(!viewModel.loading)
        assertTrue(viewModel.restaurantsList.isEmpty())
        assertEquals("Api call failed", result)

    }

    @Test
    fun itemClicked() {
        val restaurant = mockRestaurantList.restaurants[0]

        viewModel.itemClicked(restaurant)

        assert(viewModel.clickedItem == restaurant)
    }


    // read data from the json file and set to the mock list
    private fun mockData() {
        val gson = GsonBuilder().create()

        val restaurants: RestaurantResponse = gson.fromJson(
            MockResponseFileReader("challengedata.json").content,
            RestaurantResponse::class.java
        )
        mockRestaurantList = restaurants
    }
}