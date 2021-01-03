package com.snake.dindinn.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getDishes() = apiService.getDishes()
}