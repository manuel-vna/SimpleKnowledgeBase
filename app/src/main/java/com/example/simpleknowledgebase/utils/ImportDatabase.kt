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
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.InputStreamReader


class ImportDatabase(context: Context, private val registry : ActivityResultRegistry) : DefaultLifecycleObserver {

    lateinit var activityResultLauncherImport: ActivityResultLauncher<Intent>
    var context = context
    var appDb: EntryDatabase = EntryDatabase.getInstance(context)

    var lastDbId: Int = 0 // default value for an empty database
    lateinit var importLinePointer: String


    override fun onCreate(owner: LifecycleOwner) {
        activityResultLauncherImport = registry.register("key",owner,ActivityResultContracts.StartActivityForResult()) { result ->

            val data: Intent? = result.data
            val uri: Uri? = data?.data


            GlobalScope.launch(Dispatchers.IO){

                // Retrieving the value of the column 'id' for the very last row.
                // This is achieved by setting the cursor to the last row, checking with 'cursor.position >= 0' if there is a row at all
                // Then the index of the column 'id' is retrieved
                // which is used to retrieve the value of the column 'id' of the last row of the table
                val cursor: Cursor = appDb.entryDao().getAllEntriesAsCursor()
                cursor.moveToLast()
                if (cursor.position >= 0) {
                    var dbColumnIndex: Int = cursor.getColumnIndex("id") //columnIndexes: column 'id' == 0, 'date' == 1, 'title' == 2, etc.
                    lastDbId = cursor.getInt(dbColumnIndex)
                }

                //TBD



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