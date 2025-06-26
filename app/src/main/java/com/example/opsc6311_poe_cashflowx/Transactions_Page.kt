package com.example.opsc6311_poe_cashflowx

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class Transactions_Page : AppCompatActivity() {

    private lateinit var titleInput: EditText
    private lateinit var dateInput: EditText
    private lateinit var amountInput: EditText
    private lateinit var categoryInput: EditText
    private lateinit var notesInput: EditText
    private lateinit var imageUrlInput: EditText
    private lateinit var editRadioGroup: RadioGroup
    private lateinit var addButton: Button
    private val calendar = Calendar.getInstance()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)

        titleInput = findViewById(R.id.editName)
        dateInput = findViewById(R.id.editDate)
        categoryInput = findViewById(R.id.editCategory)
        amountInput = findViewById(R.id.editCost)
        notesInput = findViewById(R.id.editNote)
        imageUrlInput = findViewById(R.id.imagePreview)
        editRadioGroup = findViewById(R.id.editRadioGroup)
        addButton = findViewById(R.id.btnAdd)

        // Set up date picker
        dateInput.setOnClickListener {
            DatePickerDialog(this,
                { _, year, month, day ->
                    calendar.set(year, month, day)
                    dateInput.setText(dateFormat.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        addButton.setOnClickListener {
            saveTransaction()
        }
    }

    private fun saveTransaction() {
        val id = UUID.randomUUID().toString()
        val title = titleInput.text.toString().trim()
        val amount = amountInput.text.toString().toDoubleOrNull() ?: 0.0
        val category = categoryInput.text.toString().trim()
        val notes = notesInput.text.toString().trim()
        val imageUrl = imageUrlInput.text.toString().trim()
        val date = dateInput.text.toString().trim()

        if (title.isEmpty() || category.isEmpty() || date.isEmpty()) {
            Toast.makeText(this, "Please fill in all required fields.", Toast.LENGTH_SHORT).show()
            return
        }

        when (editRadioGroup.checkedRadioButtonId) {
            R.id.expenseRadio -> {
                TransactionManager.addExpense(id, title, date, amount, category, notes, imageUrl)
                Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show()
                finish()
            }
            R.id.earningRadio -> {
                TransactionManager.addEarning(id, title, date, amount, category, notes, imageUrl)
                Toast.makeText(this, "Earning added", Toast.LENGTH_SHORT).show()
                finish()
            }
            else -> {
                Toast.makeText(this, "Select transaction type", Toast.LENGTH_SHORT).show()
            }
        }
        // Clear input fields
        titleInput.text.clear()
        dateInput.text.clear()
        amountInput.text.clear()
        categoryInput.text.clear()
        notesInput.text.clear()
        imageUrlInput.text.clear()
    }
}
