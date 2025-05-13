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

class RequestedBizumAdapter(
    private val bizumItems: List<BizumItem>
) : RecyclerView.Adapter<RequestedBizumAdapter.RequestBizumHolder>() {
    inner class RequestBizumHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val date = itemView.findViewById<TextView>(R.id.request_bizum_date)
        val from = itemView.findViewById<TextView>(R.id.request_bizum_from)
        val desc = itemView.findViewById<TextView>(R.id.request_bizum_description)
        val amount = itemView.findViewById<TextView>(R.id.request_bizum_amount)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestBizumHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.request_bizum_layout, parent, false)
        return RequestBizumHolder(itemView)
    }

    override fun onBindViewHolder(holder: RequestBizumHolder, position: Int) {
        val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
        holder.date.text = sdf.format(bizumItems.get(position).date).uppercase()

        holder.from.text = bizumItems.get(position).from
        holder.desc.text = bizumItems.get(position).description

        holder.amount.text = if (bizumItems[position].amountIn) {
            bizumItems[position].amount.toString()
        } else {
            "-${bizumItems[position].amount}"
        }
    }

    override fun getItemCount() = bizumItems.size
}