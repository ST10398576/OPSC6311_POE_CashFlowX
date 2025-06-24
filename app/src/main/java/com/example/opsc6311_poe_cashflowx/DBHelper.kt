package com.example.opsc6311_poe_cashflowx

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "CashflowDB.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT, password TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun registerUser(username: String, email: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("username", username)
            put("email", email)
            put("password", password)
        }
        val result = db.insert("users", null, values)
        return result != -1L
    }

    fun checkUserExists(email: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM users WHERE email = ?", arrayOf(email))
        val exists = cursor.count > 0
        cursor.close()
        return exists
    }

    fun loginUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM users WHERE username = ? AND password = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val isLoggedIn = cursor.count > 0
        cursor.close()
        return isLoggedIn
    }

    fun updatePassword(username: String, oldPassword: String, newPassword: String): Boolean {
        val db = this.writableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM users WHERE username = ? AND password = ?",
            arrayOf(username, oldPassword)
        )

        return if (cursor.count > 0) {
            val values = ContentValues().apply {
                put("password", newPassword)
            }
            db.update("users", values, "username = ?", arrayOf(username))
            cursor.close()
            true
        } else {
            cursor.close()
            false
        }
    }
}

}