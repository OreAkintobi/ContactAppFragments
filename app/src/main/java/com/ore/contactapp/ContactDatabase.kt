package com.ore.contactapp

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Annotates class to be a Room Database with a table (entity) of the Contact class
@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase : RoomDatabase() {

    abstract fun contactDao(): ContactDAO

 //   private class ContactDatabaseCallback : RoomDatabase.Callback() {

//        override fun onOpen(db: SupportSQLiteDatabase) {
//            super.onOpen(db)
//            INSTANCE?.let { database ->
//                CoroutineScope(IO).launch {
//                    populateDatabase(database.contactDao())
//                }
//            }
//        }

//        suspend fun populateDatabase(contactDao: ContactDAO) {
//            // Delete all content here.
//
//          //  contactDao.deleteAll()
//
//
//            // Add sample contacts.
////            var contact = Contact(1,"Ore Akintobi", "ore@ore.com", "08181248830")
////            contactDao.insert(contact)
////            contact = Contact( 2,"Olaolu Akintobi", "laolak@gmail.com", "08033087643")
////            contactDao.insert(contact)
////            contact = Contact( 3,"Bukky Akintobi", "bukkson@gmail.com", "08031957892")
////            contactDao.insert(contact)
////            contact = Contact( 4,"Lamide Akintobi", "lamide@gmail.com", "08031957893")
////            contactDao.insert(contact)
////            contact = Contact( 5,"Bobo Brazil", "bobo@gmail.com", "08031957894")
////            contactDao.insert(contact)
////            contact = Contact( 6,"Panshak Deeprak", "panshak@gmail.com", "08031957895")
////            contactDao.insert(contact)
////            contact = Contact( 7,"Festus Abrum", "brumbrum@gmail.com", "08031957896")
////            contactDao.insert(contact)
////            contact = Contact( 8,"Baby Yoda", "babyyoda@gmail.com", "08031957897")
////            contactDao.insert(contact)
////            contact = Contact( 9,"Pedro Pascal", "pedropascal8@gmail.com", "08031957898")
////            contactDao.insert(contact)
////            contact = Contact( 10,"The Mandalorian", "trilobyte@gmail.com", "08023387654")
////            contactDao.insert(contact)
////            contact = Contact( 11,"Rick Sanchez", "bambambigelow@gmail.com", "08183087643")
////            contactDao.insert(contact)
//
//        }
//    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ContactDatabase? = null

        fun getDatabase(
            context: Context
        ): ContactDatabase? {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
           if(INSTANCE == null) {
               INSTANCE = Room.databaseBuilder(
                   context.applicationContext,
                   ContactDatabase::class.java,
                   "contact_database"
               )
                   .allowMainThreadQueries()
                   .build()
           }
               return INSTANCE
           }
        }
    }
