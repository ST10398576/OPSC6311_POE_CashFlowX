package com.example.opsc6311_poe_cashflowx

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.opsc6311_poe_cashflowx.model.EarningsItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

class Transactions_Page : AppCompatActivity() {

    private lateinit var editName: EditText
    private lateinit var editDate: EditText
    private lateinit var editCategory: EditText
    private lateinit var editCost: EditText
    private lateinit var editNote: EditText
    private lateinit var editRadioGroup: RadioGroup
    private lateinit var imagePreview: ImageView
    private val IMAGE_PICK_CODE = 1001
    private var selectedImageUri: Uri? = null
    private val storageRef = FirebaseStorage.getInstance().reference

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

        findViewById<Button>(R.id.btnAdd).setOnClickListener {
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

            // Upload image if available
            if (selectedImageUri != null) {
                val fileName = UUID.randomUUID().toString()
                val imgRef = storageRef.child("transaction_images/$fileName")

                imgRef.putFile(selectedImageUri!!)
                    .addOnSuccessListener {
                        imgRef.downloadUrl.addOnSuccessListener { uri ->
                            saveTransactionToFirebase(type, name, date, cost, category, note, uri.toString())
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Image upload failed", Toast.LENGTH_SHORT).show()
                    }
            } else {
                saveTransactionToFirebase(type, name, date, cost, category, note, "")
            }
        }
    }

    private fun saveTransactionToFirebase(
        type: TransactionType,
        name: String,
        date: String,
        cost: Double,
        category: String,
        note: String,
        imageUrl: String
    ) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val dbRef = FirebaseDatabase.getInstance().reference

        if (type == TransactionType.EXPENSE) {
            val newExpense = ExpensesItem(0, name, date, cost, category, note, imageUrl)
            dbRef.child("Users").child(userId).child("Expenses").push().setValue(newExpense)
            startActivity(Intent(this, Expenses::class.java))
        } else {
            val newEarning = EarningsItem(0, name, date, cost, category, note, imageUrl)
            dbRef.child("Users").child(userId).child("Earnings").push().setValue(newEarning)
            startActivity(Intent(this, Earnings::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK_CODE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            imagePreview.setImageURI(selectedImageUri)
        }
    }
}
