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
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.EntryDao
import com.example.simpleknowledgebase.EntryDatabase
import com.example.simpleknowledgebase.activities.MainActivity
import com.example.simpleknowledgebase.viewmodels.AddEntryViewModel
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.time.LocalDateTime


class ImportDatabase(context: Context, private val registry : ActivityResultRegistry) : DefaultLifecycleObserver {

    lateinit var activityResultLauncherImport: ActivityResultLauncher<Intent>
    var context = context
    val appDb: EntryDatabase = EntryDatabase.getInstance(context)
    val entryDao: EntryDao = appDb.entryDao()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val mainActivity = MainActivity()

    lateinit var entriesImportList: List<Entry>
    var lastDbId: Int = 0 // default value for an empty database
    var newIdsInDb: MutableList<Long> = ArrayList()


    override fun onCreate(owner: LifecycleOwner) {

        activityResultLauncherImport = registry.register("key",owner,ActivityResultContracts.StartActivityForResult()) { result ->

            val data: Intent? = result?.data
            val uri: Uri? = data?.data


            coroutineScope.launch(){

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

                val inputStream: InputStream? = context.contentResolver.openInputStream(uri!!)


                fun readCsv(inputStream: InputStream): List<Entry> {

                    // read data in a buffer
                    val reader: BufferedReader = inputStream.bufferedReader()

                    return reader.useLines {
                        it.filter { it.isNotBlank() }
                        .map {
                            val (title,category,description,source) =
                                it.split(';', ignoreCase = false, limit = 4)
                            Entry(title, category,description,source)
                        }.toList()
                    }
                }
                entriesImportList = readCsv(inputStream!!)

                //list of new table IDs (=rows) has to be empty for every new import run
                newIdsInDb.clear()

                for (entry in entriesImportList) {
                    lastDbId += 1

                    val id: Int = lastDbId
                    val date: String = LocalDateTime.now().toString()
                    val title: String = entry.title
                    val category: String = entry.category
                    val description: String = entry.description
                    val source: String = entry.source

                    var successCode = entryDao.insertEntry(Entry(id,date,title,category,description,source))
                    newIdsInDb.add(successCode)

                }

                // this Boolean compares if the amount of initially retrieved rows from the file matches
                val importSuccess: Boolean = newIdsInDb.count() == entriesImportList.count()


                withContext(Dispatchers.Main){
                    mainActivity.importFinishedMessage(context,importSuccess)
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

        try {
            activityResultLauncherImport.launch(data)
        }
        catch(e: Exception) {
            mainActivity.importErrorMessage(context)
        }
    }

}