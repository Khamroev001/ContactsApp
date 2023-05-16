package com.example.contactsapp.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.contactsapp.Dialog
import com.example.contactsapp.R
import com.example.contactsapp.databinding.FragmentContactDetailBinding
import com.example.contactsapp.model.Contact

class ContactDetailFragment : Fragment() {
    lateinit var contact:Contact
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContactDetailBinding.inflate(inflater, container, false)

      contact = arguments?.getSerializable("contact") as Contact

        binding.name.text = contact.name
        binding.phone.text = "+"+contact.phone

        binding.delete.setOnClickListener {
            Dialog(contact).show(parentFragmentManager,"myDialog")
        }
        binding.call.setOnClickListener {
            makePhoneCall()
        }
        binding.edit.setOnClickListener {
            val bundle = bundleOf()
            bundle.putSerializable("contact", contact)
            findNavController().navigate(R.id.action_viewFragment_to_editFragment,bundle)
        }
        return binding.root
    }

    private fun makePhoneCall() {
        val phoneNumber = "+"+contact.phone

        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$phoneNumber")
        startActivity(intent)
    }
}