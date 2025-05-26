package com.example.foodpanda

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DBNAME, null, 1) {

    companion object {
        const val DBNAME = "Login.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("create Table users(username TEXT primary key, password TEXT)")
        db.execSQL("create Table orders(username TEXT, `order` TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("drop Table if exists users")
        db.execSQL("drop Table if exists orders")
        onCreate(db)
    }

    fun insertData(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("username", username)
            put("password", password)
        }
        val result = db.insert("users", null, contentValues)
        return result != -1L
    }

    fun checkusername(username: String): Boolean {
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery("Select * from users where username = ?", arrayOf(username))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun insertOrder(username: String, order: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put("username", username)
            put("order", order)
        }
        val result = db.insert("orders", null, contentValues)
        return result != -1L
    }

    fun checkusernamepassword(username: String, password: String): Boolean {
        val db = this.writableDatabase
        val cursor: Cursor = db.rawQuery("Select * from users where username = ? and password = ?", arrayOf(username, password))
        val valid = cursor.count > 0
        cursor.close()
        return valid
    }
}
