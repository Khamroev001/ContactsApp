package com.example.contactsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.R
import com.example.contactsapp.model.Contact

class ContactAdapter(private var list: MutableList<Contact>, var contInterface: ContactInterface) :
    RecyclerView.Adapter<ContactAdapter.ContactHolder>() {

    class ContactHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var phone: TextView = itemView.findViewById(R.id.phone)
        var linearLayout: LinearLayout = itemView.findViewById(R.id.cont_lay)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        return ContactHolder(LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false))
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        var item = list[position]
        holder.name.text = item.name
        holder.phone.text = item.phone
        holder.linearLayout.setOnClickListener {
            contInterface.onClick(item)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    interface ContactInterface {
        fun onClick(contact: Contact) {}
    }
}