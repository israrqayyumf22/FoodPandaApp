package com.example.foodpanda

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var repassword: EditText
    private lateinit var signup: Button
    private lateinit var signin: TextView
    private lateinit var DB: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        repassword = findViewById(R.id.repassword)
        signup = findViewById(R.id.btnsignup)
        signin = findViewById(R.id.btnsignin)
        DB = DBHelper(this)

        signup.setOnClickListener {
            val user = username.text.toString()
            val pass = password.text.toString()
            val repass = repassword.text.toString()

            if (user.isEmpty() || pass.isEmpty() || repass.isEmpty()) {
                Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
            } else {
                if (pass == repass) {
                    val checkuser = DB.checkusername(user)
                    if (!checkuser) {
                        val insert = DB.insertData(user, pass)
                        if (insert) {
                            Toast.makeText(this, "Registered successfully", Toast.LENGTH_SHORT).show()

                            val sharedPreferencesHelper = SharedPreferencesHelper(this)
                            sharedPreferencesHelper.setLoggedIn(true)

                            val mainIntent = Intent(this, MainActivity::class.java)
                            startActivity(mainIntent)
                            finish()
                        } else {
                            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "User already exists! please sign in", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Passwords not matching", Toast.LENGTH_SHORT).show()
                }
            }
        }

        signin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}
