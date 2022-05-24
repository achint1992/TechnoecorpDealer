package com.technoecorp.gorilladealer.ui.custom

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.Insets
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import com.technoecorp.gorilladealer.R
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
        setCancelable(false)
        Timber.e("Coming Here 2")
        val orientation = c.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            val width = getScreenWidth(c as Activity) * .45
            val height = getScreenHeight(c as Activity) * .25
            val win = this.window
            win!!.setLayout(width.toInt(), height.toInt())
        } else {
            val width = getScreenWidth(c as Activity) * .25
            val height = getScreenHeight(c as Activity) * .45
            val win = this.window
            win!!.setLayout(width.toInt(), height.toInt())
        }
    }

    fun getScreenWidth(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.width() - insets.left - insets.right
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }

    fun getScreenHeight(activity: Activity): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowMetrics = activity.windowManager.currentWindowMetrics
            val insets: Insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            windowMetrics.bounds.height() - insets.bottom - insets.top
        } else {
            val displayMetrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }
    }
}