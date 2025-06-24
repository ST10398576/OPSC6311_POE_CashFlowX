package com.example.cashflowx

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BudgetCategoryAdapter(private val categories: List<BudgetCategoryDisplay>) :
    RecyclerView.Adapter<BudgetCategoryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameText: TextView = view.findViewById(R.id.textCategoryName)
        val goalRangeText: TextView = view.findViewById(R.id.textGoalRange)
        val spentText: TextView = view.findViewById(R.id.textActualSpent)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_budget_category, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = categories[position]
        holder.nameText.text = item.name
        holder.goalRangeText.text = "Goal: R${item.minGoal} - R${item.maxGoal}"
        holder.spentText.text = "Spent: R${item.actualSpent}"

        // Color logic for status
        val color = when {
            item.actualSpent > item.maxGoal -> Color.parseColor("#FFCDD2") // Light Red
            item.actualSpent >= item.minGoal -> Color.parseColor("#FFF9C4") // Light Yellow
            else -> Color.parseColor("#C8E6C9") // Light Green
        }

        holder.itemView.setBackgroundColor(color)
    }
}