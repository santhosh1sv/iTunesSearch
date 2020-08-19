package com.altimetrik.itunessearch.utilities

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.altimetrik.itunessearch.R


fun Activity.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Activity.isConnectingToInternet(): Boolean {
    val connMgr =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connMgr.activeNetworkInfo
    if (activeNetworkInfo != null) {
        if (activeNetworkInfo.type == ConnectivityManager.TYPE_WIFI) {
            return true
        } else if (activeNetworkInfo.type == ConnectivityManager.TYPE_MOBILE) {
            return true
        }
    }
    return false
}

fun Activity.hideKeyboard() {
    val imm: InputMethodManager =
        this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    var view: View? = this.currentFocus
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
}


fun Activity.showSingleChoiceItemsDialog(title: String,itemsId:Int, selection: Int, f: (Int) -> Unit) {
    val builder: AlertDialog.Builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setSingleChoiceItems(
        itemsId, selection
    ) { dialog, which ->
        f(which)
        dialog.dismiss()
    }
    val alert: AlertDialog = builder.create()
    alert.show()
}

fun AppCompatActivity.showActionBar(toolbar: Toolbar, title: String) {
    toolbar.setTitle(title)
    setSupportActionBar(toolbar)
    getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
    getSupportActionBar()?.setDisplayShowHomeEnabled(true)
}