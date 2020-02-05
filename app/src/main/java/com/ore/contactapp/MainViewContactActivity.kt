package com.ore.contactapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ore.loginsignupui.R
import kotlinx.android.synthetic.main.activity_main_view_contact.*

/** DISPLAYS AN INDIVIDUAL CONTACT'S INFORMATION ON A SEPARATE PAGE AFTER REDIRECTING FROM RECYCLERVIEW/CONTACT LIST **/
class MainViewContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_view_contact)

        // sets up Tool Bar for Activity
        setSupportActionBar(toolbar)
        // sets up Back Navigation Icon
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)
        // sets up event listener for Back Navigation Icon
        toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }

        // sets up event listener on phone ImageView/Button
        callContact.setOnClickListener {
            callContact()
        }

        // sets up event listener on email ImageView/Button
        emailContact.setOnClickListener {
            emailContact()
        }

        val bundle = intent.extras
        val name = bundle?.getString("NAME")
        val email = bundle?.getString("EMAIL")
        val phone = bundle?.getString("PHONE")

        placeholderViewContactName.append(name)
        placeholderViewContactEmail.append(email)
        placeholderViewContactPhone.append(phone)
        Glide.with(this).asBitmap().load("https://images.unsplash.com/photo-1506794778202-cad84cf45f1d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1000&q=80").centerCrop().fallback(
            R.drawable.ic_contact_logo_main
        ).error(R.drawable.ic_add_contact_logo).placeholder(R.drawable.ic_contact_logo_main).into(contactImageTwo)
    }

    fun callContact(){
        val number = placeholderViewContactPhone.text.toString().trim()

        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + Uri.encode(number)))
        startActivity(intent)
    }

    fun emailContact(){
        val email = placeholderViewContactEmail.text.toString().trim()
        // Initialize intent
        val intent = Intent(Intent.ACTION_SEND)
        // use ACTION_SEND to launch email client, with URI setData() and setType() methods
        intent.data = Uri.parse("mailto:")
        intent.type = "text/plain"
        // place recipient mail in intent (Send comma-seperated array if sending multiple emails)
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        try {
            startActivity(Intent.createChooser(intent, "Choose Email Client"))
        } catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}
