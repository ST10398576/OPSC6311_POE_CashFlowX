package com.example.opsc6311_poe_cashflowx

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import androidx.appcompat.app.AppCompatActivity
import com.example.opsc6311_poe_cashflowx.ExpensesItem
import com.example.opsc6311_poe_cashflowx.model.EarningsItem

class Transactions_Page : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editDate: EditText
    private lateinit var editCategory: EditText
    private lateinit var editCost: EditText
    private lateinit var editNote: EditText
    private lateinit var editRadioGroup: RadioGroup
    private lateinit var imagePreview: ImageView
    private val IMAGE_PICK_CODE = 1001
    private var selectedImageUri: Uri? = null // Store selected image URI


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)

        editName = findViewById(R.id.editName)
        editDate = findViewById(R.id.editDate)
        editCategory = findViewById(R.id.editCategory)
        editCost = findViewById(R.id.editCost)
        editNote = findViewById(R.id.editNote)
        editRadioGroup = findViewById(R.id.editRadioGroup)
        imagePreview = findViewById(R.id.imagePreview)

        val addImageBtn = findViewById<Button>(R.id.btnAddImage)
        addImageBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_CODE)
        }

        val db = AppDatabase.getDatabase(this)

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
            lifecycleScope.launch {
                val name = editName.text.toString()
                val date = editDate.text.toString()
                val category = editCategory.text.toString()
                val cost = editCost.text.toString().toDoubleOrNull() ?: 0.0
                val note = editNote.text.toString()
                val type = when (editRadioGroup.checkedRadioButtonId) {
                    R.id.expenseRadio -> TransactionType.EXPENSE
                    R.id.earningRadio -> TransactionType.EARNING
                    else -> TransactionType.EXPENSE
                }
                val imageUri = selectedImageUri?.toString() ?: ""

                if (type == TransactionType.EXPENSE) {
                    val newExpense = ExpensesItem(0, name, date, cost, category, note, imageUri)
                    db.expensesDao().insertExpense(newExpense)
                    startActivity(Intent(this@Transactions_Page, Expenses::class.java))
                } else {
                    val newEarning = EarningsItem(0, name, date, cost, category, note, imageUri)
                    db.earningsDao().insertEarning(newEarning)
                    startActivity(Intent(this@Transactions_Page, Earnings::class.java))
                }
            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == IMAGE_PICK_CODE && resultCode == RESULT_OK && data != null) {
                selectedImageUri = data.data
                imagePreview.setImageURI(selectedImageUri)
            }
        }
    }
}