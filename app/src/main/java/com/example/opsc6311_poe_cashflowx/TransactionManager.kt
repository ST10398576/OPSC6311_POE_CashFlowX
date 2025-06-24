package com.example.cashflowx.data

import com.example.cashflowx.model.EarningsItem
import com.example.cashflowx.model.ExpensesItem

object TransactionManager {
    val expensesList = mutableListOf<ExpensesItem>()
    val earningsList = mutableListOf<EarningsItem>()


    fun addExpense(item: ExpensesItem) {
        expensesList.add(item)
    }

    fun addEarning(item: EarningsItem) {
        earningsList.add(item)
    }

    fun getExpenses(): List<ExpensesItem> = expensesList
    fun getEarnings(): List<EarningsItem> = earningsList
}