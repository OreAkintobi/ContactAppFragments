package com.ore.contactapp.database

import androidx.lifecycle.LiveData
import com.ore.contactapp.Contact

class ContactRepository(private val contactDatabaseDao: ContactDatabaseDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val allContacts: LiveData<MutableList<Contact>> = contactDatabaseDao.getAllContacts()

    suspend fun insert(contact: Contact) {
        contactDatabaseDao.insertContact(contact)
    }

    suspend fun clearContacts() {
        contactDatabaseDao.clearContacts()
    }
}