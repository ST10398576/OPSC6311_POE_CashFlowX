package com.example.cashflowx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cashflowx.adapter.EarningsAdapter
import com.example.cashflowx.data.TransactionManager

class Earnings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earnings_page)

        val recyclerView = findViewById<RecyclerView>(R.id.earningsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val earningsList = TransactionManager.getEarnings()

        recyclerView.adapter = EarningsAdapter(earningsList)
    }
}
