package com.example.opsc6311_poe_cashflowx

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Menu : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Firebase Setup
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            // If no user is signed in, redirect to login
            startActivity(Intent(this, Welcome::class.java))
            finish()
            return
        }

        val uid = currentUser.uid
        databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(uid)


        val budgetBtn = findViewById<Button>(R.id.budgetBtn)
        val transactionBtn = findViewById<Button>(R.id.transactionBtn)
        val earningsBtn = findViewById<Button>(R.id.earningsBtn)
        val expensesBtn = findViewById<Button>(R.id.expensesBtn)
        val calendarBtn = findViewById<Button>(R.id.calendarBtn)
        val reportBtn = findViewById<Button>(R.id.reportBtn)
        //val settingsBtn = findViewById<Button>(R.id.settingsBtn)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Already on Menu/Home page
                    true
                }
                R.id.nav_settings -> {
                    startActivity(Intent(this, Settings::class.java))
                    true
                }
                else -> false
            }
        }


        databaseRef.child("username").get().addOnSuccessListener {
        val username = it.value?.toString() ?: "Unknown"
        }

        budgetBtn.setOnClickListener {
            val intent = Intent(this, Budget_Page::class.java)
            startActivity(intent)
        }

        transactionBtn.setOnClickListener {
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
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        reportBtn.setOnClickListener {
            val intent = Intent(this, GraphActivity::class.java)
            startActivity(intent)
        }
    }
}