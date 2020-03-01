package com.ore.contactapp.ui.singlecontact

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ore.contactapp.Contact

class SingleContactViewModel(contact: Contact) : ViewModel() {

    private val _contact = MutableLiveData<Contact>()
    val contact: LiveData<Contact>
        get() = _contact

    init {
        _contact.value = contact
    }

    fun callContact(view: View) {
        val phone = contact.value?.phone
        val context = view.context
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + Uri.encode(phone)))
        context.startActivity(intent)
    }

    fun emailContact(view: View) {
        val email = contact.value?.email
        val intent = Intent(Intent.ACTION_SEND)
        val context = view.context
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        try {
            context.startActivity(Intent.createChooser(intent, "Choose Email Client"))
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}