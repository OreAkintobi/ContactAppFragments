package com.ore.contactapp.utils

class Reusables {

    //        firestoreDB.collection("Contacts")
//            .get().addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    dataSource.clearContacts()
//                    val document = task.result
//                    if (document != null) {
//                        val thisArray = ArrayList<Contact>()
//                        for (contact in document) {
//                            val item = contact.data
//                            val newContact =  Contact(
//                                item["name"] as String?,
//                                item["email"] as String?,
//                                item["phone"] as String?
//                            )
//                            allContactsViewModel.contacts.value?.add(newContact)
//                            allContactsViewModel.contacts.value?.sortBy {
//                                it.name
//                            }
//                            adapter.notifyDataSetChanged()
//                        }
//                    } else {
//                        Log.d(TAG, "No such document")
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.exception)
//                }
//            }


}