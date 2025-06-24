package com.example.cashflowx

import androidx.room.Database
import androidx.room.Room
import com.example.cashflowx.model.ExpensesItem

// AppDatabase.kt
@Database(entities = [ExpensesItem::class, EarningsItem::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun expensesDao(): ExpensesDao
    abstract fun earningsDao(): EarningsDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cashflowx_db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}

