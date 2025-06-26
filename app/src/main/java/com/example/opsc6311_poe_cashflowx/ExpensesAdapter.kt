package com.example.opsc6311_poe_cashflowx

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.opsc6311_poe_cashflowx.model.ExpensesItem

class ExpensesAdapter(private val expenses: List<ExpensesItem>) :
    RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder>() {

    inner class ExpensesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.tvTitle)
        val amount: TextView = view.findViewById(R.id.tvAmount)
        val date: TextView = view.findViewById(R.id.tvDate)
        val category: TextView = view.findViewById(R.id.tvCategory)
        val notes: TextView = view.findViewById(R.id.tvNote)
        val image: ImageView = view.findViewById(R.id.tvImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_expenses_item, parent, false)
        return ExpensesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        val item = expenses[position]
        holder.title.text = item.title
        holder.amount.text = "R${item.amount}"
        holder.date.text = item.date
        holder.category.text = item.category
        holder.notes.text = item.notes

        if (item.imageUrl.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(item.imageUrl)
                .into(holder.image)
        } else {
            holder.image.setImageResource(R.drawable.background_gradient) // optional fallback
        }
    }

    override fun getItemCount(): Int = expenses.size
}
