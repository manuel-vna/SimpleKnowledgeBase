package com.example.simpleknowledgebase.utils

import android.database.Cursor
import android.os.Environment
import android.util.Log
import com.example.simpleknowledgebase.EntryDatabase
import kotlinx.coroutines.*
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*


class ExportDatabase(appDb: EntryDatabase) {

    var appDb: EntryDatabase = appDb
    var writingSuccess:Boolean = true

    fun runExport() : Boolean {

        GlobalScope.launch(Dispatchers.IO) {

            val cursor: Cursor = appDb.entryDao().getAllEntriesAsCursor()
            cursor.moveToFirst()

            val pathname =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString()

            try {
                val root = File(pathname)
                if (!root.exists()) {
                    root.mkdirs()
                }
                val timestampFormat = SimpleDateFormat("yyyyMMddhhmm") // Android filepath doesn't seem to allow the character '-'
                val fileName: String = "export_${timestampFormat.format(Date())}.csv"
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
                writingSuccess = true

            } catch (e: IOException) {
                e.printStackTrace()
                Log.d("Debug_A", e.toString())
            }
        }
        return writingSuccess
    }

}