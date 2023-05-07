package com.example.contactsapp

import android.R.attr.phoneNumber
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
import java.util.regex.Pattern


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddContactFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var mydb=DBHelper(requireContext())
        val binding = FragmentAddContactBinding.inflate(inflater, container, false)
        binding.back.setOnClickListener {
            findNavController().navigate(R.id.action_addContactFragment_to_contactsFragment)
        }

        binding.addCheck.setOnClickListener {
            val pattern: Pattern = Pattern.compile("^\\+\\d{10}$")
            if (binding.name.text.toString().isNotEmpty()&&binding.phonenumber.text.toString().isNotEmpty()){
                    mydb.addContact(Contact(binding.name.text.toString(),binding.phonenumber.text.toString()))
                    Toast.makeText(
                        requireContext(),
                        "New contact has been added",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                // the phone number is null or empty
                // show a toast message to the user
                Toast.makeText(requireContext(), "Phone number or name is empty", Toast.LENGTH_SHORT).show();
            }
        }

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddContactFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}