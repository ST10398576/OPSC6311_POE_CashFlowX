package com.example.opsc6311_poe_cashflowx

import android.media.Image
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Transaction(
    val name: String,
    val dateTime: String,
    val category: String,
    val cost: Double,
    val note: String,
    val type: TransactionType,
) : Parcelable

enum class TransactionType {
    EXPENSE, EARNING
}