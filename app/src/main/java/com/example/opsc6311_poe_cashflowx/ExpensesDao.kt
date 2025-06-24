package com.example.opsc6311_poe_cashflowx

// ExpensesDao.kt
@Dao
interface ExpensesDao {
    @Insert
    suspend fun insertExpense(expense: ExpensesItem)

    @Query("SELECT * FROM expenses")
    suspend fun getAllExpenses(): List<ExpensesItem>
}