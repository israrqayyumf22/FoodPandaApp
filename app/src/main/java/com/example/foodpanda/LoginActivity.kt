package com.example.foodpanda

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var btnlogin: Button
    private lateinit var register: TextView
    private lateinit var DB: DBHelper
    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.username1)
        password = findViewById(R.id.password1)
        btnlogin = findViewById(R.id.btnsignin1)
        register = findViewById(R.id.btnsignupText)
        DB = DBHelper(this)
        sharedPreferencesHelper = SharedPreferencesHelper(this)

        btnlogin.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()

            if (user.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            } else {
                val checkuserpass = DB.checkusernamepassword(user, pass)
                if (checkuserpass) {
                    Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show()

                    sharedPreferencesHelper.setLoggedIn(true)
                    sharedPreferencesHelper.setUsername(user)

                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }

        register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}
