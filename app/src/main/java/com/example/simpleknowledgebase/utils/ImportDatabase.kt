package com.example.simpleknowledgebase.utils

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.EntryDao
import com.example.simpleknowledgebase.EntryDatabase
import com.example.simpleknowledgebase.R
import com.example.simpleknowledgebase.activities.MainActivity
import com.example.simpleknowledgebase.fragments.ImportDatabaseDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.InputStream
import java.time.LocalDateTime


class ImportDatabase(context: Context, private val registry : ActivityResultRegistry) : DefaultLifecycleObserver {

    lateinit var activityResultLauncherImport: ActivityResultLauncher<Intent>
    var context = context
    val appDb: EntryDatabase = EntryDatabase.getInstance(context)
    val entryDao: EntryDao = appDb.entryDao()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val mainActivity = MainActivity()

    var entriesImportList: MutableList<Entry> = ArrayList()
    var lastDbId: Int = 0 // default value for an empty database
    var newIdsInDb: MutableList<Long> = ArrayList()

    //filed input length definitions
    private val titleMax: Int = R.string.titleMax.toInt()
    private val categoryMax: Int = R.string.categoryMax.toInt()
    private val descriptionMax: Int = R.string.descriptionMax.toInt()
    private val sourceMax: Int = R.string.sourceMax.toInt()


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


                fun readCsv(inputStream: InputStream): MutableList<Entry> {

                    val reader: BufferedReader = inputStream.bufferedReader()
                    val csvFormat: CSVFormat = CSVFormat.DEFAULT.builder()
                        .setDelimiter(';')
                        .setEscape('/')
                        .build();
                    val csvParser = CSVParser(reader, csvFormat);

                    for (csvRecord in csvParser) {

                        // row tih column size !=  4: drop row and record the according line numbers
                        if (csvRecord.size() != 4){
                            Log.i("Debug_A",csvRecord.toString())
                            continue
                        }

                        //increase the id which belongs to each new row
                        lastDbId += 1

                        val id: Int = lastDbId
                        val date: String = LocalDateTime.now().toString()
                        var title: String = if (csvRecord.get(0).length <= titleMax) csvRecord.get(0) else csvRecord.get(0).substring(0, titleMax)
                        val category: String = if (csvRecord.get(1).length <= categoryMax) csvRecord.get(1) else csvRecord.get(1).substring(0, categoryMax)
                        val description: String = csvRecord.get(2)
                        val source: String = csvRecord.get(3)

                        entriesImportList += Entry(id, date, title, category, description, source)

                    }
                    return entriesImportList
                }

                entriesImportList = readCsv(inputStream!!)


                //list of new table IDs (=rows) has to be empty for every new import run
                newIdsInDb.clear()

                // add collected entries to the database
                try {
                    for (entry in entriesImportList) {
                        var successCode = entryDao.insertEntry(entry)
                        newIdsInDb.add(successCode)
                    }
                } catch(e: Exception){
                    Log.i("Debug_A", "Import failed with Exception: $e")
                }

                // this Boolean compares if the amount of initially retrieved rows from the file matches
                val importSuccess: Boolean = newIdsInDb.count() == entriesImportList.count()

                // clear entry elements
                entriesImportList.clear()

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

        activityResultLauncherImport.launch(data)

        //TBD:
        //mainActivity.importErrorMessage(context)
    }

    fun openImportInfoWindow(supportFragmentManager2: FragmentManager){
        var supportFragmentManager = supportFragmentManager2
        // TBD add 'Don't as me again' popup to Import workflow
        // Contents of the popup:
        ImportDatabaseDialogFragment.newInstance().show(supportFragmentManager, ImportDatabaseDialogFragment.TAG)
    }

}