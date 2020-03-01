package com.ore.contactapp.ui.login


import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ore.loginsignupui.R
import com.ore.loginsignupui.databinding.FragmentOpenAppBinding

class FragmentOpenApp : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentOpenAppBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_open_app, container, false)

        Toast.makeText(
            context,
            R.string.toast_open_app,
            Toast.LENGTH_SHORT
        ).show()

        binding.openAppButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_fragmentOpenApp_to_fragmentAddContact)
        }

        binding.viewAllContactsButton.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_fragmentOpenApp_to_fragmentAllContacts2)
        }

        binding.killAppButton.setOnClickListener {
            exitApp()
        }

//        binding.logoutButton.setOnClickListener {
//            val prefs: SharedPreferences = getSharedPreferences("MY_PREF", Context.MODE_PRIVATE)
//            prefs.edit().clear().apply()
//            finish()
//        }

        return binding.root
    }

    private fun exitApp() {
        //Display Message to User with Alert Dialog
        // build alert dialog
        val dialogBuilder = AlertDialog.Builder(context)
        // set message of alert dialog
        dialogBuilder.setMessage(R.string.sure)
            // if the dialog is cancelable
            .setCancelable(true)
            // positive button text and action
            .setPositiveButton(R.string.yes) { _, _ ->
                Toast.makeText(
                    context,
                    R.string.toast_exiting_app,
                    Toast.LENGTH_SHORT
                ).show()
//                finishAffinity()
//                exitProcess(0)

            }
            // negative button text and action
            .setNegativeButton(R.string.no) { dialog, _ ->
                dialog.cancel()
            }
        // create dialog box
        val alert = dialogBuilder.create()
        // set title for alert dialog box
        alert.setTitle(R.string.dialog_box_quit)
        // show alert dialog
        alert.show()
    }

}
