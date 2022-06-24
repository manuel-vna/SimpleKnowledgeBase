package com.example.simpleknowledgebase

import androidx.room.*


@Dao
interface EntryDao {

    @Query("SELECT * FROM KbTable")
    fun findAll(): List<Entry>

    @Query("Select * FROM KbTable WHERE (title LIKE '%'||:keyword||'%' OR description LIKE '%'||:keyword||'%') " )
    fun findKeyword(keyword: String): List<Entry>

    //TBD: More queries

    @Insert
    fun insertEntry(entry: Entry)

    @Update
    fun updateEntry(entry: Entry)

    @Delete
    fun deleteEntry(entry: Entry)


}