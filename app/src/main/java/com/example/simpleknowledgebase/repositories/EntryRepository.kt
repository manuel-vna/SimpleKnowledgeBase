package com.example.simpleknowledgebase.repositories


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
    //TBD: Adv. Search testing:
    var searchResultsDate: List<Entry>? = null


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


    fun findKeyword(keyword: String){
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = keywordFindAsync(keyword).await()
        }
    }
    private fun keywordFindAsync(keyword: String): Deferred<List<Entry>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async entryDao.findKeyword(keyword)
        }


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


    fun findCategory(keyword: String){
        coroutineScope.launch(Dispatchers.Main) {
            searchResultsCategory.setValue(singleCategoryFindAsync(keyword).await())
        }
    }
    private fun singleCategoryFindAsync(keyword: String): Deferred<List<Entry>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async entryDao.findCategory(keyword)
        }


    var searchResultsRowNumber = entryDao.observeTotalRowNumber()


    //TBD: Adv. Search testing:

    /*
    fun findEntriesOfDateTimespan(dateFrom: String, dateTo: String){
        coroutineScope.launch(Dispatchers.Main) {
            searchResultsDate = dateFindAsync(dateFrom, dateTo).await()
        }
    }
    private fun dateFindAsync(dateFrom: String, dateTo: String): Deferred<List<Entry>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async entryDao.findEntriesOfDateTimespan(dateFrom, dateTo)
        }

     */


}