package com.example.simpleknowledgebase.repositories


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.EntryDao
import com.example.simpleknowledgebase.viewmodels.AdvancedSearchViewModel
import com.example.simpleknowledgebase.viewmodels.KeywordSearchViewModel
import kotlinx.coroutines.*



class EntryRepository(private val entryDao: EntryDao) {

    private lateinit var keywordSearchViewModel: KeywordSearchViewModel
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val searchResults = MutableLiveData<List<Entry>>()
    val searchResultsAllCategories = MutableLiveData<List<String>>()
    val searchResultsCategory = MutableLiveData<List<Entry>>()
    private lateinit var searchResultsDate: List<Entry>
    private lateinit var searchResultsAdvancedSearchField: List<Entry>


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

    fun findEntriesOfDateTimeSpan(
        dateFrom: String,
        dateTo: String,
        returnEntriesOfDateTimeSpan: (List<Entry>) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            searchResultsDate = entryDao.findEntriesOfDateTimeSpan(dateFrom, dateTo)
            Log.i("Debug_A", "EntryRepository -Date: " + searchResultsDate.toString())
            returnEntriesOfDateTimeSpan(searchResultsDate)
        }
    }

    // find entries by advanced search field

    fun findEntriesOfAdvancedSearchField(
        field: String,
        keyword: String,
        returnEntriesOfAdvancedSearchField: (List<Entry>) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.Main) {
            when(field){
                "title" -> searchResultsAdvancedSearchField = entryDao.findAdvancedSearchTitle(keyword)
                "description" -> searchResultsAdvancedSearchField = entryDao.findAdvancedSearchDescription(keyword)
                "source" -> searchResultsAdvancedSearchField = entryDao.findAdvancedSearchSource(keyword)
            }
            Log.i("Debug_A", "EntryRepository - Field: " + searchResultsAdvancedSearchField.toString())
            returnEntriesOfAdvancedSearchField(searchResultsAdvancedSearchField)
        }
    }




}