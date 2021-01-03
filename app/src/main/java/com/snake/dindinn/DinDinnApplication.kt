package com.snake.dindinn

import android.app.Application

class DinDinnApplication : Application() {
    val dishRepository by lazy {
        DishRepository()
    }
}