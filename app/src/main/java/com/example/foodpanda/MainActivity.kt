package com.example.foodpanda

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader
import com.example.foodpanda.adapters.RestaurantListAdapter
import com.example.foodpanda.model.RestaurantModel
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), RestaurantListAdapter.RestaurantListClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Restaurant List"

        val restaurantModelList = getRestaurantData()
        initRecyclerView(restaurantModelList)
    }

    private fun initRecyclerView(restaurantModelList: List<RestaurantModel>) {
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RestaurantListAdapter(restaurantModelList, this)
    }

    private fun getRestaurantData(): List<RestaurantModel> {
        val inputStream = resources.openRawResource(R.raw.restaurent)
        val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
        val jsonStr = reader.use { it.readText() }
        return Gson().fromJson(jsonStr, Array<RestaurantModel>::class.java).toList()
    }

    override fun onItemClick(restaurantModel: RestaurantModel) {
        val intent = Intent(this, RestaurantMenuActivity::class.java)
        intent.putExtra("RestaurantModel", restaurantModel)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        AlertDialog.Builder(this)
            .setTitle("Exit")
            .setMessage("Do you want to exit?")
            .setPositiveButton("Yes") { _, _ -> finish() }
            .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        MenuInflater(this).inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_account) {
            startActivity(Intent(this, AccountActivity::class.java))
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}