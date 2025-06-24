package com.example.cashflowx

// EarningsDao.kt
@Dao
interface EarningsDao {
    @Insert
    suspend fun insertEarning(earning: EarningsItem)

    @Query("SELECT * FROM earnings")
    suspend fun getAllEarnings(): List<EarningsItem>
}