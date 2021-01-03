package com.snake.dindinn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.snake.dindinn.model.DishModel

class CartAdapter(private val clickListener: CartAdapter.ClickListener) :
    RecyclerView.Adapter<CartAdapter.MyViewHolder>() {

    private val dish = mutableListOf<DishModel>()

    fun setDishes(dishes: List<DishModel>) {
        this.dish.clear()
        this.dish.addAll(dishes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cart_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = dish.size

    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dish = dish[position]

        Glide.with(holder.itemView)
            .load(dish.imageUrl)
            .centerCrop()
            .into(holder.posterImageView)

        holder.movieNameTextView.text = dish.name
        holder.textViewDesc.text = dish.desc
        holder.textViewCost.text = "₹ "+dish.cost.toString()
        if (dish.isSelectedList) {
//            holder.watchlistButton.setImageResource(R.drawable.ic_remove_from_watchlist)
        } else {
//            holder.watchlistButton.setImageResource(R.drawable.ic_add_to_watchlist)
        }
        holder.fabRemove.setOnClickListener {
            if (dish.isSelectedList) {
                clickListener.removeFromCart(dish.id)
            } else {
                clickListener.addToCart(dish.id)
            }
        }
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val posterImageView: ImageView = itemView.findViewById(R.id.imageView)
        val movieNameTextView: TextView = itemView.findViewById(R.id.textView_title)
        val textViewDesc: TextView = itemView.findViewById(R.id.textView_desc)
        val textViewCost: TextView = itemView.findViewById(R.id.textView_Cost)
        val fabRemove: FloatingActionButton = itemView.findViewById(R.id.fab)
    }




    interface ClickListener {

        fun addToCart(movieId: Long)

        fun removeFromCart(movieId: Long)
    }
}