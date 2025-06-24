package com.example.opsc6311_poe_cashflowx

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Expenses : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expenses_page)

        val backIcon = findViewById<ImageView>(R.id.white_back_icon)
        backIcon.setOnClickListener {
            finish()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.expensesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val expensesList = TransactionManager.getExpenses()

        recyclerView.adapter = ExpensesAdapter(expensesList)
    }
}
