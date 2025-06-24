package com.example.opsc6311_poe_cashflowx

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val budgetBtn = findViewById<Button>(R.id.budgetBtn)
        val transactionBtn = findViewById<Button>(R.id.transactionBtn)
        val earningsBtn = findViewById<Button>(R.id.earningsBtn)
        val expensesBtn = findViewById<Button>(R.id.expensesBtn)
        val calendarBtn = findViewById<Button>(R.id.calendarBtn)
        val reportBtn = findViewById<Button>(R.id.reportBtn)
        val settingsBtn = findViewById<Button>(R.id.settingsBtn)

        settingsBtn.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        val settingsBtn = findViewById<Button>(R.id.settingsBtn)

        settingsBtn.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        budgetBtn.setOnClickListener {
            val intent = Intent(this, Budget_Page::class.java)
            startActivity(intent)
        }

        transactionBtn.setOnClickListener {
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Transactions_Page::class.java)
            startActivity(intent)
        }

        earningsBtn.setOnClickListener {
            val intent = Intent(this, Earnings::class.java)
            startActivity(intent)
        }


        expensesBtn.setOnClickListener {
            val intent = Intent(this, Expenses::class.java)
            startActivity(intent)
        }

        calendarBtn.setOnClickListener {
            Toast.makeText(this, "Content is currently unavailable", Toast.LENGTH_SHORT).show()
        }

        reportBtn.setOnClickListener {
            Toast.makeText(this, "Content is currently unavailable", Toast.LENGTH_SHORT).show()
        }
    }
}
