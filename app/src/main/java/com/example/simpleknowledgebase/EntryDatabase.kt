package com.example.simpleknowledgebase

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = [Entry::class], version = 1)
abstract class EntryDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao
}
