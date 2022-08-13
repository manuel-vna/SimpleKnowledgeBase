package com.example.simpleknowledgebase.utils

import android.database.Cursor
import android.os.Environment
import android.util.Log
import com.example.simpleknowledgebase.EntryDatabase
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class ExportDatabase(appDb: EntryDatabase) {

    var appDb: EntryDatabase = appDb

    // exportSuccess states:
    // unknown: initial value
    // success: if FileWriter routine finishes successfully inside runExport()->try{}
    // error  : if an error occurs in the try{}-block: error
    // empty  : if the database is empty
    var exportSuccess: String = "unknown"

    fun runExport() : String {

            val cursor: Cursor = appDb.entryDao().getAllEntriesAsCursor()
            cursor.moveToFirst()

            // check if database is empty
            if (appDb.entryDao().findAll().isEmpty()){
                exportSuccess = "empty"
                return exportSuccess
            }

            val pathname =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString()

            try {
                val root = File(pathname)
                if (!root.exists()) {
                    root.mkdirs()
                }
                val timestampFormat = SimpleDateFormat("yyyyMMdd_hhmm") // Android filepath doesn't seem to allow the character '-' and ':'
                val fileName: String = "${timestampFormat.format(Date())}_exportDb.csv"
                val gpxfile = File(
                    root,
                    fileName
                )
                val writer: FileWriter = FileWriter(gpxfile)

                while(cursor.isAfterLast == false){
                    writer.append(
                        cursor.getString(cursor.getColumnIndexOrThrow("title")) + ";"
                                + cursor.getString(cursor.getColumnIndexOrThrow("category")) + ";"
                                + cursor.getString(cursor.getColumnIndexOrThrow("description")) + ";"
                                + cursor.getString(cursor.getColumnIndexOrThrow("source")) + "\n"
                    )
                    cursor.moveToNext()
                }
                writer.flush()
                writer.close()
                cursor.close()
                appDb.close()
                exportSuccess = "success"

            } catch (e: IOException) {
                exportSuccess = "error"
                e.printStackTrace()
                Log.d("Debug_A", e.toString())
            }

        return exportSuccess
    }

}