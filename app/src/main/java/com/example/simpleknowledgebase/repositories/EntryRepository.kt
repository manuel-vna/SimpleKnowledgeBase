package com.example.simpleknowledgebase.repositories


import androidx.lifecycle.MutableLiveData
import com.example.simpleknowledgebase.Entry
import com.example.simpleknowledgebase.EntryDao
import com.example.simpleknowledgebase.viewmodels.KeywordSearchViewModel
import kotlinx.coroutines.*



class EntryRepository(private val entryDao: EntryDao) {

    private lateinit var keywordSearchViewModel: KeywordSearchViewModel
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    val searchResults = MutableLiveData<List<Entry>>()
    val searchResultsAllCategories = MutableLiveData<List<String>>()
    val searchResultsCategory = MutableLiveData<List<Entry>>()

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
            searchResults.value = asyncKeywordFind(keyword).await()
        }
    }
    private fun asyncKeywordFind(keyword: String): Deferred<List<Entry>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async entryDao.findKeyword(keyword)
        }


    fun findAllCategories(){
        coroutineScope.launch(Dispatchers.Main) {
            searchResultsAllCategories.value = asyncCategoryFind().await()
        }
    }
    private fun asyncCategoryFind(): Deferred<List<String>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async entryDao.findAllCategories()
        }


    fun findCategory(keyword: String){
        coroutineScope.launch(Dispatchers.Main) {
            searchResultsCategory.value = asyncSingleCategoryFind(keyword).await()
        }
    }
    private fun asyncSingleCategoryFind(keyword: String): Deferred<List<Entry>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async entryDao.findCategory(keyword)
        }



}