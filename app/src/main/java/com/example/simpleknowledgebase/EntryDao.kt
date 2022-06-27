package com.example.simpleknowledgebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*


@Dao
interface EntryDao {

    @Query("SELECT * FROM KbTable")
    fun findAll():LiveData<List<Entry>>

    @Query("Select * FROM KbTable WHERE (title LIKE '%'||:keyword||'%' OR description LIKE '%'||:keyword||'%') " )
    // Room cannot handle MutableLiveData here
    fun findKeyword(keyword: String): List<Entry>

    //TBD: More queries

    @Insert
    fun insertEntry(entry: Entry)

    @Update
    fun updateEntry(entry: Entry)

    @Delete
    fun deleteEntry(entry: Entry)


}