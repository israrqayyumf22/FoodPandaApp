package com.example.foodpanda

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.foodpanda.model.RestaurantModel

class OrderSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_success)

        val restaurantModel = intent.getParcelableExtra<RestaurantModel>("RestaurantModel")

        supportActionBar?.apply {
            title = restaurantModel?.name ?: "Order Success"
            subtitle = restaurantModel?.address ?: ""
            setDisplayHomeAsUpEnabled(true)
        }

        val buttonDone = findViewById<TextView>(R.id.buttonDone)
        buttonDone.setOnClickListener { finish() }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
