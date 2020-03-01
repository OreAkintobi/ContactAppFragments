package com.ore.contactapp.ui.singlecontact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ore.contactapp.Contact

class SingleContactViewModelFactory(private val contact: Contact) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingleContactViewModel::class.java)) {
            return SingleContactViewModel(contact) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}