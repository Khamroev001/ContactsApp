package com.example.contactsapp.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.contactsapp.model.Contact

class DBHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        const val DB_NAME = "contact"
        const val DB_VERSION = 1
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val query = "create table 'contact'('id' integer PRIMARY KEY AUTOINCREMENT, 'name' TEXT NOT NULL, 'phone_number' INTEGER NOT NULL)"
        p0?.execSQL(query)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    fun addContact(contact: Contact): Long {
        val writableDb = this.writableDatabase
        val values = ContentValues().apply {
            put("name", contact.name)
            put("phone_number", contact.phone)
        }
        return writableDb.insert("contact", null, values)
    }

    fun getContacts(isAZ: Boolean = true): MutableList<Contact> {
        val contacts = mutableListOf<Contact>()
        val db = this.readableDatabase
        val cursor = db.query("contact", null, null, null, null, null, "name ${if (isAZ) "ASC" else "DESC"}")
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow("id"))
                val name = getString(getColumnIndexOrThrow("name"))
                val phone = getString(getColumnIndexOrThrow("phone_number"))
                contacts.add(Contact(id, name, phone))
            }
        }
        cursor.close()
        return contacts
    }

    @SuppressLint("Range")
    fun getContactsFilteredByName(searchText: String): MutableList<Contact> {
        val contacts = mutableListOf<Contact>()
        val selectQuery = "SELECT * FROM contact WHERE name LIKE '%$searchText%'"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val phone = cursor.getString(cursor.getColumnIndex("phone_number"))
                val contact = Contact(id, name, phone)
                contacts.add(contact)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return contacts
    }

    fun deleteAllContacts() {
        val writableDb = this.writableDatabase
        writableDb.delete("contact", null, null)
    }

    fun deleteContact(contact: Contact) {
        val writableDb = this.writableDatabase
        writableDb.delete("contact", "id = ?", arrayOf(contact.id.toString()))
    }
}
