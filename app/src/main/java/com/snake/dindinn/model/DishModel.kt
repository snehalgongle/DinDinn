package com.snake.dindinn.model

import com.google.gson.annotations.SerializedName

data class DishModel(
    @SerializedName("id")
    val id: Long,
    @SerializedName("dish_name")
    val name: String,
    @SerializedName("dish_desc")
    val desc: String,
    @SerializedName("dish_image")
    val imageUrl: String,
    @SerializedName("dish_cost")
    val cost: Double,
    val isSelectedList: Boolean = false
)