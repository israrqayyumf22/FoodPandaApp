package com.example.foodpanda

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodpanda.adapters.PlaceYourOrderAdapter
import com.example.foodpanda.model.RestaurantModel
import com.google.android.material.textfield.TextInputLayout

class PlaceYourOrderActivity : AppCompatActivity() {

    private lateinit var inputName: EditText
    private lateinit var inputAddress: EditText
    private lateinit var addressLayout: TextInputLayout
    private lateinit var inputCardNumber: EditText
    private lateinit var inputCardExpiry: EditText
    private lateinit var inputCardPin: EditText
    private lateinit var tvSubtotalAmount: TextView
    private lateinit var tvDeliveryChargeAmount: TextView
    private lateinit var tvDeliveryCharge: TextView
    private lateinit var tvTotalAmount: TextView
    private lateinit var buttonPlaceYourOrder: TextView
    private lateinit var btnPickup: Button
    private lateinit var btnDelivery: Button
    private lateinit var cartItemsRecyclerView: RecyclerView
    private var isDeliveryOn = false
    private lateinit var placeYourOrderAdapter: PlaceYourOrderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_your_order)

        val restaurantModel = intent.getParcelableExtra<RestaurantModel>("RestaurantModel")

        supportActionBar?.apply {
            title = restaurantModel?.name ?: "Order"
            subtitle = restaurantModel?.address ?: ""
            setDisplayHomeAsUpEnabled(true)
        }

        inputName = findViewById(R.id.inputName)
        inputAddress = findViewById(R.id.inputAddressxml)
        addressLayout = findViewById(R.id.Addresslayout)
        inputCardNumber = findViewById(R.id.inputCardNumber)
        inputCardExpiry = findViewById(R.id.inputCardExpiry)
        inputCardPin = findViewById(R.id.inputCardPin)
        tvSubtotalAmount = findViewById(R.id.tvSubtotalAmount)
        tvDeliveryChargeAmount = findViewById(R.id.tvDeliveryChargeAmount)
        tvDeliveryCharge = findViewById(R.id.tvDeliveryCharge)
        tvTotalAmount = findViewById(R.id.tvTotalAmount)
        buttonPlaceYourOrder = findViewById(R.id.buttonPlaceYourOrder)
        btnPickup = findViewById(R.id.btnPickup)
        btnDelivery = findViewById(R.id.btnDelivery)
        cartItemsRecyclerView = findViewById(R.id.cartItemsRecyclerView)

        buttonPlaceYourOrder.setOnClickListener {
            restaurantModel?.let { onPlaceOrderButtonClick(it) }
        }

        btnPickup.setOnClickListener {
            setDeliveryOption(false)
            restaurantModel?.let { calculateTotalAmount(it) }
        }

        btnDelivery.setOnClickListener {
            setDeliveryOption(true)
            restaurantModel?.let { calculateTotalAmount(it) }
        }

        setDeliveryOption(false)
        restaurantModel?.let { initRecyclerView(it) }
        restaurantModel?.let { calculateTotalAmount(it) }
    }

    private fun setDeliveryOption(delivery: Boolean) {
        isDeliveryOn = delivery
        if (delivery) {
            btnDelivery.setBackgroundColor(resources.getColor(R.color.foodpanda_pink))
            btnPickup.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
            addressLayout.visibility = android.view.View.VISIBLE
            tvDeliveryChargeAmount.visibility = android.view.View.VISIBLE
            tvDeliveryCharge.visibility = android.view.View.VISIBLE
        } else {
            btnPickup.setBackgroundColor(resources.getColor(R.color.foodpanda_pink))
            btnDelivery.setBackgroundColor(resources.getColor(android.R.color.darker_gray))
            addressLayout.visibility = android.view.View.GONE
            tvDeliveryChargeAmount.visibility = android.view.View.GONE
            tvDeliveryCharge.visibility = android.view.View.GONE
        }
    }


    private fun calculateTotalAmount(restaurantModel: RestaurantModel) {
        var subTotalAmount = 0f

        // Null safety: if menus is nullable, add safe call
        restaurantModel.menus?.forEach { menuItem ->
            subTotalAmount += menuItem.price * menuItem.totalInCart
        }

        tvSubtotalAmount.text = "PKR %.2f".format(subTotalAmount)
        if (isDeliveryOn) {
            tvDeliveryChargeAmount.text = "PKR %.2f".format(restaurantModel.delivery_charge)
            subTotalAmount += restaurantModel.delivery_charge
        }
        tvTotalAmount.text = "PKR %.2f".format(subTotalAmount)
    }

    private fun onPlaceOrderButtonClick(restaurantModel: RestaurantModel) {
        if (TextUtils.isEmpty(inputName.text.toString())) {
            inputName.error = "Please enter name"
            Toast.makeText(this, "Please enter name", Toast.LENGTH_SHORT).show()
            return
        }
        if (isDeliveryOn && TextUtils.isEmpty(inputAddress.text.toString())) {
            inputAddress.error = "Please enter address"
            Toast.makeText(this, "Please enter address", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(inputCardNumber.text.toString())) {
            inputCardNumber.error = "Please enter card number"
            Toast.makeText(this, "Please enter card number", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(inputCardExpiry.text.toString())) {
            inputCardExpiry.error = "Please enter card expiry"
            Toast.makeText(this, "Please enter card expiry", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(inputCardPin.text.toString())) {
            inputCardPin.error = "Please enter card pin/cvv"
            Toast.makeText(this, "Please enter card pin/cvv", Toast.LENGTH_SHORT).show()
            return
        }

        val name = inputName.text.toString()
        val address = inputAddress.text.toString()
        val cardNumber = inputCardNumber.text.toString()
        val cardExpiry = inputCardExpiry.text.toString()
        val cardPin = inputCardPin.text.toString()
        val orderTotal = tvTotalAmount.text.toString().removePrefix("PKR ").toFloatOrNull() ?: 0f
        val deliveryMethod = if (isDeliveryOn) "Delivery" else "Pickup"

        // Start success activity..
        val intent = Intent(this, OrderSuccessActivity::class.java) // Fixed typo here
        intent.putExtra("RestaurantModel", restaurantModel)
        startActivityForResult(intent, 1000)
    }

    private fun initRecyclerView(restaurantModel: RestaurantModel) {
        cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        placeYourOrderAdapter = PlaceYourOrderAdapter(restaurantModel.menus ?: emptyList())
        cartItemsRecyclerView.adapter = placeYourOrderAdapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        if (requestCode == 1000) {
            setResult(Activity.RESULT_OK)
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}
