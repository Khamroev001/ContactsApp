package com.example.contactsapp.model

import com.example.contactsapp.R

data class Contact(var id: Int = 0, var name: String, var phone: String, var img: Int = R.drawable.account) : java.io.Serializable
