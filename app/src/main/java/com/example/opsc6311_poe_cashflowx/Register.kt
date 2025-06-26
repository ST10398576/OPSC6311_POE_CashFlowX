package com.example.opsc6311_poe_cashflowx

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Register : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var usernameInput : EditText
    private lateinit var emailInput : EditText
    private lateinit var passwordInput : EditText
    private lateinit var passwordConfirm : EditText
    private lateinit var registerButton : Button
    private lateinit var tvLoginRedirect: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        usernameInput = findViewById(R.id.etUsername)
        emailInput = findViewById(R.id.etEmail)
        passwordInput = findViewById(R.id.etPassword)
        passwordConfirm = findViewById(R.id.etConfirmPassword)
        registerButton = findViewById(R.id.btnRegister)
        tvLoginRedirect = findViewById(R.id.tvLoginRedirect)

        registerButton.setOnClickListener {
            val username = usernameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val confirmPassword = passwordConfirm.text.toString().trim()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Store user in Realtime Database
                        val uid = auth.currentUser?.uid
                        val ref = FirebaseDatabase.getInstance().getReference("users/$uid")
                        ref.setValue(mapOf("email" to email))

                        val intent = Intent(this, Registration_Success::class.java)
                        startActivity(intent)
                        finish()
                    } else {
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
