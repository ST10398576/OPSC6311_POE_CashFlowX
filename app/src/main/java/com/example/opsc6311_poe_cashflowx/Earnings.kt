package com.example.opsc6311_poe_cashflowx

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc6311_poe_cashflowx.model.EarningsItem
import com.google.firebase.database.*

class Earnings : AppCompatActivity() {

    private lateinit var earningsRecyclerView: RecyclerView
    private lateinit var earningsAdapter: EarningsAdapter
    private val earningsList = mutableListOf<EarningsItem>()

    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earnings_page)

        // Initialize RecyclerView
        earningsRecyclerView = findViewById(R.id.earningsRecyclerView)
        earningsRecyclerView.layoutManager = LinearLayoutManager(this)

        earningsAdapter = EarningsAdapter(earningsList)
        earningsRecyclerView.adapter = earningsAdapter

        // Initialize Firebase reference
        databaseRef = FirebaseDatabase.getInstance().getReference("earnings")

        // Fetch data
        fetchEarningsFromFirebase()
    }

    private fun fetchEarningsFromFirebase() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                earningsList.clear()
                for (earningSnapshot in snapshot.children) {
                    val earning = earningSnapshot.getValue(EarningsItem::class.java)
                    if (earning != null) {
                        earningsList.add(earning)
                    }
                }
                earningsAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Earnings", "Database error: ${error.message}")
            }
        })
    }
}
