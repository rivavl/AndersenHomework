package com.marina.andersenhomework

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    val name = view.findViewById<TextView>(R.id.contact_name)
    val number = view.findViewById<TextView>(R.id.contact_number)
    val photoUrl = view.findViewById<ImageView>(R.id.contact_photo)
}