package com.example.cashflowx

data class BudgetCategoryDisplay(
    val name: String,
    val minGoal: Double,
    val maxGoal: Double,
    val actualSpent: Double
)