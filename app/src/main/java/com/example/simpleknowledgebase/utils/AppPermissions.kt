package com.example.simpleknowledgebase.utils

import android.Manifest.permission
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity


class AppPermissions() {

    //if-Blocks: Android 11 or higher
    //else-Blocks: Below Android 11

    private fun checkExportPermission(context:Context): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return true
        }
        else {
            val result = ContextCompat.checkSelfPermission(context, permission.WRITE_EXTERNAL_STORAGE)
            return result == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestExportPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(permission.WRITE_EXTERNAL_STORAGE),
            101
        )
    }

    private fun checkImportPermission(context:Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            Environment.isExternalStorageManager()
        } else {
            val resultRead =
                ContextCompat.checkSelfPermission(context, permission.READ_EXTERNAL_STORAGE)
            val resultWrite =
                ContextCompat.checkSelfPermission(context, permission.WRITE_EXTERNAL_STORAGE)
            resultRead == PackageManager.PERMISSION_GRANTED && resultWrite == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestImportPermission(activity: Activity, context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.addCategory("android.intent.category.DEFAULT")
                intent.data = Uri.parse(String.format("package:%s", context.packageName))
                startActivity(context,intent,null)
            } catch (e: Exception) {
                val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
                startActivity(context,intent,null)
            }
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(permission.WRITE_EXTERNAL_STORAGE),
                101
            )
        }
    }


}