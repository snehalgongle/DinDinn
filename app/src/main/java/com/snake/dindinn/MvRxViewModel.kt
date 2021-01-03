package com.snake.dindinn

import androidx.lifecycle.MutableLiveData
import com.airbnb.mvrx.*

//abstract class MvRxViewModel<S : MvRxState> (initialState :S): BaseMvRxViewModel<S>(initialState,debugMode = BuildConfig.DEBUG)

class MvRxViewModel(
    initialState: ListState,
    private val dishRepository: DishRepository
) : BaseMvRxViewModel<ListState>(initialState, debugMode = true) {

    val errorMessage = MutableLiveData<String>()

    init {
        setState {
            copy(dish = Loading())
        }
        dishRepository.getWatchlistedMovies()
            .execute {
                copy(dish = it)
            }

//        apiHelper.getDishes()
    }

    fun addDishList(movieId: Long) {
        withState { state ->
            if (state.dish is Success) {
                val index = state.dish.invoke().indexOfFirst {
                    it.id == movieId
                }
                dishRepository.addDishList(movieId)
                    .execute {
                        if (it is Success) {
                            copy(
                                dish = Success(
                                    state.dish.invoke().toMutableList().apply {
                                        set(index, it.invoke())
                                    }
                                )
                            )
                        } else if (it is Fail){
                            errorMessage.postValue("Failed to add movie to watchlist")
                            copy()
                        } else {
                            copy()
                        }
                    }
            }
        }
    }

    fun removeDishList(movieId: Long) {
        withState { state ->
            if (state.dish is Success) {
                val index = state.dish.invoke().indexOfFirst {
                    it.id == movieId
                }
                dishRepository.removeDishList(movieId)
                    .execute {
                        if (it is Success) {
                            copy(
                                dish = Success(
                                    state.dish.invoke().toMutableList().apply {
                                        set(index, it.invoke())
                                    }
                                )
                            )
                        } else if (it is Fail){
                            errorMessage.postValue("Failed to remove movie from watchlist")
                            copy()
                        } else {
                            copy()
                        }
                    }
            }
        }
    }

    companion object : MvRxViewModelFactory<MvRxViewModel, ListState> {
        override fun create(viewModelContext: ViewModelContext,
                            state: ListState): MvRxViewModel? {
            val dishRepository = viewModelContext.app<DinDinnApplication>().dishRepository
            return MvRxViewModel(state, dishRepository)
        }
    }
}