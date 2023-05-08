package com.example.contactsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.contactsapp.databinding.FragmentAddContactBinding
import com.example.contactsapp.model.Contact
import com.example.contactsapp.utils.DBHelper

class AddContactFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val myDb = DBHelper(requireContext())
        val binding = FragmentAddContactBinding.inflate(inflater, container, false)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.addCheck.setOnClickListener {
            if (binding.name.text.toString().isNotEmpty() && binding.phonenumber.text.toString().isNotEmpty()) {
                myDb.addContact(Contact(
                    name = binding.name.text.toString(),
                    phone = binding.phonenumber.text.toString()
                ))
                Toast.makeText(requireContext(), "New contact has been added", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Phone number or name is empty", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }
}