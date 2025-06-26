package com.example.opsc6311_poe_cashflowx

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc6311_poe_cashflowx.BudgetCategoryAdapter
import com.example.opsc6311_poe_cashflowx.BudgetCategoryDisplay
import com.example.opsc6311_poe_cashflowx.DBHelper
import com.example.opsc6311_poe_cashflowx.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Budget_Page : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: BudgetCategoryAdapter
    private val categoryList = mutableListOf<BudgetCategoryDisplay>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_page)

        dbHelper = DBHelper(this)
        recyclerView = findViewById(R.id.recyclerViewBudget)
        adapter = BudgetCategoryAdapter(categoryList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val fab = findViewById<FloatingActionButton>(R.id.fabAddCategory)
        fab.setOnClickListener {
            showAddCategoryDialog()
        }

        loadBudgetDataForCurrentMonth()
    }

    private fun showAddCategoryDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_budget_category, null)
        val editName = dialogView.findViewById<EditText>(R.id.editCategoryName)
        val editMin = dialogView.findViewById<EditText>(R.id.editMinGoal)
        val editMax = dialogView.findViewById<EditText>(R.id.editMaxGoal)

        AlertDialog.Builder(this)
            .setTitle("Add Budget Category")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = editName.text.toString().trim()
                val min = editMin.text.toString().toDoubleOrNull() ?: 0.0
                val max = editMax.text.toString().toDoubleOrNull() ?: 0.0
                val currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))

                if (name.isNotEmpty()) {
                    val success = dbHelper.insertBudgetCategory(name, min, max, currentMonth)
                    if (success) {
                        Toast.makeText(this, "Category added!", Toast.LENGTH_SHORT).show()
                        loadBudgetDataForCurrentMonth()
                    } else {
                        Toast.makeText(this, "Category already exists!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    private fun loadBudgetDataForCurrentMonth() {
        val currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"))
        val categories = dbHelper.getBudgetCategoriesForMonth(currentMonth)
        categoryList.clear()

        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val now = LocalDate.now()
        val start = now.withDayOfMonth(1).format(dateFormatter)
        val end = now.withDayOfMonth(now.lengthOfMonth()).format(dateFormatter)

        for (category in categories) {
            val spent = dbHelper.getCategorySpendingInPeriod(category.name, start, end)
            categoryList.add(
                BudgetCategoryDisplay(
                    name = category.name,
                    minGoal = category.minGoal,
                    maxGoal = category.maxGoal,
                    actualSpent = spent
                )
            )
        }

        adapter.notifyDataSetChanged()
    }
}