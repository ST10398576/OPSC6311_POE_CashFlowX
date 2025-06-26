package com.example.opsc6311_poe_cashflowx

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Context
import com.example.opsc6311_poe_cashflowx.databinding.ActivityAchievementsBinding

class AchievementsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAchievementsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievementsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadBadges()
    }

    private fun loadBadges() {
        val prefs = getSharedPreferences("badges", Context.MODE_PRIVATE)
        S
        val logged7Days = prefs.getBoolean("logged7Days", false)
        val metMonthlyGoal = prefs.getBoolean("metMonthlyGoal", false)

        binding.badgeLogged7Days.setImageResource(
            if (logged7Days) R.drawable.badge_logged else R.drawable.badge_locked
        )

        binding.badgeGoalMet.setImageResource(
            if (metMonthlyGoal) R.drawable.badge_goal else R.drawable.badge_locked
        )
    }
}