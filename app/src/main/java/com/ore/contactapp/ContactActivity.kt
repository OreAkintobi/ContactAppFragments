package com.ore.contactapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ore.loginsignupui.R
import kotlinx.android.synthetic.main.contact_scrolling.*

class ContactActivity : AppCompatActivity() {

    lateinit var database: ContactDatabase
    private lateinit var contactViewModel: ContactViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        val contacts = ArrayList<Contact>()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contact_scrolling)



        recyclerView.layoutManager = LinearLayoutManager(this)

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)


        contactViewModel.allContacts.observe(this, Observer { myContact ->
            // Update the cached copy of the words in the adapter.
            recyclerView.adapter = ContactsAdapter(myContact, this)
//            contacts.let { adapter.setContacts(it)}
        })




        setSupportActionBar(toolbarAll)
        toolbarAll.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)

        toolbarAll.setNavigationOnClickListener {
            super.onBackPressed()
        }

        val myFabSrc= resources.getDrawable(R.drawable.ic_add_black_24dp, null)
        DrawableCompat.setTint(myFabSrc,Color.BLUE)
//        val rightColor: Drawable = myFabSrc.constantState!!.newDrawable()
//        val rightColor = myFabSrc.constantState?.newDrawable()


//        val f = AppCompatResources.getDrawable(this,R.drawable.ic_add_black_24dp)
//        val e = DrawableCompat.wrap(f!!)
//        DrawableCompat.setTint(e,Color.BLUE)
//        addNewContactButton.setImageDrawable(e)


//        rightColor?.mutate().
//        rightColor.mutate().
//            setColorFilter(getResources().getColor(R.color.background), PorterDuff.Mode.MULTIPLY)


        val bundle = intent.extras?.getParcelable<Contact>("CONTACT")
        if (bundle != null) {
            contacts.add(bundle)
        }

        contacts.sortBy {
            it.name
        }


        addNewContactButton.setOnClickListener {
            val intent = Intent(this, MainBabyBlissLoginUIActivity::class.java)
            startActivityForResult(intent, 1)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item = menu.findItem(R.id.menu1)
        item.isVisible = false
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.preferences) {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                database = ContactDatabase.getDatabase(applicationContext)!!
            val bundle = data?.extras
            val result = bundle?.getParcelable<Contact>("CONTACT")
            if (result != null) {
                database.contactDao().insert(result)

                val contactList = database.contactDao().getAllContacts()
                Log.e("database",contactList.toString())
            }
        }
    }
}

