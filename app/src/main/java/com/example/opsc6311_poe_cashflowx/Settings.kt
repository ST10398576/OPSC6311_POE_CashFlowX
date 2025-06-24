package com.example.opsc6311_poe_cashflowx

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Settings : AppCompatActivity() {

    private lateinit var oldPasswordEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var confirmButton: Button
    private lateinit var backButton: Button

    private lateinit var dbHelper: DBHelper
    private lateinit var currentUsername: String // You should pass this in via Intent or session

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        // Initialize views
        val oldPass = findViewById<EditText>(R.id.editTextText)
        val newPass = findViewById<EditText>(R.id.editTextText2)
        val confirmPass = findViewById<EditText>(R.id.editTextText3)
        val confirmBtn = findViewById<Button>(R.id.button2)

        val dbHelper = DBHelper(this)
        val username = intent.getStringExtra("username") ?: "" // Make sure to pass this when navigating

        confirmBtn.setOnClickListener {
            val oldPassword = oldPass.text.toString().trim()
            val newPassword = newPass.text.toString().trim()
            val confirmPassword = confirmPass.text.toString().trim()

            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
            } else if (newPassword != confirmPassword) {
                Toast.makeText(
                    this,
                    "New password and confirmation do not match",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val success = dbHelper.updatePassword(username, oldPassword, newPassword)
                if (success) {
                    Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Old password is incorrect", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // TODO: Replace this with actual logged-in username (e.g. from login or SharedPreferences)
        currentUsername = intent.getStringExtra("username") ?: ""

        confirmButton.setOnClickListener {
            val oldPassword = oldPasswordEditText.text.toString()
            val newPassword = newPasswordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (newPassword != confirmPassword) {
                Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val success = dbHelper.updatePassword(currentUsername, oldPassword, newPassword)
            if (success) {
                Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Old password is incorrect", Toast.LENGTH_SHORT).show()
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}
