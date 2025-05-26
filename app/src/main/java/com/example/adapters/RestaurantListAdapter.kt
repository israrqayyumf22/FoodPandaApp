package com.example.foodpanda.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodpanda.R
import com.example.foodpanda.model.RestaurantModel

class RestaurantListAdapter(
    private var restaurantModelList: List<RestaurantModel>,
    private val clickListener: RestaurantListClickListener
) : RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder>() {

    fun updateData(newList: List<RestaurantModel>) {
        restaurantModelList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val restaurant = restaurantModelList[position]
        holder.restaurantName.text = restaurant.name
        holder.restaurantAddress.text = "Address: ${restaurant.address}"

        holder.itemView.setOnClickListener {
            clickListener.onItemClick(restaurant)
        }

        Glide.with(holder.thumbImage)
            .load(restaurant.image)
            .into(holder.thumbImage)
    }

    override fun getItemCount(): Int = restaurantModelList.size

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val restaurantName: TextView = view.findViewById(R.id.restaurantName)
        val restaurantAddress: TextView = view.findViewById(R.id.restaurantAddress)
        val restaurantHours: TextView = view.findViewById(R.id.restaurantHours)
        val thumbImage: ImageView = view.findViewById(R.id.thumbImage)
    }

    interface RestaurantListClickListener {
        fun onItemClick(restaurantModel: RestaurantModel)
    }
}