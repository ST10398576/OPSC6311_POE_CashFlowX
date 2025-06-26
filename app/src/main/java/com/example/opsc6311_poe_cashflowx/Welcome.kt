package com.example.opsc6311_poe_cashflowx

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import android.content.Intent
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth


class Welcome : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnNext: Button
    private lateinit var tvRegisterRedirect: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        auth = FirebaseAuth.getInstance()
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        btnNext = findViewById(R.id.btnNext)
        tvRegisterRedirect = findViewById(R.id.tvRegisterRedirect)

        btnNext.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Menu::class.java)
                        intent.putExtra("userId", auth.currentUser?.uid)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Login failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

        }

        tvRegisterRedirect.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }
    }
}