package com.example.opsc6311_poe_cashflowx.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "earnings")
data class EarningsItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val earnTitle: String,
    val earnDate: String,
    val earnAmount: Double,
    val earnCategory: String,
    val earnNote: String,
    val earnImage: String
)