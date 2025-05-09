package com.fortune.app.ui.adapters.cardMovements

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.R
import java.text.NumberFormat
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.Date
import java.util.Locale

class MovementCardAdapter(val movementItems: List<MovementCardItem>) : RecyclerView.Adapter<MovementCardAdapter.PaymentActivityHolder>(){
    inner class PaymentActivityHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mDate = itemView.findViewById<TextView>(R.id.movement_date)
        val mYear = itemView.findViewById<TextView>(R.id.movement_year)
        val mReceiver = itemView.findViewById<TextView>(R.id.movement_title)
        val mAmount = itemView.findViewById<TextView>(R.id.movement_amount)

        val container = itemView.findViewById<LinearLayout>(R.id.item_movement_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentActivityHolder {
        val itemInflated = LayoutInflater.from(parent.context).inflate(
            R.layout.movement_activity_layout, parent, false
        )
        return PaymentActivityHolder(itemInflated)
    }

    override fun onBindViewHolder(holder: PaymentActivityHolder, position: Int) {
        val currentItem = movementItems[position]

        holder.container.tooltipText = "${currentItem.mReceiver} | ${currentItem.mAmount}"
        holder.mReceiver.text = currentItem.mReceiver

        val mDate = currentItem.mDate
        holder.mYear.text = mDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().year.toString()

        val dateStrStructure = formatDateStructure(mDate)
        holder.mDate.text = dateStrStructure

        holder.mReceiver.text = currentItem.mReceiver

        val formattedBalance = NumberFormat.getCurrencyInstance(Locale("es", "ES"))
            .format(currentItem.mAmount.toDouble())
            .replace("€", " €")

        holder.mAmount.text = formattedBalance
    }

    fun formatDateStructure(date: Date): String {
        val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

        val dayOfMonth = localDate.dayOfMonth
        val monthAbbr = localDate.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).uppercase()
        val dayOfWeek = localDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)

        return "$dayOfMonth $monthAbbr | $dayOfWeek"
    }

    override fun getItemCount(): Int {
        return movementItems.size
    }
}