package com.example.opsc6311_poe_cashflowx

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.opsc6311_poe_cashflowx.model.EarningsItem

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

