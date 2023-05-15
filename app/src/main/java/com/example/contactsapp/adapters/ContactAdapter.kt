package com.example.contactsapp.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.R
import com.example.contactsapp.model.Contact

class ContactAdapter(private var list: MutableList<Contact>, var contInterface: ContactInterface) :
    RecyclerView.Adapter<ContactAdapter.ContactHolder>() {

    class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var phone: TextView = itemView.findViewById(R.id.phone)
        var call: ImageView = itemView.findViewById(R.id.call_item)
        var linearLayout: LinearLayout = itemView.findViewById(R.id.cont_lay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        return ContactHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        var item = list[position]
        holder.name.text = item.name
        holder.phone.text = "+"+item.phone
        holder.linearLayout.setOnClickListener {
            contInterface.onClick(item)
        }
        holder.call.setOnClickListener {
           contInterface.callonClick(item)
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ContactInterface {
        fun callonClick(contact: Contact){}
        fun onClick(contact: Contact) {}
    }
}