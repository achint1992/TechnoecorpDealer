package com.technoecorp.gorilladealer.ui.custom

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import com.technoecorp.gorilladealer.R
import android.view.WindowManager
import android.graphics.drawable.ColorDrawable
import android.util.DisplayMetrics
import android.view.Window
import timber.log.Timber

/**
 * Created by achint on 3/25/17.
 */
class CustomDialogClass(var c: Context) : Dialog(c) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.loader_layout)
        Timber.e("Coming Here")
        val wm = c.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        Timber.e("Coming Here 1")
        val display = wm.defaultDisplay
        setCancelable(false)
        val metrics = DisplayMetrics()
        display.getMetrics(metrics)
        Timber.e("Coming Here 2")
        val orientation = c.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            val width = metrics.widthPixels * .45
            val height = metrics.heightPixels * .25
            val win = this.window
            win!!.setLayout(width.toInt(), height.toInt())
        } else {
            val width = metrics.widthPixels * .25
            val height = metrics.heightPixels * .45
            val win = this.window
            win!!.setLayout(width.toInt(), height.toInt())
        }
    }

}