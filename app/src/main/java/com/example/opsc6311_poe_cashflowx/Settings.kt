package com.example.opsc6311_poe_cashflowx

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class Settings : AppCompatActivity() {

    private lateinit var dbHelper: DBHelper
    private lateinit var currentUsername: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        // Get the username from intent
        currentUsername = intent.getStringExtra("username") ?: ""

        dbHelper = DBHelper(this)

        val oldPasswordEditText = findViewById<EditText>(R.id.editTextText)
        val newPasswordEditText = findViewById<EditText>(R.id.editTextText2)
        val confirmPasswordEditText = findViewById<EditText>(R.id.editTextText3)
        val confirmButton = findViewById<Button>(R.id.ConfirmNewPassword)
        val logOutBtn = findViewById<Button>(R.id.LogOutBtn)

        confirmButton.setOnClickListener {
            val oldPassword = oldPasswordEditText.text.toString().trim()
            val newPassword = newPasswordEditText.text.toString().trim()
            val confirmPassword = confirmPasswordEditText.text.toString().trim()

            if (oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

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

        val logoutBtn = findViewById<Button>(R.id.LogOutBtn)
        logoutBtn.setOnClickListener {
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { _, _ ->
                    // No shared preference clearing needed if you're not storing sessions
                    val intent = Intent(this, Onboarding::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}
