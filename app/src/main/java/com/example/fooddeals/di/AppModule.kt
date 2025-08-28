package com.example.fooddeals.di

import com.example.fooddeals.data.network.RestaurantsApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
/**
 * AppModule - Hilt module to inject the classes needed for the building the Retrofit builder and
 * providing the client to the network classes
 *
 * @constructor Create empty AppModule
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule
{
    // sets the HttpClient component for Retrofit Builder
    @Singleton
    @Provides
    fun provideHttpClient(): OkHttpClient
    {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    // creates the Gson Converter factory
    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    // provides the retrofit builder
    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit
    {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    // provides the Restaurant API service class
    @Singleton
    @Provides
    fun provideRestaurantsService(retrofit: Retrofit): RestaurantsApiService =
        retrofit.create(RestaurantsApiService::class.java)
}

const val BASE_URL = "https://eccdn.com.au/misc/"