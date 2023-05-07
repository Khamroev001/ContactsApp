package com.example.contactsapp.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.example.contactsapp.DeleteAllDialog
import com.example.contactsapp.R
import com.example.contactsapp.adapters.ContactAdapter
import com.example.contactsapp.databinding.FragmentContactsBinding
import com.example.contactsapp.model.Contact
import com.example.contactsapp.utils.DBHelper

class ContactsFragment : Fragment() {
    private var contacts = mutableListOf<Contact>()
    private lateinit var binding: FragmentContactsBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater, container, false)
        // DB
        val myDb = DBHelper(requireContext())
        contacts = myDb.getContacts()

        if (contacts.isEmpty()) {
            binding.box.visibility = View.VISIBLE
        } else {
            binding.contactRv.adapter = allContactsAdapter()
        }

        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_contactsFragment_to_addContactFragment)
        }

        binding.search.doOnTextChanged { text, start, before, count ->
            if (count == 0) {
                binding.search.visibility = View.GONE
                binding.toolbar.visibility = View.VISIBLE
                binding.search.clearFocus()
                val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.search.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)

                binding.contactRv.adapter = allContactsAdapter()
            } else {
                var filteredContacts: MutableList<Contact> = myDb.getContactsFilteredByName(text.toString())
                var adapter = ContactAdapter(filteredContacts, object : ContactAdapter.ContactInterface {
                    override fun onClick(contact: Contact) {
                        val bundle = bundleOf()
                        bundle.putSerializable("contact", contact)
                        findNavController().navigate(R.id.action_contactsFragment_to_viewFragment, bundle)
                    }
                })
                binding.contactRv.adapter = adapter
            }
        }

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.search_menu -> {
                    binding.search.visibility = View.VISIBLE
                    binding.toolbar.visibility = View.INVISIBLE
                    binding.search.isFocusable = true
                    binding.search.requestFocus()

                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(binding.search, InputMethodManager.SHOW_IMPLICIT)

                    true
                }
                R.id.az -> {
                    binding.contactRv.adapter = allContactsAdapter(true)
                    true
                }

                R.id.za -> {
                    binding.contactRv.adapter = allContactsAdapter(false)
                    true
                }
                R.id.delete -> {
                    DeleteAllDialog().show(parentFragmentManager,"myDeleteAllDialog")
                    binding.contactRv.adapter = allContactsAdapter()
                    true
                }
                else -> false
            }
        }

        return binding.root
    }

    private fun allContactsAdapter(isAz: Boolean = true): ContactAdapter {
        return ContactAdapter(DBHelper(requireContext()).getContacts(isAz), object : ContactAdapter.ContactInterface {
            override fun onClick(contact: Contact) {
                val bundle = bundleOf()
                bundle.putSerializable("contact", contact)
                findNavController().navigate(R.id.action_contactsFragment_to_viewFragment, bundle)
            }
        })
    }
}