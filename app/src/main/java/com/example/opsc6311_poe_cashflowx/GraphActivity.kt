package com.example.opsc6311_poe_cashflowx

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate

class GraphActivity : AppCompatActivity() {

    private lateinit var barChart: BarChart
    private lateinit var progressBar: ProgressBar
    private lateinit var btnAddExpense: Button
    private lateinit var btnAchievements: Button
    private lateinit var periodSpinner: Spinner
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        barChart = findViewById(R.id.barChart)
        progressBar = findViewById(R.id.progressBar)
        btnAddExpense = findViewById(R.id.btnAddExpense)
        btnAchievements = findViewById(R.id.btnAchievements)
        periodSpinner = findViewById(R.id.spinnerPeriod)


        btnAddExpense.setOnClickListener {
            Toast.makeText(this, "Expense added!", Toast.LENGTH_SHORT).show()
            updateProgressBar(75)
        }

        btnAchievements.setOnClickListener {
            startActivity(Intent(this, AchievementsActivity::class.java))
        }

        val periods = arrayOf("This Week", "This Month", "This Year")
        periodSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, periods)

        val items = listOf("Food: R200", "Transport: R150", "Entertainment: R300")
        listView.adapter = ExpensesAdapter(this, items)

        setupBarChart()
    }

    private fun setupBarChart() {
        val entries = listOf(
            BarEntry(0f, 200f),
            BarEntry(1f, 150f),
            BarEntry(2f, 300f)
        )
        val labels = listOf("Food", "Transport", "Entertainment")

        val barDataSet = BarDataSet(entries, "Spending")
        barDataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()
        barDataSet.valueTextSize = 14f

        val barData = BarData(barDataSet)
        barChart.data = barData

        barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
        barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart.description.isEnabled = false
        barChart.animateY(1000)
        barChart.invalidate()
    }

    private fun updateProgressBar(value: Int) {
        progressBar.progress = 75
    }
}
