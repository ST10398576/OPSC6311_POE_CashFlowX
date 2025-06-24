package com.example.opsc6311_poe_cashflowx

import androidx.room.*
import com.example.opsc6311_poe_cashflowx.model.EarningsItem

// EarningsDao.kt
@Dao
interface EarningsDao {
    @Insert
    suspend fun insertEarning(earning: EarningsItem)

    @Query("SELECT * FROM earnings")
    suspend fun getAllEarnings(): List<EarningsItem>
}