package com.example.foodpanda.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodpanda.R
import com.example.foodpanda.model.Menu

class PlaceYourOrderAdapter(
    private var menuList: List<Menu>
) : RecyclerView.Adapter<PlaceYourOrderAdapter.MyViewHolder>() {

    fun updateData(newList: List<Menu>) {
        menuList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.place_order_recycler_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val menu = menuList[position]

        holder.menuName.text = menu.name
        holder.menuPrice.text = "Price: PKR %.2f".format(menu.price * menu.totalInCart)
        holder.menuQty.text = "Qty: ${menu.totalInCart}"

        Glide.with(holder.thumbImage)
            .load(menu.url)
            .into(holder.thumbImage)
    }

    override fun getItemCount(): Int = menuList.size

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val menuName: TextView = view.findViewById(R.id.menuName)
        val menuPrice: TextView = view.findViewById(R.id.menuPrice)
        val menuQty: TextView = view.findViewById(R.id.menuQty)
        val thumbImage: ImageView = view.findViewById(R.id.thumbImage)
    }
}
