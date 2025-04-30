package com.fortune.app.ui.adapters.cards

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.R
import com.fortune.app.ui.view.app.CardDetailActivity

class CardAdapter(
    private val context: Context,
    private val cardItems: List<CardItem>
) : RecyclerView.Adapter<CardAdapter.CardViewHolder>() {
    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardTypeTextView: TextView = itemView.findViewById(R.id.card_type)
        val cardNumberHintTextView: TextView = itemView.findViewById(R.id.card_number_hint)
        val cardBalanceTextView: TextView = itemView.findViewById(R.id.card_balance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.card, parent, false)
        return CardViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val currentItem = cardItems[position]
        holder.cardTypeTextView.text = currentItem.cardType
        holder.cardNumberHintTextView.text = "*${currentItem.cardNumberHint.toString()}"
        holder.cardBalanceTextView.text = "${currentItem.cardBalance.toString()} €"

        holder.itemView.setOnClickListener {
            val openCardDetail = Intent(context, CardDetailActivity::class.java)
            openCardDetail.putExtra("card_uuid", cardItems.get(position).cardUUID)
            context.startActivity(openCardDetail)
        }
    }

    override fun getItemCount() = cardItems.size
}