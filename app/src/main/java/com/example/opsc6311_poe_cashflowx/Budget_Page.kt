package com.example.opsc6311_poe_cashflowx

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Budget_Page : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var budgetRef: DatabaseReference
    private lateinit var adapter: BudgetCategoryAdapter
    private val categoryList = mutableListOf<BudgetCategoryDisplay>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_page)

        val currentUser = FirebaseAuth.getInstance().currentUser
        budgetRef = FirebaseDatabase.getInstance().getReference("budgets").child(currentUser?.uid ?: "unknown_user")

        recyclerView = findViewById(R.id.recyclerViewBudget)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = BudgetCategoryAdapter(categoryList)
        recyclerView.adapter = adapter

        loadBudgetsFromFirebase()

        findViewById<FloatingActionButton>(R.id.fabAddCategory).setOnClickListener {
            showAddCategoryDialog()
        }
    }

    private fun saveBudgetCategoryToFirebase(category: BudgetCategory) {
        val key = budgetRef.push().key
        key?.let {
            budgetRef.child(it).setValue(category)
                .addOnSuccessListener {
                    Toast.makeText(this, "Budget saved!", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save budget.", Toast.LENGTH_SHORT).show()
                }
        }
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
                    val category = BudgetCategory(name, min, max, currentMonth)
                    saveBudgetCategoryToFirebase(category)
                } else {
                    Toast.makeText(this, "Name is required", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun loadBudgetsFromFirebase() {
        budgetRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                categoryList.clear()
                for (data in snapshot.children) {
                    val category = data.getValue(BudgetCategory::class.java)
                    category?.let {
                        // Placeholder: actualSpent = 0.0, later calculate from expenses
                        categoryList.add(BudgetCategoryDisplay(it.name, it.minGoal, it.maxGoal, 0.0))
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Budget_Page, "Error loading budgets", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
