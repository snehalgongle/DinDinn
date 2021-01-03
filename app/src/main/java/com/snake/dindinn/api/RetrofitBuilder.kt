package com.snake.dindinn.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitBuilder {

        const val BASE_URL: String = "https://snaketech.stoplight.io/mocks/snaketech/dindinn/4654119/"

        val retrofitBuilder: Retrofit.Builder by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
        }


        val apiService: ApiService by lazy{
            retrofitBuilder
                .build()
                .create(ApiService::class.java)
        }
}