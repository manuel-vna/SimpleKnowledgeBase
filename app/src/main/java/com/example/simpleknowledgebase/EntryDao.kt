package com.example.simpleknowledgebase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*


@Dao
interface EntryDao {

    @Query("SELECT * FROM KbTable")
    fun findAll(): List<Entry>

    @Query("Select * FROM KbTable WHERE (title LIKE '%'||:keyword||'%' OR description LIKE '%'||:keyword||'%' OR category LIKE '%'||:keyword||'%') " )
    // Room cannot handle MutableLiveData here
    fun findKeyword(keyword: String): List<Entry>

    @Query("SELECT DISTINCT category FROM KbTable ORDER BY category ASC")
    fun findAllCategories(): List<String>

    @Query("Select * FROM KbTable WHERE (category IS :keyword)")
    // match the exact string of a category
    fun findCategory(keyword: String): List<Entry>

    @Query("Select COUNT() FROM KbTable ")
    fun observeTotalRowNumber() : LiveData<Int>

    /*
    //TBD: Adv. Search testing:
    @Query("Select * FROM kbTable WHERE (date BETWEEN :dateFrom and :dateTo)")
    suspend fun findEntriesOfDateTimespan(dateFrom: String, dateTo: String) : List<Entry>
     */

    @Insert
    fun insertEntry(entry: Entry)

    @Update
    fun updateEntry(entry: Entry)

    @Delete
    fun deleteEntry(entry: Entry)


}