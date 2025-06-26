package com.example.opsc6311_poe_cashflowx

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc6311_poe_cashflowx.model.EarningsItem
import com.example.opsc6311_poe_cashflowx.model.ExpensesItem
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.database.*

class GraphActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private lateinit var progressBar: ProgressBar
    private lateinit var btnAddExpense: Button
    private lateinit var btnAchievements: Button
    private lateinit var periodSpinner: Spinner
    private lateinit var recyclerView: RecyclerView

    private val database = FirebaseDatabase.getInstance()
    private val expensesRef = database.getReference("expenses")
    private val earningsRef = database.getReference("earnings")

    private val expenseMap = mutableMapOf<String, Float>()
    private val earningMap = mutableMapOf<String, Float>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        barChart = findViewById(R.id.barChart)
        progressBar = findViewById(R.id.progressBar)
        btnAddExpense = findViewById(R.id.btnAddExpense)
        btnAchievements = findViewById(R.id.btnAchievements)
        periodSpinner = findViewById(R.id.spinnerPeriod)
        recyclerView = findViewById(R.id.recyclerViewExpenses)

        recyclerView.layoutManager = LinearLayoutManager(this)

        periodSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            arrayOf("This Week", "This Month", "This Year")
        )

        btnAddExpense.setOnClickListener {
            Toast.makeText(this, "Expense added!", Toast.LENGTH_SHORT).show()
            updateProgressBar(75)
        }

        btnAchievements.setOnClickListener {
            startActivity(Intent(this, AchievementsActivity::class.java))
        }

        fetchDataAndDisplayGraph()
    }

    private fun fetchDataAndDisplayGraph() {
        // Fetch expenses
        expensesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                expenseMap.clear()
                val expenseItems = mutableListOf<ExpensesItem>()

                for (child in snapshot.children) {
                    val item = child.getValue(ExpensesItem::class.java)
                    if (item != null) {
                        expenseItems.add(item)
                        val current = expenseMap[item.category] ?: 0f
                        expenseMap[item.category] = current + item.amount.toFloat()
                    }
                }

                recyclerView.adapter = ExpensesAdapter(expenseItems)

                // Fetch earnings after expenses
                earningsRef.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        earningMap.clear()

                        for (child in snapshot.children) {
                            val item = child.getValue(EarningsItem::class.java)
                            if (item != null) {
                                val current = earningMap[item.category] ?: 0f
                                earningMap[item.category] = current + item.amount.toFloat()
                            }
                        }

                        setupBarChart()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@GraphActivity, "Failed to load earnings", Toast.LENGTH_SHORT).show()
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@GraphActivity, "Failed to load expenses", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupBarChart() {
        val entries = mutableListOf<BarEntry>()
        val labels = mutableListOf<String>()

        var index = 0f
        for ((category, amount) in expenseMap) {
            entries.add(BarEntry(index, amount))
            labels.add("Expense: $category")
            index++
        }

        for ((category, amount) in earningMap) {
            entries.add(BarEntry(index, amount))
            labels.add("Earning: $category")
            index++
        }

        val barDataSet = BarDataSet(entries, "Income vs Expenses")
        barDataSet.colors = ColorTemplate.COLORFUL_COLORS.toList()
        barDataSet.valueTextSize = 14f

        barChart.data = BarData(barDataSet)
        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.xAxis.labelRotationAngle = -45f
        barChart.description.isEnabled = false
        barChart.setFitBars(true)
        barChart.animateY(1000)
        barChart.invalidate()
    }

    private fun updateProgressBar(value: Int) {
        progressBar.progress = value
    }
}
