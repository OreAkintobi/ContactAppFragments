package com.ore.contactapp.ui.allcontacts


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.ore.contactapp.Contact
import com.ore.contactapp.database.ContactDatabase
import com.ore.contactapp.utils.toast
import com.ore.loginsignupui.R
import com.ore.loginsignupui.databinding.FragmentAllContactsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

class FragmentAllContacts : Fragment() {
    private lateinit var allContactsViewModel: AllContactsViewModel
    private lateinit var binding: FragmentAllContactsBinding
    lateinit var adapter: AllContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_all_contacts, container, false)
        val application = requireNotNull(this.activity).application
        val dataSource = ContactDatabase.getInstance(application).contactDatabaseDao
        val firestoreDB = FirebaseFirestore.getInstance()
        val viewModelFactory = AllContactsViewModelFactory(dataSource, application)
        allContactsViewModel =
            ViewModelProvider(this, viewModelFactory).get((AllContactsViewModel::class.java))
        binding.allContactsViewModel = allContactsViewModel
        binding.lifecycleOwner = this


        allContactsViewModel.contacts.observe(viewLifecycleOwner, Observer { contactList ->
            contactList as ArrayList<Contact>
            adapter =
                AllContactsAdapter(contactList.sortedBy { it.name }, this.context!!) { contact ->
                view!!.findNavController().navigate(
                    FragmentAllContactsDirections.actionFragmentAllContactsToFragmentViewSingleContact(
                        contact
                    )
                )
            }
            binding.recyclerView.adapter = adapter
        })

        binding.addNewContactButton.setOnClickListener {
            view?.let { view ->
                Navigation.findNavController(view)
                    .navigate(R.id.action_fragmentAllContacts_to_fragmentAddContact2)
            }
        }

        ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val contact = adapter.getContactAt(viewHolder.adapterPosition)
                AlertDialog.Builder(context).apply {
                    setTitle("Are you sure you want to delete ${contact.name}?")
                    setMessage("This contact will be deleted on ALL databases.")
                    setPositiveButton("Yes") { _, _ ->
                        firestoreDB.collection("Contacts").document("${contact.contactDbId}")
                            .delete().addOnCompleteListener {
                            CoroutineScope(Main).launch {
                                dataSource.deleteContact(contact)
                            }
                        }.addOnFailureListener {
                            it.stackTrace
                            Toast.makeText(
                                context,
                                "This Operation didn't work because ${it.stackTrace}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        context.toast("${contact.name} deleted", 1)
                    }
                    setNegativeButton("No") { _, _ ->
                        findNavController().navigate(R.id.fragmentAllContacts)
                    }
                }.create().show()
                binding.recyclerView.scrollToPosition(viewHolder.adapterPosition)
            }
        }).attachToRecyclerView(binding.recyclerView)

        return binding.root
    }
}