package com.ore.contactapp.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

// File that creates extension functions for Toast and Snackbar messages used in views

// Here, the name of the function is Toast, while the Context is where the extension function is being applied.
fun Context.toast(message: String, length: Int = 2) {
    if (length == 1) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

// NB: Snackbars work best in Coordinator Layouts
fun View.snackbar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).also { snackbar ->
        snackbar.setAction("ok") {
            snackbar.dismiss()
        }
    }.show()
}