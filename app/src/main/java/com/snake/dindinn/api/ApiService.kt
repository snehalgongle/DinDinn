package com.snake.dindinn.api

import androidx.lifecycle.LiveData
import com.snake.dindinn.model.DishModel
import retrofit2.http.GET

interface ApiService {

    @GET("dish")
    fun getDishes(): LiveData<List<DishModel>>

}