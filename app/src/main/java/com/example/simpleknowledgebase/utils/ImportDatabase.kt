package com.example.simpleknowledgebase.utils

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.simpleknowledgebase.EntryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class ImportDatabase(context: Context, private val registry : ActivityResultRegistry) : DefaultLifecycleObserver {

    lateinit var activityResultLauncherImport: ActivityResultLauncher<Intent>
    var context = context
    var appDb: EntryDatabase = EntryDatabase.getInstance(context)


    override fun onCreate(owner: LifecycleOwner) {
        activityResultLauncherImport = registry.register("key",owner,ActivityResultContracts.StartActivityForResult()) { result ->

            val data: Intent? = result.data
            val uri: Uri? = data?.data


            GlobalScope.launch(Dispatchers.IO){
                try {
                    val cursor: Cursor = appDb.entryDao().getAllEntriesAsCursor()
                    cursor.moveToLast()
                    val dbColumnIndex: Int = cursor.getColumnIndex("id")
                    val lastDbId: Int = cursor.getInt(dbColumnIndex)

                } catch (e: Exception) {
                    Log.i("Debug_A", "Database Status: No entries yet")
                }


            }

        }
    }


    fun selectImportFile(){
        var data = Intent(Intent.ACTION_GET_CONTENT)
        data.addCategory(Intent.CATEGORY_OPENABLE)
        val mimeTypes = arrayOf("text/csv", "text/comma-separated-values")
        data.type = "*/*"
        data.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        data = Intent.createChooser(data, "Choose a file")
        activityResultLauncherImport.launch(data)
    }

}