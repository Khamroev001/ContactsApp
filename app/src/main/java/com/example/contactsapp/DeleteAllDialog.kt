package com.example.contactsapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.contactsapp.utils.DBHelper
import kotlinx.coroutines.awaitCancellation

class DeleteAllDialog() : DialogFragment() {
    @SuppressLint("ResourceType")
    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("Delete everything?")
                .setMessage("Are you sure you want to remove everything")
                .setCancelable(true)
                .setPositiveButton("Yes") { _, _ ->
                    DBHelper(requireContext()).deleteAllContacts()
                    dismiss()
                    requireActivity().recreate()
                    findNavController().popBackStack()
                }
                .setNegativeButton("No") { _, _ -> }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}