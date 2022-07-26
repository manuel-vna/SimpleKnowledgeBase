package com.example.simpleknowledgebase.repositories


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.EntryDao
import com.example.simpleknowledgebase.viewmodels.KeywordSearchViewModel
import kotlinx.coroutines.*



class EntryRepository(private val entryDao: EntryDao) {

    private lateinit var keywordSearchViewModel: KeywordSearchViewModel
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val searchResults = MutableLiveData<List<Entry>>()
    val searchResultsAllCategories = MutableLiveData<List<String>>()
    val searchResultsCategory = MutableLiveData<List<Entry>>()
    //In Testing: Adv. Search testing:
    var searchResultsDate: List<Entry>? = null
    //private lateinit var searchResultsDate: List<Entry>


    fun insertEntry(entry: Entry){
        coroutineScope.launch(Dispatchers.IO) {
            entryDao.insertEntry(entry)
        }
    }

    fun updateEntry(entry: Entry){
        coroutineScope.launch(Dispatchers.IO) {
            entryDao.updateEntry(entry)
        }
    }

    fun deleteEntry(entry: Entry){
        coroutineScope.launch(Dispatchers.IO) {
            entryDao.deleteEntry(entry)
        }
    }

    // observing the number of rows = number of entries

    var searchResultsRowNumber = entryDao.observeTotalRowNumber()

    // find entries by keyword

    fun findKeyword(keyword: String){
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = keywordFindAsync(keyword).await()
        }
    }
    private fun keywordFindAsync(keyword: String): Deferred<List<Entry>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async entryDao.findKeyword(keyword)
        }

    // find all existing categories

    fun findAllCategories() {
        // coroutines that change UI elements can only be performed by the MAIN thread
        coroutineScope.launch(Dispatchers.Main) {
        searchResultsAllCategories.setValue(categoryFindAsync().await())
    }
    }
    private fun categoryFindAsync(): Deferred<List<String>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async entryDao.findAllCategories()
        }

    //find category by keyword

    fun findCategory(keyword: String){
        coroutineScope.launch(Dispatchers.Main) {
            searchResultsCategory.setValue(singleCategoryFindAsync(keyword).await())
        }
    }
    private fun singleCategoryFindAsync(keyword: String): Deferred<List<Entry>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async entryDao.findCategory(keyword)
        }

    // find entries by time date time spans

    //approach 1 a: coroutine with async-await:
    /*
    fun dateFindAsync(dateFrom: String, dateTo: String): Deferred<List<Entry>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async entryDao.findEntriesOfDateTimeSpan(dateFrom, dateTo)
        }
     */

    //approach 1b: coroutine with async-await:
    /*
    fun findEntriesOfDateTimeSpan(dateFrom: String, dateTo: String) : List<Entry>? {
        coroutineScope.launch {
            searchResultsDate = entryDao.findEntriesOfDateTimeSpan(dateFrom, dateTo)
        }
        return searchResultsDate
    }
     */

    //approach 1c: coroutine with async-await:
    fun findEntriesOfDateTimeSpan(dateFrom: String, dateTo: String) : List<Entry>? {
        coroutineScope.launch {
            searchResultsDate = entryDao.findEntriesOfDateTimeSpan(dateFrom, dateTo)
            Log.i("Debug_A", "EntryRepository: "+searchResultsDate.toString())
        }
        return searchResultsDate
    }

    //approach 2: coroutine with withContext():
    /*
    fun dateFindAsync(dateFrom: String, dateTo: String) : List<Entry> {
        coroutineScope.launch {
            searchResultsDate = withContext(Dispatchers.IO) {
                entryDao.findEntriesOfDateTimeSpan(dateFrom, dateTo)
            }
        }
        return searchResultsDate
    }
     */


}