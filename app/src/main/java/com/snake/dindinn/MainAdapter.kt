package com.snake.dindinn

import android.annotation.SuppressLint
import android.content.Context
import android.view.Gravity.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.snake.dindinn.model.DishModel

class MainAdapter(private val context:Context, private val clickListener: ClickListener) :
    RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    private val dishList = mutableListOf<DishModel>()

    fun setDishes(dishes: List<DishModel>) {
        this.dishList.clear()
        this.dishList.addAll(dishes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.main_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount() = dishList.size

    override fun getItemViewType(position: Int): Int {
        return position
    }

    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dish = dishList[position]

        Glide.with(holder.itemView)
            .load(dish.imageUrl)
            .centerCrop()
            .into(holder.imageView)

        holder.textViewTitle.text = dish.name
        holder.textViewDesc.text = dish.desc
        holder.textViewCost.text = "₹ "+dish.cost.toString()
        if (dish.isSelectedList) {
            holder.addButton.setText("")
            holder.addButton.setIconResource(R.drawable.ic_baseline_cancel_24)
            holder.addButton.iconGravity = LEFT
//            holder.addButton.background=context.resources.getDrawable(R.drawable.ic_baseline_close_24)
            clickListener.changeCartNumber(dishList.filter { it.isSelectedList }.size)
        } else {
            holder.addButton.setText(R.string.add)
            holder.addButton.icon=null
//            holder.addButton.isCheckable = true
            clickListener.changeCartNumber(dishList.filter { it.isSelectedList }.size)
        }
        holder.addButton.setOnClickListener {
            if (dish.isSelectedList) {
                holder.addButton.setText("")
                holder.addButton.setIconResource(R.drawable.ic_baseline_cancel_24)
                holder.addButton.iconGravity = LEFT
                clickListener.removeFromCart(dish.id)
                clickListener.changeCartNumber(dishList.filter { it.isSelectedList }.size)
            } else {
                holder.addButton.setText(R.string.add)
                holder.addButton.icon=null
                clickListener.addToCart(dish.id)
                clickListener.changeCartNumber(dishList.filter { it.isSelectedList }.size)
            }
        }
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textViewTitle: TextView = itemView.findViewById(R.id.textView_title)
        val textViewDesc: TextView = itemView.findViewById(R.id.textView_desc)
        val addButton: MaterialButton = itemView.findViewById(R.id.button)
        val textViewCost: TextView = itemView.findViewById(R.id.textView_Cost)
    }


    interface ClickListener {

        fun addToCart(dishId: Long)

        fun removeFromCart(dishId: Long)

        fun changeCartNumber(count: Int)
    }
}