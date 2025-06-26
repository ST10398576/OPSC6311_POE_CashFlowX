package com.example.opsc6311_poe_cashflowx

import com.example.opsc6311_poe_cashflowx.model.ExpensesItem
import com.example.opsc6311_poe_cashflowx.model.EarningsItem
import com.google.firebase.database.FirebaseDatabase

object TransactionManager {

    private val dbRef = FirebaseDatabase.getInstance().reference

    fun addExpense(
        id: String,
        title: String,
        date: String,
        amount: Double,
        category: String,
        notes: String,
        imageUrl: String
    ) {
        val expense = ExpensesItem(id, title, date, amount, category, notes, imageUrl)
        dbRef.child("expenses").child(id).setValue(expense)
    }

    fun addEarning(
        id: String,
        title: String,
        date: String,
        amount: Double,
        category: String,
        notes: String,
        imageUrl: String
    ) {
        val earning = EarningsItem(id, title, date, amount, category, notes, imageUrl)
        dbRef.child("earnings").child(id).setValue(earning)
    }
}
