package com.technoecorp.gorilladealer.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

class PermissionUtils {

    companion object {
        fun checkStorage(act: Context): Boolean {
            val result =
                ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            return result == PackageManager.PERMISSION_GRANTED
        }

        //Request Permission
        fun requestStorage(launcher: ActivityResultLauncher<String>) {
            launcher.launch(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
        }
    }
}