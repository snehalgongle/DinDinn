package com.snake.dindinn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.airbnb.mvrx.*
import com.snake.dindinn.model.DishModel
import kotlinx.android.synthetic.main.fragment_cart.*

class CartFragment  : BaseMvRxFragment() {

    private lateinit var cartAdapter: CartAdapter

    // add ViewModel declaration here
    private val mvRxViewModel: MvRxViewModel by activityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cartAdapter = CartAdapter(object : CartAdapter.ClickListener {
            override fun addToCart(movieId: Long) {
                // call ViewModel to add movie to watchlist
                mvRxViewModel.addDishList(movieId)
            }

            override fun removeFromCart(movieId: Long) {
                // call ViewModel to remove movie from watchlist
                mvRxViewModel.removeDishList(movieId)
            }
        })
        cart_recyclerview.addItemDecoration(HalfCustomDividerItemDecoration())
        cart_recyclerview.adapter = cartAdapter
        mvRxViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })
    }


    override fun invalidate() {
        withState(mvRxViewModel) { state ->
            when (state.dish) {
                is Success -> {
                    val cartList = state.dish.invoke().filter { it.isSelectedList }
                    cart_button.setText("Checkout Total ₹ "+grandTotal(cartList))
                    showCartList(cartList)
                }
                is Fail -> {
                    Toast.makeText(requireContext(), "Failed to load cart", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showCartList(cartList: List<DishModel>) {
        if (cartList.isEmpty()) {
            cart_imageView.visibility = View.VISIBLE
            cart_button.visibility=View.GONE
            cart_recyclerview.visibility = View.GONE
        } else {
            cart_imageView.visibility = View.GONE
            cart_button.visibility=View.VISIBLE
            cart_recyclerview.visibility = View.VISIBLE

            cartAdapter.setDishes(cartList)
        }
    }

    private fun grandTotal(items: List<DishModel>): Double {
        var totalPrice = 0.0
        for (i in items.indices) {
            totalPrice += items[i].cost
        }
        return totalPrice
    }

}