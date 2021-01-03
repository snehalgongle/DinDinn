package com.snake.dindinn

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.Toast
import androidx.lifecycle.Observer
import com.airbnb.mvrx.*
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : BaseMvRxFragment() {

    private lateinit var mainAdapter: MainAdapter

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
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainAdapter = MainAdapter(requireActivity(), object : MainAdapter.ClickListener {
            override fun addToCart(dishId: Long) {
                // call ViewModel to add movie to watchlist
                mvRxViewModel.addDishList(dishId)
            }

            override fun removeFromCart(dishId: Long) {
                // call ViewModel to remove movie from watchlist
                mvRxViewModel.removeDishList(dishId)
            }

            override fun changeCartNumber(count: Int) {
                fab.viewTreeObserver.addOnGlobalLayoutListener(object :
                    ViewTreeObserver.OnGlobalLayoutListener {
                    @SuppressLint("RestrictedApi")
                    override fun onGlobalLayout() {
                        val badgeDrawable = BadgeDrawable.create(requireActivity())
                        badgeDrawable.number = count
                        //Important to change the position of the Badge
                        badgeDrawable.horizontalOffset = 40
                        badgeDrawable.verticalOffset = 40
                        if (fab != null) {
                            BadgeUtils.attachBadgeDrawable(badgeDrawable, fab, null)
                            fab.viewTreeObserver.removeOnGlobalLayoutListener(this)
                        }
                    }
                })
            }

        })
        recyclerview.adapter = mainAdapter
        mvRxViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })

        fab.setOnClickListener {
            requireFragmentManager().beginTransaction()
                .replace(R.id.container, CartFragment())
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun invalidate() {
        withState(mvRxViewModel) { state ->
            when (state.dish) {
                is Loading -> {
                    progress_bar.visibility = View.VISIBLE
                    recyclerview.visibility = View.GONE
                }
                is Success -> {
                    progress_bar.visibility = View.GONE
                    recyclerview.visibility = View.VISIBLE
                    mainAdapter.setDishes(state.dish.invoke())
                }
                is Fail -> {
                    Toast.makeText(
                        requireContext(),
                        "Failed to load all movies",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}