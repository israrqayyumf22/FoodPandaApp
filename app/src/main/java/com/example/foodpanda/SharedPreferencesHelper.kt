package com.example.foodpanda

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USERNAME = "username"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setLoggedIn(loggedIn: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, loggedIn).apply()
    }

    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    fun setUsername(username: String) {
        sharedPreferences.edit().putString(KEY_USERNAME, username).apply()
    }

    fun getUsername(): String {
        return sharedPreferences.getString(KEY_USERNAME, "User") ?: "User"
    }
}
