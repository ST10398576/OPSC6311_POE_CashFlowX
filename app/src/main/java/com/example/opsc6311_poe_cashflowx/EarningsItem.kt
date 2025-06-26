package com.example.opsc6311_poe_cashflowx.model

data class EarningsItem(
    var id: String? = null,                // Firebase will generate this
    var title: String = "",                // Expense name
    var date: String = "",                 // Date entered by user
    var amount: Double = 0.0,              // Cost entered by user
    var category: String = "",             // Category entered by user
    var notes: String = "",                // Notes entered by user
    var imageUrl: String = ""              // Firebase Storage image URL
)