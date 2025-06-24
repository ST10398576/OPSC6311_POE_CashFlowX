package com.example.cashflowx.adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cashflowx.R
import com.example.cashflowx.model.ExpensesItem
import com.bumptech.glide.Glide // For loading images using Glide

class ExpensesAdapter(private val expensesList: List<ExpensesItem>) :
    RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpensesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_expenses_item, parent, false)
        return ExpensesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpensesViewHolder, position: Int) {
        val item = expensesList[position]
        holder.source.text = item.expenTitle
        holder.date.text = item.expenDate
        holder.amount.text = "R${item.expenAmount}"
        holder.category.text = item.expenCategory
        holder.note.text = item.expenNote

        // Load image using Glide
        if (item.expenImage.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(Uri.parse(item.expenImage)) // assuming expenImage contains a URI string
                .into(holder.image)
        }
    }

    override fun getItemCount() = expensesList.size

    class ExpensesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val source: TextView = itemView.findViewById(R.id.tvTitle)
        val date: TextView = itemView.findViewById(R.id.tvDate)
        val amount: TextView = itemView.findViewById(R.id.tvAmount)
        val category: TextView = itemView.findViewById(R.id.tvCategory)
        val note: TextView = itemView.findViewById(R.id.tvNote)

        // ImageView to display the image
        val image: ImageView = itemView.findViewById(R.id.tvImage)
    }
}