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


    fun findKeyword(keyword: String){
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncKeywordFind(keyword).await()
        }
    }

    fun insertEntry(entry: Entry){
        coroutineScope.launch(Dispatchers.IO) {
            entryDao.insertEntry(entry)
        }
    }

    private fun asyncKeywordFind(keyword: String): Deferred<List<Entry>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async entryDao.findKeyword(keyword)
        }

}