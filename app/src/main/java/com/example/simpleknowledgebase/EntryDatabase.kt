package com.example.simpleknowledgebase

import android.app.Application
import androidx.room.Database;
import androidx.room.Room
import androidx.room.RoomDatabase;


@Database(entities = [Entry::class], version = 1)
abstract class EntryDatabase : RoomDatabase() {

    abstract fun entryDao(): EntryDao

    companion object {
        @Volatile
        private var INSTANCE: EntryDatabase? = null

        fun getInstance(application: Application): EntryDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        application.applicationContext,
                        EntryDatabase::class.java,
                        "entry_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}
