package com.example.opsc6311_poe_cashflowx

import com.example.opsc6311_poe_cashflowx.model.EarningsItem

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