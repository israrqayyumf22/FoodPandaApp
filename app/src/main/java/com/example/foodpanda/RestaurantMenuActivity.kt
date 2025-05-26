package com.example.foodpanda

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodpanda.adapters.MenuListAdapter
import com.example.foodpanda.model.Menu
import com.example.foodpanda.model.RestaurantModel

class RestaurantMenuActivity : AppCompatActivity(), MenuListAdapter.MenuListClickListener {

    private var menuList: List<Menu>? = null
    private lateinit var menuListAdapter: MenuListAdapter
    private var itemsInCartList: MutableList<Menu>? = null
    private var totalItemInCart = 0
    private lateinit var buttonCheckout: android.widget.TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_menu)

        val restaurantModel = intent.getParcelableExtra<RestaurantModel>("RestaurantModel")
        supportActionBar?.apply {
            title = restaurantModel?.name
            subtitle = restaurantModel?.address
            setDisplayHomeAsUpEnabled(true)
        }

        menuList = restaurantModel?.menus
        initRecyclerView()

        buttonCheckout = findViewById(R.id.buttonCheckout)
        buttonCheckout.setOnClickListener {
            if (itemsInCartList == null || itemsInCartList!!.isEmpty()) {
                Toast.makeText(this, "Please add some items in cart.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            restaurantModel?.menus = itemsInCartList!!
            val intent = Intent(this, PlaceYourOrderActivity::class.java)
            intent.putExtra("RestaurantModel", restaurantModel)
            startActivityForResult(intent, 1000)
        }
    }

    private fun initRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        menuListAdapter = MenuListAdapter(menuList ?: emptyList(), this)
        recyclerView.adapter = menuListAdapter
    }

    override fun onAddToCartClick(menu: Menu) {
        if (itemsInCartList == null) {
            itemsInCartList = mutableListOf()
        }
        itemsInCartList!!.add(menu)
        totalItemInCart = itemsInCartList!!.sumOf { it.totalInCart }
        buttonCheckout.text = "Checkout ($totalItemInCart items)"
    }

    override fun onUpdateCartClick(menu: Menu) {
        itemsInCartList?.let {
            val index = it.indexOf(menu)
            if (index != -1) {
                it[index] = menu
                totalItemInCart = it.sumOf { m -> m.totalInCart }
                buttonCheckout.text = "Checkout ($totalItemInCart items)"
                buttonCheckout.setTextColor(resources.getColor(R.color.white))
            }
        }
    }

    override fun onRemoveFromCartClick(menu: Menu) {
        itemsInCartList?.let {
            it.remove(menu)
            totalItemInCart = it.sumOf { m -> m.totalInCart }
            buttonCheckout.text = "Checkout ($totalItemInCart items)"
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {
            finish()
        }
    }
}
