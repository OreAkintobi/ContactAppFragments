package com.ore.loginsignupui

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.ore.contactapp.Contact
import com.ore.contactapp.database.ContactDatabase
import com.ore.contactapp.database.ContactDatabaseDao
import junit.framework.Assert.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class ContactDatabaseTest {

    private lateinit var contactDatabaseDao: ContactDatabaseDao
    private lateinit var db: ContactDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, ContactDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        contactDatabaseDao = db.contactDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetContact() {
        val contact = Contact("Ore Akintobi", "oreakintobi@gmail.com", "08181248830")
        contactDatabaseDao.insertContact(contact)
        val lastContact = contactDatabaseDao.getLastContact()
        assertEquals(lastContact.name, "Ore Akintobi")
    }
}

