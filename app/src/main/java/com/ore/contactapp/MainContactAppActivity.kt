package com.ore.contactapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ore.loginsignupui.R
import com.wajahatkarim3.easyvalidation.core.view_ktx.nonEmpty
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.activity_main_contact_app.*


class MainBabyBlissLoginUIActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_contact_app)
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_keyboard_arrow_left_black_24dp)

        toolbar.setNavigationOnClickListener {
            super.onBackPressed()
        }

        addContactButton.setOnClickListener { saveContact() }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu1) {
            saveContact()
            return true
        }
        if (item.itemId == R.id.preferences) {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveContact() {
        val nameValue = name.text.toString()
        val emailValue = email.text.toString()
        val phoneValue = phone.text.toString()

        if (
            phoneValue.validator()
                .validNumber()
                .startsWith("0")
                .addErrorCallback {
                    Toast.makeText(this, "Please Enter a Valid Phone Number", Toast.LENGTH_LONG)
                        .show()
                    phone.setText("")
                }.check() && emailValue.validator()
                .validEmail()
                .nonEmpty()
                .addErrorCallback {
                    Toast.makeText(this, "Please Enter a Valid Email", Toast.LENGTH_LONG).show()
                    email.setText("")
                }.check() && nameValue.nonEmpty {
                Toast.makeText(this, "Please Enter a Name", Toast.LENGTH_LONG).show()
                name.setText("")
            }
        ) {
            Toast.makeText(
                this,
                "Name: $nameValue \nEmail: $emailValue \nPhone Number: $phoneValue",
                Toast.LENGTH_SHORT
            ).show()



            val intent = Intent()
            intent.putExtra(CONTACT, Contact(0, nameValue, emailValue, phoneValue))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    companion object {
        const val CONTACT = "CONTACT"
    }
}
