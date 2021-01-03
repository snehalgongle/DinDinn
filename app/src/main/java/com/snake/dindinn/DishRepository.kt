package com.snake.dindinn

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.snake.dindinn.api.ApiHelper
import com.snake.dindinn.api.ApiService
import com.snake.dindinn.api.RetrofitBuilder
import com.snake.dindinn.model.DishModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call

class DishRepository {

    private val dish = mutableListOf<DishModel>()

    fun getWatchlistedMovies() = Observable.fromCallable<List<DishModel>> {
//        Thread.sleep(3000)
        val apiService= RetrofitBuilder.apiService
        GlobalScope.launch(Dispatchers.IO) {
            delay(2000)
//            apiService.getDishes().apply {  }
//            Log.d(this.javaClass.simpleName, "getWatchlistedMovies: " + apiService.getDishes())
        }

        dish.addAll(
            listOf(
                DishModel(
                    1211,
                    "Chicken-Biriyani",
                    "Flavourful basmati rice interspersed with succulent pieces of spicy chicken make biriyani a must-have for any special occasion.",
                    "https://vaya.in/recipes/wp-content/uploads/2018/03/Ambur-Chicken-Biriyani.jpg",
                    240.00,
                    false
                ),
                DishModel(
                    1235,
                    "Pizza",
                    "Pizza is one of the most celebrated Italian dish. With time, pizza has been adapted in several variations, and one such delicious tailoring of this cuisine is the pear and Gorgonzola cheese pizza.",
                    "https://vaya.in/recipes/wp-content/uploads/2019/02/Pear-and-Gorgonzola-Cheese-Pizza.jpg",
                    320.00,
                    false
                ),
                DishModel(
                    1236,
                    "Veggie Burger",
                    "Burger Recipe with mix veggie patties, spiced mayo dressing and cucumber, tomato, onion slices. Veggie burgers are an all time favorite.",
                    "https://vaya.in/recipes/wp-content/uploads/2019/01/Veggie-Burger.jpg",
                    180.00,
                    false
                ),
                DishModel(
                    1237,
                    "Dhokla",
                    "Dhokla is one vegetarian dish that can be eaten for breakfast, can be included as part of a main course menu, and can make a healthy snack too.",
                    "https://vaya.in/recipes/wp-content/uploads/2020/03/Dhokla-recipe.jpg",
                    130.00,
                    false
                ),
                DishModel(
                    1238,
                    "Chicken Curry",
                    "This spicy chicken curry really lives up to its name. It has a true kick to it, thanks to the heavy reliance on chilis and spicy powder.",
                    "https://vaya.in/recipes/wp-content/uploads/2019/02/Spicy-Malvani-Chicken-Curry.jpg",
                    275.00,
                    false
                ),
                DishModel(
                    1239,
                    "Waffles",
                    "Waffles probably the most delicious dish in the world, waffles are one of the best choice when it comes to breakfast",
                    "https://vaya.in/recipes/wp-content/uploads/2018/05/Eggo-Waffles.jpg",
                    200.00,
                    false
                ),
                DishModel(
                    1210,
                    "Hakka Noodles",
                    "Hakka noodles is an enticing, delicious, aromatic, and flavorful dish.  It is based on the Chinese noodles to which are added crispy stir-fried with vegetables.",
                    "https://vaya.in/recipes/wp-content/uploads/2019/01/Chicken-Hakka-Noodles.jpg",
                    210.00,
                    false
                ),
                DishModel(
                    1211,
                    "Pancakes",
                    "Pancakes are one of the rare recipes you may want to include in your regular cooking menu but they are one of the most delicious one.",
                    "https://vaya.in/recipes/wp-content/uploads/2018/03/Blueberry-Pancakes.jpg",
                    220.00,
                    false
                ),
                DishModel(
                    1212,
                    "Paratha",
                    "Who doesn’t like spicy meals? I mean they are hot and delicious. Paratha's are one such delicious dish.",
                    "https://vaya.in/recipes/wp-content/uploads/2018/03/Aloo-Paratha.jpg",
                    110.00,
                    false
                ),
                DishModel(
                    1213,
                    "Egg Rolls",
                    "Egg roll can be a perfect snack or side dish.",
                    "https://vaya.in/recipes/wp-content/uploads/2018/03/Ambur-Chicken-Biriyani.jpg",
                    120.00,
                    false
                )
            )
        )
        dish

    }.subscribeOn(Schedulers.io())

    // add method to watchlist a movie
    fun addDishList(dishId: Long): Observable<DishModel> {
        return Observable.fromCallable {
            val dish = dish.first { dish -> dish.id == dishId }
            dish.copy(isSelectedList = true)
        }
    }

    // add method to remove a movie from watchlist
    fun removeDishList(dishId: Long): Observable<DishModel> {
        return Observable.fromCallable {
            val dish = dish.first { dish -> dish.id == dishId }
            dish.copy(isSelectedList = false)
        }
    }

}