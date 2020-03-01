package com.ore.contactapp.ui.allcontacts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.ore.contactapp.Contact
import com.ore.contactapp.database.ContactDatabase
import com.ore.contactapp.database.ContactDatabaseDao
import com.ore.contactapp.database.ContactRepository
import kotlinx.coroutines.launch

class AllContactsViewModel(
    var database: ContactDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: ContactRepository
    // LiveData gives us updated contacts when they change.
    val contacts: LiveData<List<Contact>>

    init {
        database = ContactDatabase.getInstance(application).contactDatabaseDao
        repository = ContactRepository(database)
        contacts = repository.allContacts
    }

    fun insert(contact: Contact) = viewModelScope.launch {
        repository.insert(contact)

    }
}