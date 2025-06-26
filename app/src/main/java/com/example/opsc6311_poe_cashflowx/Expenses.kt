package com.example.opsc6311_poe_cashflowx

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc6311_poe_cashflowx.model.EarningsItem
import com.example.opsc6311_poe_cashflowx.model.ExpensesItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Expenses : AppCompatActivity() {

    private lateinit var expensesRecyclerView: RecyclerView
    private lateinit var expensesAdapter: ExpensesAdapter
    private val expensesList = mutableListOf<ExpensesItem>()

    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses_page)

        // Initialize RecyclerView
        expensesRecyclerView = findViewById(R.id.expensesRecyclerView)
        expensesRecyclerView.layoutManager = LinearLayoutManager(this)

        expensesAdapter = ExpensesAdapter(expensesList)
        expensesRecyclerView.adapter = expensesAdapter

        // Initialize Firebase reference
        databaseRef = FirebaseDatabase.getInstance().getReference("earnings")

        // Fetch data
        fetchExpensesFromFirebase()
    }

    private fun fetchExpensesFromFirebase() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                expensesList.clear()
                for (expenseSnapshot in snapshot.children) {
                    val expense = expenseSnapshot.getValue(ExpensesItem::class.java)
                    if (expense != null) {
                        expensesList.add(expense)
                    }
                }
                expensesAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Expenses, "Failed to load expenses.", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
