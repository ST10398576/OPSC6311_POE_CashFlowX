package com.example.opsc6311_poe_cashflowx

// Firebase requires a no-arg constructor and public vars with default values
data class BudgetCategoryDisplay(
    var name: String = "",
    var minGoal: Double = 0.0,
    var maxGoal: Double = 0.0,
    var actualSpent: Double = 0.0
)