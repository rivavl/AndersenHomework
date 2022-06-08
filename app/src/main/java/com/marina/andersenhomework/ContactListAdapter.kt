package com.marina.andersenhomework

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.marina.andersenhomework.entity.Contact

class ContactListAdapter : ListAdapter<Contact, ContactViewHolder>(ContactDiffCallback()) {

    var onContactLongClickListener: ((Contact) -> Unit)? = null
    var onContactClickListener: ((Contact) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val layout = R.layout.rv_item
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = getItem(position)

        holder.view.setOnLongClickListener {
            onContactLongClickListener?.invoke(contact)
            true
        }

        holder.view.setOnClickListener {
            onContactClickListener?.invoke(contact)
        }

        holder.name.text = contact.name
        holder.number.text = contact.number
        loadImage(contact.photoUrl, holder.photoUrl, holder.view.context)
    }

    private fun loadImage(url: String, imageView: ImageView, context: Context) {
        Glide.with(context)
            .load(url)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(imageView)
    }
}