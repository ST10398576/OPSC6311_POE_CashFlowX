package com.example.opsc6311_poe_cashflowx

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.opsc6311_poe_cashflowx.model.EarningsItem


class EarningsAdapter(private val earningsList: List<EarningsItem>) :
    RecyclerView.Adapter<EarningsAdapter.EarningsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EarningsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_earnings_item, parent, false)
        return EarningsViewHolder(view)
    }

    override fun onBindViewHolder(holder: EarningsViewHolder, position: Int) {
        val item = earningsList[position]
        holder.source.text = item.earnTitle
        holder.date.text = item.earnDate
        holder.amount.text = "R${item.earnAmount}"
        holder.category.text = item.earnCategory
        holder.note.text = item.earnNote

        // Load image using Glide
        if (item.earnImage.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(Uri.parse(item.earnImage)) // assuming expenImage contains a URI string
                .into(holder.image)
        }
    }

    override fun getItemCount() = earningsList.size

    class EarningsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val source: TextView = itemView.findViewById(R.id.tvTitle)
        val date: TextView = itemView.findViewById(R.id.tvDate)
        val amount: TextView = itemView.findViewById(R.id.tvAmount)
        val category: TextView = itemView.findViewById(R.id.tvCategory)
        val note: TextView = itemView.findViewById(R.id.tvNote)

        // ImageView to display the image
        val image: ImageView = itemView.findViewById(R.id.tvImage)

    }
}