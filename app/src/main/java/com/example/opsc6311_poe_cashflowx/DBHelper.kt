package com.example.opsc6311_poe_cashflowx

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "CashflowDB.db", null, 2) {

    companion object {
        private const val TABLE_NAME = "transactions"
        private const val USERS_TABLE = "users"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            """
            CREATE TABLE $USERS_TABLE (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                username TEXT,
                email TEXT,
                password TEXT
            )
        """.trimIndent()
        )

        db?.execSQL(
            """
            CREATE TABLE $TABLE_NAME (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                dateTime TEXT,
                category TEXT,
                cost REAL,
                note TEXT,
                type TEXT,
                imageUri TEXT
            )
        """.trimIndent()
        )

        db?.execSQL(
            """
            CREATE TABLE BudgetCategory (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT UNIQUE,
                minGoal REAL,
                maxGoal REAL,
                month TEXT
            )
        """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $USERS_TABLE")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS BudgetCategory")
        onCreate(db)
    }

    fun insertBudgetCategory(name: String, minGoal: Double, maxGoal: Double, month: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("name", name)
            put("minGoal", minGoal)
            put("maxGoal", maxGoal)
            put("month", month)
        }
        val result = db.insert("BudgetCategory", null, values)
        return result != -1L
    }

    fun getBudgetCategoriesForMonth(month: String): List<BudgetCategory> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM BudgetCategory WHERE month = ?", arrayOf(month))
        val categories = mutableListOf<BudgetCategory>()

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val minGoal = cursor.getDouble(cursor.getColumnIndexOrThrow("minGoal"))
                val maxGoal = cursor.getDouble(cursor.getColumnIndexOrThrow("maxGoal"))
                categories.add(BudgetCategory(name, minGoal, maxGoal, month))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return categories
    }

    fun getCategorySpendingInPeriod(category: String, startDate: String, endDate: String): Double {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            """
            SELECT SUM(cost) FROM $TABLE_NAME
            WHERE category = ? AND type = 'EXPENSE' AND dateTime BETWEEN ? AND ?
        """, arrayOf(category, startDate, endDate)
        )

        cursor.use {
            if (it.moveToFirst()) {
                return it.getDouble(0)
            }
        }
        db.close()
        return 0.0
    }

    fun insertTransaction(transaction: Transaction): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("name", transaction.name)
            put("dateTime", transaction.date)
            put("category", transaction.category)
            put("cost", transaction.cost)
            put("note", transaction.note)
            put("type", transaction.type.name)
            put("imageUri", transaction.imageUrl)
        }
        val result = db.insert(TABLE_NAME, null, values)
        return result != -1L
    }

    fun getTransactionsByType(type: TransactionType): List<Transaction> {
        val db = this.readableDatabase
        val transactions = mutableListOf<Transaction>()
        val cursor: Cursor = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE type = ?", arrayOf(type.name)
        )

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val dateTime = cursor.getString(cursor.getColumnIndexOrThrow("dateTime"))
                val category = cursor.getString(cursor.getColumnIndexOrThrow("category"))
                val cost = cursor.getDouble(cursor.getColumnIndexOrThrow("cost"))
                val note = cursor.getString(cursor.getColumnIndexOrThrow("note"))
                val imageUri = cursor.getString(cursor.getColumnIndexOrThrow("imageUri"))
                val transactionType = TransactionType.valueOf(cursor.getString(cursor.getColumnIndexOrThrow("type")))

                transactions.add(Transaction(name, dateTime, category, cost, note, transactionType, imageUri))
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return transactions
    }

    fun registerUser(username: String, email: String, password: String): Boolean {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put("username", username)
            put("email", email)
            put("password", password)
        }
        val result = db.insert(USERS_TABLE, null, values)
        return result != -1L
    }

    fun checkUserExists(email: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $USERS_TABLE WHERE email = ?", arrayOf(email))
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun loginUser(username: String, password: String): Boolean {
        val db = this.readableDatabase
        val query = "SELECT * FROM $USERS_TABLE WHERE username = ? AND password = ?"
        val cursor = db.rawQuery(query, arrayOf(username, password))
        val isLoggedIn = cursor.count > 0
        cursor.close()
        db.close()
        return isLoggedIn
    }

    fun updatePassword(username: String, oldPassword: String, newPassword: String): Boolean {
        val db = this.writableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM users WHERE username = ? AND password = ?",
            arrayOf(username, oldPassword)
        )

        val success = if (cursor.moveToFirst()) {
            val values = ContentValues().apply {
                put("password", newPassword)
            }
            db.update("users", values, "username = ?", arrayOf(username)) > 0
        } else {
            false
        }

        cursor.close()
        return success
    }
}
