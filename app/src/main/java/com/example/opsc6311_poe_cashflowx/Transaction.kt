package com.example.opsc6311_poe_cashflowx


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val name: String,
    val date: String,
    val category: String,
    val cost: Double,
    val note: String,
    val type: TransactionType,
    val imageUrl: String
) : Parcelable

enum class TransactionType {
    EXPENSE, EARNING
}