package com.example.opsc6311_poe_cashflowx

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge


class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        val dbHelper = DBHelper(this)

        val usernameInput = findViewById<EditText>(R.id.etUsername)
        val emailInput = findViewById<EditText>(R.id.etEmail)
        val passwordInput = findViewById<EditText>(R.id.etPassword)
        val registerButton = findViewById<Button>(R.id.btnRegister)
        val tvLoginRedirect = findViewById<TextView>(R.id.tvLoginRedirect)


        registerButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
            } else if (dbHelper.checkUserExists(email)) {
                Toast.makeText(this, "Email has already registered", Toast.LENGTH_SHORT).show()
            } else {
                val success = dbHelper.registerUser(username, email, password)
                if (success) {
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Registration_Success::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Registration_Fail::class.java)
                    startActivity(intent)
                }
            }
        }

        tvLoginRedirect.setOnClickListener {
            val intent = Intent(this, Welcome::class.java)
            startActivity(intent)
        }
    }
}
