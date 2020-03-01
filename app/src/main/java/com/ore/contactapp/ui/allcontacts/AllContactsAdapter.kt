package com.ore.contactapp.ui.allcontacts

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ore.contactapp.Contact
import com.ore.loginsignupui.R
import com.ore.loginsignupui.databinding.ContactViewBinding

/** THIS CLASS PROVIDES/INFLATES A UNIT TEMPLATE OF WHAT EACH CONTACT DISPLAYED IN A RECYCLERVIEW LIST WOULD LOOK LIKE **/
class AllContactsAdapter(
    private var contacts: List<Contact>,
    private var context: Context,
    private val clickListener: (Contact) -> Unit
) :
    RecyclerView.Adapter<AllContactsAdapter.ContactViewHolder>() {
    private lateinit var binding: ContactViewBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        val inflater = LayoutInflater.from(context)
        binding = ContactViewBinding.inflate(inflater, parent, false)
        return ContactViewHolder(
            binding
        )
    }

    fun getContactAt(position: Int): Contact {
        return contacts[position]
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.bind(contact, clickListener)
        Glide.with(binding.root.context).asBitmap()
            .load("https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80")
            .centerCrop().fallback(
                R.drawable.ic_contact_logo_main
            ).error(R.drawable.ic_add_contact_logo).placeholder(R.drawable.ic_contact_logo_main)
            .into(binding.contactImageTwo)

        binding.contactCall.setOnClickListener {
            val number = binding.contactPhone.text.toString().trim()
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + Uri.encode(number)))
            try {
                startActivity(binding.contactPhone.context, intent, null)
            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    class ContactViewHolder(private val binding: ContactViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Contact, clickListener: (Contact) -> Unit) {
            binding.contact = data
            binding.root.setOnClickListener { clickListener(data) }
        }
    }
}