package com.example.opsc6311_poe_cashflowx

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth

class Settings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        val oldPasswordField = findViewById<EditText>(R.id.txOldPassword)
        val newPasswordField = findViewById<EditText>(R.id.txNewPassword)
        val confirmPasswordField = findViewById<EditText>(R.id.txConfirmNewPassword)
        val confirmButton = findViewById<Button>(R.id.ConfirmNewPassword)

        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        confirmButton.setOnClickListener {
            val oldPassword = oldPasswordField.text.toString().trim()
            val newPassword = newPasswordField.text.toString().trim()
            val confirmPassword = confirmPasswordField.text.toString().trim()

            if (newPassword != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (user != null && user.email != null) {
                val credential = EmailAuthProvider.getCredential(user.email!!, oldPassword)

                // Reauthenticate
                user.reauthenticate(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Now change password
                        user.updatePassword(newPassword).addOnCompleteListener { updateTask ->
                            if (updateTask.isSuccessful) {
                                Toast.makeText(this, "Password updated successfully", Toast.LENGTH_SHORT).show()
                                oldPasswordField.text.clear()
                                newPasswordField.text.clear()
                                confirmPasswordField.text.clear()
                            } else {
                                Toast.makeText(this, "Failed to update password", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Re-authentication failed. Wrong old password?", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        val logoutBtn = findViewById<Button>(R.id.LogOutBtn)
        logoutBtn.setOnClickListener {
            androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { _, _ ->
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, Onboarding::class.java))
                    finish()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}
