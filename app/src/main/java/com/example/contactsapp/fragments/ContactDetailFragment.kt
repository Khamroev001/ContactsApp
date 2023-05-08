package com.example.contactsapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.contactsapp.Dialog
import com.example.contactsapp.databinding.FragmentContactDetailBinding
import com.example.contactsapp.model.Contact

class ContactDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContactDetailBinding.inflate(inflater, container, false)

        val contact = arguments?.getSerializable("contact") as Contact

        binding.name.text = contact.name
        binding.phone.text = contact.phone

        binding.delete.setOnClickListener {
            Dialog(contact).show(parentFragmentManager,"myDialog")
        }
        return binding.root
    }
}