package com.example.opsc6311_poe_cashflowx

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc6311_poe_cashflowx.model.ExpensesItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Expenses : AppCompatActivity() {

    private lateinit var expensesRecyclerView: RecyclerView
    private lateinit var expensesAdapter: ExpensesAdapter
    private val expensesList = mutableListOf<ExpensesItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses_page)

        expensesRecyclerView = findViewById(R.id.recyclerViewExpenses)
        expensesRecyclerView.layoutManager = LinearLayoutManager(this)
        expensesAdapter = ExpensesAdapter(expensesList)
        expensesRecyclerView.adapter = expensesAdapter

        fetchExpensesFromFirebase()
    }

    private fun fetchExpensesFromFirebase() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val dbRef = FirebaseDatabase.getInstance().reference
            .child("Users")
            .child(userId)
            .child("Expenses")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                expensesList.clear()
                for (expenseSnap in snapshot.children) {
                    val item = expenseSnap.getValue(ExpensesItem::class.java)
                    item?.id = expenseSnap.key
                    if (item != null) {
                        expensesList.add(item)
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
