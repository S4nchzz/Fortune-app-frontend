package com.fortune.app.ui.adapters.cards

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.DrawableContainer.DrawableContainerState
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.R
import com.fortune.app.ui.adapters.bizums.BizumItem
import com.fortune.app.ui.view.app.BizumRequestAcceptOrDeny
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
        val ll = itemView.findViewById<LinearLayout>(R.id.request_bizum_ll)
        val context = itemView.context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestBizumHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.request_bizum_layout, parent, false)
        return RequestBizumHolder(itemView)
    }

    override fun onBindViewHolder(holder: RequestBizumHolder, position: Int) {
        holder.ll.setOnClickListener {
            val openRequestBizumSendOrDeny = Intent(holder.context, BizumRequestAcceptOrDeny::class.java)

            openRequestBizumSendOrDeny.putExtra("bizumID", bizumItems[position].id)

            val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
            openRequestBizumSendOrDeny.putExtra("date", sdf.format(bizumItems[position].date).uppercase())

            openRequestBizumSendOrDeny.putExtra("from", bizumItems[position].from)
            openRequestBizumSendOrDeny.putExtra("description", bizumItems[position].description)
            openRequestBizumSendOrDeny.putExtra("amount", bizumItems[position].amount)

            holder.context.startActivity(openRequestBizumSendOrDeny)

            if (holder.context is Activity) {
                holder.context.finish()
            }
        }

        val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
        holder.date.text = sdf.format(bizumItems.get(position).date).uppercase()

        holder.from.text = bizumItems.get(position).from
        holder.desc.text = bizumItems.get(position).description

        holder.amount.text = "${bizumItems[position].amount} €"
    }

    override fun getItemCount() = bizumItems.size
}