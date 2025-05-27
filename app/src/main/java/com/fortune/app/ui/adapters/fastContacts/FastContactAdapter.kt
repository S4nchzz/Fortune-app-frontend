package com.fortune.app.ui.adapters.fastContacts

import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.fortune.app.R
import com.fortune.app.ui.view.app.FastContactOperate


class FastContactAdapter(
    private val fastContactList: List<FastContactItem>
) : RecyclerView.Adapter<FastContactAdapter.FastContactViewHolder>() {
    inner class FastContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pfp = itemView.findViewById<ImageView>(R.id.rview_item_pfp)
        val name = itemView.findViewById<TextView>(R.id.rview_item_name)
        val layout = itemView.findViewById<LinearLayout>(R.id.item_layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FastContactViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fast_contact_layout, parent, false)
        return FastContactViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FastContactViewHolder, position: Int) {
        val base64Image = fastContactList[position].pfp

        if (!base64Image.isNullOrBlank()) {
            try {
                val cleanBase64 = if (base64Image.contains(",")) {
                    base64Image.substringAfter(",")
                } else base64Image

                val decodedBytes = Base64.decode(cleanBase64, Base64.DEFAULT)
                val bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
                holder.pfp.setImageBitmap(bitmap)
            } catch (e: Exception) {
                e.printStackTrace()
                holder.pfp.setImageResource(R.drawable.profile_default_profile_icon)
            }
        } else {
            holder.pfp.setImageResource(R.drawable.profile_default_profile_icon)
        }

        holder.name.text = fastContactList[position].name

        holder.layout.setOnClickListener {
            val context = holder.layout.context
            val openFastContactOperate = Intent(context, FastContactOperate::class.java)
            openFastContactOperate.putExtra("to_id", fastContactList[position].to_id)
            context.startActivity(openFastContactOperate)
        }
    }

    override fun getItemCount() = fastContactList.size
}