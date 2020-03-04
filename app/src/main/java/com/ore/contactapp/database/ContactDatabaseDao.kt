package com.ore.contactapp.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ore.contactapp.Contact

@Dao
interface ContactDatabaseDao {
    @Insert
    fun insertContact(vararg contact: Contact)

    @Insert
    fun insertSingle(contact: Contact)

    @Update
    fun updateContact(contact: Contact)

    @Query("SELECT * from contact_table WHERE name = :key")
    fun getContact(key: String): Contact

//    @Query("DELETE FROM contact_table WHERE name = :contact LIMIT 1")
//    fun deleteContact(contact: Contact)

    @Delete
    fun deleteContact(contact: Contact)

    @Query("DELETE FROM contact_table")
    fun clearContacts()

    @Query("SELECT * from contact_table ORDER BY name ASC")
    fun getAllContacts(): LiveData<MutableList<Contact>>

    @Query("SELECT * FROM contact_table ORDER BY contactDbId DESC LIMIT 1")
    fun getLastContact(): Contact
}