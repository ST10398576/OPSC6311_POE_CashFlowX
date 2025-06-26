package com.example.opsc6311_poe_cashflowx

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpensesItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val expenTitle: String,
    val expenDate: String,
    val expenAmount: Double,
    val expenCategory: String,
    val expenNote: String,
    val expenImage: String
)