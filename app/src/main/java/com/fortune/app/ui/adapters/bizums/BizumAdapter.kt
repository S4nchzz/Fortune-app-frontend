package com.fortune.app.ui.adapters.cards

import android.content.Context
import android.content.Intent
import android.graphics.drawable.DrawableContainer.DrawableContainerState
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.R
import com.fortune.app.ui.adapters.bizums.BizumItem
import com.fortune.app.ui.view.app.CardDetailActivity
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class BizumAdapter(
    private val bizumItems: List<BizumItem>
) : RecyclerView.Adapter<BizumAdapter.BizumCardHolder>() {
    inner class BizumCardHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bizumDate = itemView.findViewById<TextView>(R.id.bizum_date)
        val bizumTo = itemView.findViewById<TextView>(R.id.bizum_to)
        val bizumDescription = itemView.findViewById<TextView>(R.id.bizum_description)
        val bizumInOutImage = itemView.findViewById<ImageView>(R.id.bizum_in_out)
        val bizumAmount = itemView.findViewById<TextView>(R.id.bizum_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BizumCardHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.recent_bizum_layout, parent, false)
        return BizumCardHolder(itemView)
    }

    override fun onBindViewHolder(holder: BizumCardHolder, position: Int) {
        val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
        holder.bizumDate.text = sdf.format(bizumItems.get(position).date).uppercase()
        
        holder.bizumTo.text = bizumItems.get(position).to
        holder.bizumDescription.text = bizumItems.get(position).description

        holder.bizumAmount.text = if (bizumItems[position].amountIn) {
            bizumItems[position].amount.toString()
        } else {
            "-${bizumItems[position].amount}"
        }

        val drawableRes = if (bizumItems[position].amountIn) {
            R.drawable.in_bizum
        } else {
            R.drawable.out_bizum
        }

        holder.bizumInOutImage.setImageResource(drawableRes)
    }

    override fun getItemCount() = bizumItems.size
}