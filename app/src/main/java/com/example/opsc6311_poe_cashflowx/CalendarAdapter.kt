package com.example.opsc6311_poe_cashflowx

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter(
    private val daysOfMonth: List<String>,
    private val onItemListener: OnItemListener
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    inner class CalendarViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val dayOfMonth: TextView = view.findViewById(R.id.cellDayText)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemListener.onItemClick(adapterPosition, dayOfMonth.text.toString())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calendar_cell, parent, false)
        view.layoutParams.height = (parent.height * 0.166666666).toInt()
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayOfMonth.text = daysOfMonth[position]
    }

    override fun getItemCount(): Int = daysOfMonth.size

    interface OnItemListener {
        fun onItemClick(position: Int, dayText: String)
    }
}
