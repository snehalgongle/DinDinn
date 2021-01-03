package com.snake.dindinn

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MvRxState
import com.airbnb.mvrx.Uninitialized
import com.snake.dindinn.model.DishModel

data class ListState(
    val dish: Async<List<DishModel>> = Uninitialized
) : MvRxState