package com.example.opsc6311_poe_cashflowx

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

// Example:
val auth = Firebase.auth

class Onboarding : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboarding)

        val btn_register = findViewById<Button>(R.id.btn_register)
        val btn_sign_in = findViewById<Button>(R.id.btn_sign_in)

        btn_register.setOnClickListener{
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        btn_sign_in.setOnClickListener{
            val intent = Intent(this, Welcome::class.java)
            startActivity(intent)
        }
    }
}
