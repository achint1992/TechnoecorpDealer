package com.technoecorp.gorilladealer.extensions

import android.content.Context
import android.widget.Toast
import java.lang.Exception

fun Context.showShortToast(message: String?) {
    message?.let {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }

}

fun Context.showShortExceptionToast(e: Exception) {
    e.message?.let {
        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
    }

}

fun Context.showLongToast(message: String?) {
    message?.let {
        Toast.makeText(this, it, Toast.LENGTH_LONG).show()
    }

}