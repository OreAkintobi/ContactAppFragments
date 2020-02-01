package com.ore.contactapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ore.loginsignupui.R

class ContactsAdapter(var contacts: List<Contact>, var context: Context): RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>() {

    private val inflater = LayoutInflater.from(context)
//    private var contacts = ArrayList<Contact>()  /** Cached copy of contacts **/


    inner class ContactViewHolder(contactView: View): RecyclerView.ViewHolder(contactView){
        var contactName: TextView = contactView.findViewById(R.id.contactName)
        var contactEmail: TextView = contactView.findViewById(R.id.contactEmail)
        var contactPhone: TextView = contactView.findViewById(R.id.contactPhone)
        var contactCall: ImageView = contactView.findViewById(R.id.contactCall)
        var contactImage: ImageView = contactView.findViewById(R.id.contactImageTwo)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        val contactView = inflater.inflate(R.layout.contact_view, parent,false)
        return ContactViewHolder(contactView)
    }


    override fun getItemCount() = contacts.size


    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]
        holder.contactName.text = contact.name
        holder.contactEmail.text = contact.email
        holder.contactPhone.text = contact.phone
        Glide.with(context).asBitmap().load("https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80").centerCrop().fallback(
            R.drawable.ic_contact_logo_main
        ).error(R.drawable.ic_add_contact_logo).placeholder(R.drawable.ic_contact_logo_main).into(holder.contactImage)

        holder.itemView.setOnClickListener{
            val intent = Intent(context, MainViewContactActivity::class.java)
            intent.putExtra("NAME", contact.name)
            intent.putExtra("EMAIL", contact.email)
            intent.putExtra("PHONE", contact.phone)
            context.startActivity(intent)
        }

        holder.contactCall.setOnClickListener {
            val number = holder.contactPhone.text.toString().trim()

            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + Uri.encode(number)))
            try {
                startActivity(holder.contactPhone.context, intent, null)
            } catch (e: Exception){
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    internal fun setContacts(contacts: List<Contact>) {
        this.contacts = contacts
        notifyDataSetChanged()
    }

}