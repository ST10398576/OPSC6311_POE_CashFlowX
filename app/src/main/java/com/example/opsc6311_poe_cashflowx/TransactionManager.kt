package com.example.opsc6311_poe_cashflowx

import com.example.opsc6311_poe_cashflowx.model.EarningsItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

object TransactionManager {
    private val dbRef = FirebaseDatabase.getInstance().reference

    fun addExpense(item: ExpensesItem) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        dbRef.child("Users").child(userId).child("Expenses").push().setValue(item)
    }

    fun addEarning(item: EarningsItem) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        dbRef.child("Users").child(userId).child("Earnings").push().setValue(item)
    }

    // TODO: Add listeners for loading data when needed
}