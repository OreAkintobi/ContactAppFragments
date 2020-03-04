package com.ore.contactapp.ui.addcontact


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.ore.contactapp.Contact
import com.ore.contactapp.database.ContactDatabase
import com.ore.loginsignupui.R
import com.ore.loginsignupui.databinding.FragmentAddContactBinding
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.fragment_add_contact.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

@Suppress("DeferredResultUnused")
class FragmentAddContact : Fragment() {
    private lateinit var binding: FragmentAddContactBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_contact, container, false)
        val dataSource = ContactDatabase.getInstance(requireContext()).contactDatabaseDao
        val firestoreDB = FirebaseFirestore.getInstance()

        binding.contact = Contact(
            binding.name.text.toString(),
            binding.email.text.toString(),
            binding.phone.text.toString()
        )
        binding.lifecycleOwner = this

        binding.addContactButton.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val phone = binding.phone.text.toString().trim()
            val contact = Contact(name, email, phone)
            if (saveContact()) {
                firestoreDB.collection("Contacts").add(contact).addOnCompleteListener {
                    contact.contactDbId = it.result?.id.toString()
                    Log.e("contactDbId", contact.contactDbId)
                    GlobalScope.async(Dispatchers.IO) {
                        dataSource.insertContact(contact)
                    }
                }.addOnFailureListener {
                    it.stackTrace
                    Toast.makeText(
                        context,
                        "This Operation didn't work because ${it.stackTrace}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                view!!.findNavController().navigate(
                    FragmentAddContactDirections.actionFragmentAddContactToFragmentAllContacts2()
                )
            } else {
                Toast.makeText(context, "This Operation didn't Work", Toast.LENGTH_LONG).show()
            }

        }
        return binding.root
    }

    private fun saveContact(): Boolean {
        if (
            binding.phone.validator()
                .validNumber()
                .startsWith("0")
                .addErrorCallback {
                    Toast.makeText(context, "Please Enter a Valid Phone Number", Toast.LENGTH_LONG)
                        .show()
                    binding.phone.setText("")
                }.check() && binding.email.validator()
                .validEmail()
                .nonEmpty()
                .addErrorCallback {
                    Toast.makeText(context, "Please Enter a Valid Email", Toast.LENGTH_LONG).show()
                    email.setText("")
                }.check() && binding.name.nonEmpty {
                Toast.makeText(context, "Please Enter a Name", Toast.LENGTH_LONG).show()
                name.setText("")
            }
        ) {
            Toast.makeText(
                context,
                "Name: ${binding.name.text} \nEmail: ${binding.email.text} \nPhone Number: ${binding.phone.text}",
                Toast.LENGTH_SHORT
            ).show()
            return true
        } else {
            return false
        }
    }
}
