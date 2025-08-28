package com.example.fooddeals.data.network

import com.example.fooddeals.MockResponseFileReader
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.Strictness
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class RestaurantsApiServiceTest
{
    private lateinit var mockWebServer: MockWebServer
    lateinit var apiService: RestaurantsApiService
    lateinit var gson: Gson

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        gson = GsonBuilder().setStrictness(Strictness.LENIENT).create()
        mockWebServer = MockWebServer()
        mockWebServer.start()
        apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(RestaurantsApiService::class.java)
    }

    // test reading json file from the resource folder
    @Test
    fun `read sample success json file`(){
        val reader = MockResponseFileReader("challengedata.json")
        assertNotNull(reader.content)
    }


    // test valid api response from the server
    @Test
    fun `get restaurant api test`() = runTest {
        val mockResponse = MockResponse()
        mockWebServer.enqueue(mockResponse.setBody(MockResponseFileReader("challengedata.json").content))
        val response = apiService.getRestaurants()
        val request = mockWebServer.takeRequest()
        assertEquals("/challengedata.json",request.path)
        assertEquals(true, response.body()?.restaurants?.isEmpty() == false)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }
}