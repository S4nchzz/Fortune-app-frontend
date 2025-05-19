package com.fortune.app.ui.adapters.cards

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.R
import java.text.NumberFormat
import java.util.Locale

class CarouselAdapter(
    private val context: Context,
    private val cardItems: List<CardItem>,
    private val callback: (String) -> Unit
) : RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder>() {
    inner class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardTypeTextView: TextView = itemView.findViewById(R.id.card_type)
        val cardNumberHintTextView: TextView = itemView.findViewById(R.id.card_number_hint)
        val cardBalanceTextView: TextView = itemView.findViewById(R.id.card_balance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card, parent, false)
        return CarouselViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val currentItem = cardItems[position]
        holder.cardTypeTextView.text = currentItem.cardType
        holder.cardNumberHintTextView.text = "*${currentItem.cardNumberHint}"

        val formattedBalance = NumberFormat.getCurrencyInstance(Locale("es", "ES"))
            .format(currentItem.cardBalance)
            .replace("€", " €")

        holder.cardBalanceTextView.text = "${formattedBalance}"

        holder.itemView.setOnClickListener {
            callback(cardItems.get(position).cardUUID)
        }
    }

    override fun getItemCount() = cardItems.size
}