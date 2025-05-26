package com.example.foodpanda.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodpanda.R
import com.example.foodpanda.model.Menu

class MenuListAdapter(
    private var menuList: List<Menu>,
    private val clickListener: MenuListClickListener
) : RecyclerView.Adapter<MenuListAdapter.MyViewHolder>() {

    fun updateData(newList: List<Menu>) {
        menuList = newList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_recycler_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val menu = menuList[position]

        holder.menuName.text = menu.name
        holder.menuPrice.text = "Price: ${menu.price} PKR"

        holder.addToCartButton.setOnClickListener {
            menu.totalInCart = 1
            clickListener.onAddToCartClick(menu)
            holder.addMoreLayout.visibility = View.VISIBLE
            holder.addToCartButton.visibility = View.GONE
            holder.tvCount.text = menu.totalInCart.toString()
        }

        holder.imageMinus.setOnClickListener {
            val total = menu.totalInCart - 1
            if (total > 0) {
                menu.totalInCart = total
                clickListener.onUpdateCartClick(menu)
                holder.tvCount.text = total.toString()
            } else {
                holder.addMoreLayout.visibility = View.GONE
                holder.addToCartButton.visibility = View.VISIBLE
                menu.totalInCart = total
                clickListener.onRemoveFromCartClick(menu)
            }
        }

        holder.imageAddOne.setOnClickListener {
            val total = menu.totalInCart + 1
            if (total <= 10) {
                menu.totalInCart = total
                clickListener.onUpdateCartClick(menu)
                holder.tvCount.text = total.toString()
            }
        }

        Glide.with(holder.thumbImage)
            .load(menu.url)
            .into(holder.thumbImage)
    }

    override fun getItemCount(): Int = menuList.size

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val menuName: TextView = view.findViewById(R.id.menuName)
        val menuPrice: TextView = view.findViewById(R.id.menuPrice)
        val addToCartButton: TextView = view.findViewById(R.id.addToCartButton)
        val thumbImage: ImageView = view.findViewById(R.id.thumbImage)
        val imageMinus: ImageView = view.findViewById(R.id.imageMinus)
        val imageAddOne: ImageView = view.findViewById(R.id.imageAddOne)
        val tvCount: TextView = view.findViewById(R.id.tvCount)
        val addMoreLayout: LinearLayout = view.findViewById(R.id.addMoreLayout)
    }

    interface MenuListClickListener {
        fun onAddToCartClick(menu: Menu)
        fun onUpdateCartClick(menu: Menu)
        fun onRemoveFromCartClick(menu: Menu)
    }
}