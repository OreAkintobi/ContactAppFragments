package com.ore.contactapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ore.contactapp.Contact
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {
    abstract val contactDatabaseDao: ContactDatabaseDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {
                    val contactDatabaseDao = database.contactDatabaseDao

                    // Delete all content here.
//                    contactDatabaseDao.clearContacts()

                    // Add sample contacts.
                    var contact = Contact("Olaolu Akintobi", "laolak@gmail.com", "08033087643")
                    contactDatabaseDao.insertContact(contact)
                    contact = Contact("Bukky Akintobi", "bukkson@gmail.com", "08031957892")
                    contactDatabaseDao.insertContact(contact)
                    contact = Contact("Lamide Akintobi", "lamide@gmail.com", "08031957893")
                    contactDatabaseDao.insertContact(contact)
                    contact = Contact("Bobo Brazil", "bobo@gmail.com", "08031957894")
                    contactDatabaseDao.insertContact(contact)

                }
            }
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ContactDatabase::class.java,
                        "contact_database"
                    )
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}