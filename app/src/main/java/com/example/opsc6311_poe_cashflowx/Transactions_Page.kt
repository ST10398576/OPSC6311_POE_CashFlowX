package com.example.opsc6311_poe_cashflowx

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class Transactions_Page : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editDate: EditText
    private lateinit var editCategory: EditText
    private lateinit var editCost: EditText
    private lateinit var editNote: EditText
    private lateinit var imagePreview: ImageView
    private lateinit var btnAddImage: Button
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnAdd: Button
    private lateinit var btnClear: Button

    private var imageUri: Uri? = null
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transactions)

        // Link UI elements
        editName = findViewById(R.id.editName)
        editDate = findViewById(R.id.editDate)
        editCategory = findViewById(R.id.editCategory)
        editCost = findViewById(R.id.editCost)
        editNote = findViewById(R.id.editNote)
        imagePreview = findViewById(R.id.imagePreview)
        btnAddImage = findViewById(R.id.btnAddImage)
        radioGroup = findViewById(R.id.editRadioGroup)
        btnAdd = findViewById(R.id.btnAdd)
        btnClear = findViewById(R.id.btnClear)

        btnAddImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        btnAdd.setOnClickListener {
            saveTransaction()
        }

        btnClear.setOnClickListener {
            clearForm()
        }
    }

    private fun saveTransaction() {
        val name = editName.text.toString().trim()
        val date = editDate.text.toString().trim()
        val category = editCategory.text.toString().trim()
        val cost = editCost.text.toString().trim()
        val note = editNote.text.toString().trim()
        val selectedId = radioGroup.checkedRadioButtonId

        if (name.isEmpty() || date.isEmpty() || category.isEmpty() || cost.isEmpty() || selectedId == -1) {
            Toast.makeText(this, "Please fill in all required fields", Toast.LENGTH_SHORT).show()
            return
        }

        val type = findViewById<RadioButton>(selectedId).text.toString()
        val amount = cost.toDoubleOrNull()
        if (amount == null) {
            Toast.makeText(this, "Invalid cost", Toast.LENGTH_SHORT).show()
            return
        }

        val id = UUID.randomUUID().toString()
        val imageUrl = imageUri?.toString() ?: ""

        if (type == "Expense") {
            TransactionManager.addExpense(id, name, date, amount, category, note, imageUrl)
            Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show()
        } else {
            TransactionManager.addEarning(id, name, date, amount, category, note, imageUrl)
            Toast.makeText(this, "Earning added", Toast.LENGTH_SHORT).show()
        }

        clearForm()
    }

    private fun clearForm() {
        editName.text.clear()
        editDate.text.clear()
        editCategory.text.clear()
        editCost.text.clear()
        editNote.text.clear()
        radioGroup.clearCheck()
        imagePreview.setImageURI(null)
        imageUri = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            imageUri = data?.data
            imagePreview.setImageURI(imageUri)
        }
    }
}
