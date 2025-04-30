package com.fortune.app.ui.adapters.cardMovements

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.R

class MovementCardAdapter(val movementItems: List<MovementCardItem>) : RecyclerView.Adapter<MovementCardAdapter.PaymentActivityHolder>(){
    inner class PaymentActivityHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mDate = itemView.findViewById<TextView>(R.id.movement_date)
        val mYear = itemView.findViewById<TextView>(R.id.movement_year)
        val mTitle = itemView.findViewById<TextView>(R.id.movement_title)
        val mAmount = itemView.findViewById<TextView>(R.id.movement_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentActivityHolder {
        val itemInflated = LayoutInflater.from(parent.context).inflate(
            R.layout.movement_activity_layout, parent, false
        )
        return PaymentActivityHolder(itemInflated)
    }

    override fun onBindViewHolder(holder: PaymentActivityHolder, position: Int) {
        val currentItem = movementItems[position]

        holder.mTitle.text = currentItem.mTitle

        val mDate = currentItem.mDate
        holder.mYear.text = mDate.year.toString()

        val dateStrStructure = "${mDate.dayOfMonth} ${mDate.month.toString().substring(0, 3).uppercase()} | ${mDate.dayOfWeek}"
        holder.mDate.text = dateStrStructure

        holder.mTitle.text = currentItem.mTitle
        holder.mAmount.text = currentItem.mAmount.toString() // Add logic to make backround green if amount was +
    }

    override fun getItemCount(): Int {
        return movementItems.size
    }
}