package com.example.contactsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.contactsapp.R
import com.example.contactsapp.databinding.FragmentEditBinding
import com.example.contactsapp.model.Contact
import com.example.contactsapp.utils.DBHelper

class EditFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myDb = DBHelper(requireContext())
        val binding = FragmentEditBinding.inflate(inflater, container, false)
        var contact = arguments?.getSerializable("contact") as Contact

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.name.hint = contact.name
        binding.phonenumber.hint = "+"+contact.phone

        binding.addCheck.setOnClickListener {
            if (binding.name.text.toString() != "" && binding.phonenumber.text.toString() != "") {
                myDb.editContact(contact.id, binding.name.text.toString(), binding.phonenumber.text.toString())
            }
            if (binding.name.text.toString() == "" && binding.phonenumber.text.toString() != "") {
                myDb.editContact(contact.id, binding.name.hint.toString(), binding.phonenumber.text.toString())
            }
            if (binding.name.text.toString() != "" && binding.phonenumber.text.toString() == "") {
                myDb.editContact(contact.id, binding.name.text.toString(), binding.phonenumber.hint.toString())
            }

            Toast.makeText(requireContext(), "Contact has been changed", Toast.LENGTH_SHORT).show()

            findNavController().popBackStack()
            findNavController().popBackStack()

        }

        return binding.root
    }
}